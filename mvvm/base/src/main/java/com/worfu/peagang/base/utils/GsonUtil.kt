package com.worfu.peagang.base.utils

import com.google.gson.Gson

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * create by http://me.xuxiao.wang on 2019/4/15 0015 10:20
 * json数据管理类
 */
object GsonUtil {
    private var gson: Gson? = null

    init {
        if (gson == null) {
            gson = Gson()
        }
    }

    /**
     * json字符串转成bean对象
     */
    fun <T> jsonToBean(jsonString: String, cls: Class<T>): T {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!.fromJson(jsonString, cls)
    }

    /**
     * @author
     * creat at 2019/1/26 16:14
     * desc: json字符串转成list类型
     */
    fun <T> jsonToListBean(jsonArray: String, cls: Class<T>): List<T>? {
        val type = ParameterizedTypeImpl(cls)
        return gson!!.fromJson<List<T>>(jsonArray, type)
    }

    /**
     * bean对象转成字符串
     */
    fun beanToString(`object`: Any): String {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!.toJson(`object`)
    }

    private class ParameterizedTypeImpl<T>(internal var clazz: Class<T>) : ParameterizedType {

        override fun getActualTypeArguments(): Array<Type> {
            return arrayOf(clazz)
        }

        override fun getRawType(): Type {
            return List::class.java
        }

        override fun getOwnerType(): Type? {
            return null
        }
    }
}
