package cn.enjoytoday.base.utils

import android.util.Log
import cn.enjoytoday.base.BuildConfig
import cn.enjoytoday.base.base.Common

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

}
