package com.worfu.peagang.base.annotation

import android.content.Context
import android.system.Os.accept
import cn.sharesdk.framework.authorize.e
import com.worfu.peagang.base.BuildConfig
import com.worfu.peagang.base.base.BaseApplication
import com.worfu.peagang.base.utils.LogUtils
import dalvik.system.DexFile
import java.io.File
import java.io.FileFilter
import java.lang.Exception
import java.net.JarURLConnection
import java.net.URL
import java.net.URLDecoder
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import android.R.attr.entries
import dalvik.system.BaseDexClassLoader
import java.lang.reflect.Field


/**
 * @ClassName AnnotationExpression
 * @Description 注解解析
 * @Author hfcai
 * @Date 2019/4/16 10:15
 * @Version 1.0
 */
object AnnotationExpression {


    internal fun getDexFiles(context: Context): Sequence<DexFile> {
        // Here we do some reflection to access the dex files from the class loader. These implementation details vary by platform version,
        // so we have to be a little careful, but not a huge deal since this is just for testing. It should work on 21+.
        // The source for reference is at:
        // https://android.googlesource.com/platform/libcore/+/oreo-release/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
        val classLoader = context.classLoader as BaseDexClassLoader

        val pathListField = field("dalvik.system.BaseDexClassLoader", "pathList")
        val pathList = pathListField.get(classLoader) // Type is DexPathList

        val dexElementsField = field("dalvik.system.DexPathList", "dexElements")
        @Suppress("UNCHECKED_CAST")
        val dexElements = dexElementsField.get(pathList) as Array<Any> // Type is Array<DexPathList.Element>
        val dexFileField = field("dalvik.system.DexPathList\$Element", "dexFile")
        return dexElements.map {
            dexFileField.get(it) as DexFile
        }.asSequence()
    }


    private fun field(className: String, fieldName: String): Field {
        val clazz = Class.forName(className)
        val field = clazz.getDeclaredField(fieldName)
        field.isAccessible = true
        return field
    }


    /**
     * 解析retrofit请求Api类,遍历获取所有注解类，以及类信息
     */
    fun expressionRemoteApi(context: Context): MutableMap<String, AnnotationInfo> {
        val mutableMap = mutableMapOf<String, AnnotationInfo>()
        val pkgName = BuildConfig.APPLICATION_ID.substring(0, BuildConfig.APPLICATION_ID.lastIndexOf("."))

        LogUtils.e("get packageName:$pkgName")


        getDexFiles(context)
            .flatMap { it.entries().asSequence() }
            .filter { it.startsWith(pkgName) }
            .map {
              val className =  context.classLoader.loadClass(it)
                LogUtils.e("get classes:$className")
            }



        val classList = mutableListOf<Class<*>>()

        try {

            val df = DexFile(context.packageCodePath) //通过DexFile查找当前的APK中可执行文件
            val enumeration = df.entries()

            LogUtils.e("get enumeration hasMoreElements:${enumeration.hasMoreElements()}")
            while (enumeration.hasMoreElements()) {//遍历
                val className = enumeration.nextElement()
                LogUtils.e("get enumeration className:$className")
                if (className.contains(pkgName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classList.add(Class.forName(className))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

//        val classList = getClasses(pkgName)
        LogUtils.e("get classes size:${classList.size}")
        classList.filter {
            LogUtils.e("class isInterface:${it.isInterface},and annotation:" +
                    "${it.getAnnotation(TestRemoteApi::class.java)}")
            it.isInterface &&  it.getAnnotation(TestRemoteApi::class.java)!=null
        }.onEach {
            LogUtils.e("get classes:$it")
        }
        return mutableMap
    }


    /**
     * 通过包名通过
     */
    fun getClasses(pkgName: String):MutableList<Class<*>> {
        LogUtils.e("getClasses:$pkgName")
        val classes = mutableListOf<Class<*>>()
        val recursive = true
        val packDirName = pkgName.replace(".", "/")
        val dirs: Enumeration<URL>
        try {
            dirs = Thread.currentThread().contextClassLoader.getResources(packDirName)
            LogUtils.e("get dir hasMoreElements:${dirs.hasMoreElements()}")
            while (dirs.hasMoreElements()) {
                val url = dirs.nextElement()
                val protocol = url.protocol
                LogUtils.e("get protocol:$protocol")
                if ("file" == protocol) {
                    val filePath = URLDecoder.decode(url.file, "UTF-8")
                    findAndAddClassesInPackageByFile(pkgName, filePath, recursive, classes)
                } else if ("jar" == protocol) {
                    val jar: JarFile
                    try {
                        // 获取jar
                        jar = (url.openConnection() as JarURLConnection).jarFile
                        // 从此jar包 得到一个枚举类
                        val entries: Enumeration<JarEntry> = jar.entries()
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            val entry = entries.nextElement();
                            var name = entry.name
                            // 如果是以/开头的

                            if (name[0] == '/') {
                                name = name.substring(1)
                            }

                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packDirName)) {

                                var packageName: String? = null
                                val idx = name.lastIndexOf('/')
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.')
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory) {
                                        // 去掉后面的".class" 获取真正的类名

                                        packageName?.let {
                                            val className = name.substring(it.length + 1, name.length - 6)
                                            try {
                                                // 添加到classes
                                                classes.add(Class.forName("$packageName.$className"))
                                            } catch (e: ClassNotFoundException) {
                                                e.printStackTrace()
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return classes
    }


    /**
     * 以文件形式来获取包下的所有class
     */
    fun findAndAddClassesInPackageByFile(
        packageName: String, filePath: String,
        recursive: Boolean, classes: MutableList<Class<*>>
    ) {


        // 获取此包的目录 建立一个File
        val dir = File(packageName)
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory) {
            return
        }
        // 如果存在 就获取包下的所有文件 包括目录

        val dirfiles = dir.listFiles(FileFilter {
            (recursive && it.isDirectory) || (it.name.endsWith(".class"))
        }).forEachIndexed { _, file ->
            if (file.isDirectory) {
                findAndAddClassesInPackageByFile(
                    packageName + "." + file.name,
                    file.absolutePath, recursive, classes
                )
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                val className = file.name.substring(0, file.name.length - 6)
                try {
                    classes.add(Class.forName("$packageName.$className"));
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}















