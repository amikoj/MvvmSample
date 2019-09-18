package cn.enjoytoday.base.utils

import android.util.Log
import cn.enjoytoday.base.BuildConfig
import cn.enjoytoday.base.Common
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * create by http://me.xuxiao.wang on 2019/4/9 0009 16:10
 * log打印
 */
object LogUtils {

    private var TAG = Common.LOG_TAG

    private fun isDebug(method: () -> Unit) {
        if (BuildConfig.DEBUG || System.getProperty(Common.LOG_KEY) == "true") {
            method()
        }
    }

    fun i(any: Any?) {
        isDebug {
            if (any is String) {
                Log.i(TAG, any)
            } else {
                Log.i(TAG, "$any")
            }
        }

    }

    fun i(tag: String, any: Any?) {
        isDebug {
            if (any is String) {
                Log.i(tag, any)
            } else {
                Log.i(tag, "$any")
            }
        }

    }

    fun d(any: Any?) {
        isDebug {
            if (any is String) {
                Log.d(TAG, any)
            } else {
                Log.d(TAG, "$any")
            }
        }

    }


    fun d(tag:String,any: Any?) {
        isDebug {
            if (any is String) {
                Log.d(tag, any)
            } else {
                Log.d(tag, "$any")
            }
        }

    }

    fun w(any: Any?) {
        isDebug {
            if (any is String) {
                Log.w(TAG, any)
            } else {
                Log.w(TAG, "$any")
            }
        }

    }

    fun e(any: Any?) {
        isDebug {
            if (any is String) {
                Log.e(TAG, any)
            } else {
                Log.e(TAG, "$any")
            }
        }

    }


    fun e(tag:String,any: Any?) {
        isDebug {
            if (any is String) {
                Log.e(tag, any)
            } else {
                Log.e(tag, "$any")
            }
        }

    }


    val LINE_SEPARATOR = System.getProperty("line.separator")

    fun printLine(tag: String, isTop: Boolean) {
        if (isTop) {
            Log.w(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            Log.w(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
    }

    //打印json字符串
    fun printJson(tag: String, msg: String, headString: String?="") {
        isDebug{
            var message: String
            var isJson = true
            message = try {
                when {
                    msg.startsWith("{") -> {
                        val jsonObject = JSONObject(msg)
                        jsonObject.toString(4)//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数

                    }
                    msg.startsWith("[") -> {

                        val jsonArray = JSONArray(msg)
                        jsonArray.toString(4)
                    }
                    else -> {
                        isJson = false
                        msg
                    }
                }
            } catch (e: JSONException) {
                msg
            }
            if (headString.isNullOrEmpty().not()){
                message = headString + LINE_SEPARATOR + message
            }
            if (isJson){
                //json 数据打印
                printLine(tag, true)
                val lines = message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (line in lines) {
                    Log.w(tag, "║ $line")
                }
                printLine(tag, false)
            }else{
                //打印
                Log.w(tag,message)
            }
        }
    }

}
