package cn.enjoytoday.base.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * create by http://me.xuxiao.wang on 2019/4/12 0012 18:05
 * 工具包初始化
 */
class Utils private constructor() {

    companion object {
        //
        private var SPARED_PREFERENCES_NAME = "base_shred_preferences_name"


        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        fun init(context: Context): Companion {
            Utils.context = context.applicationContext
            return this
        }

        fun setSharePreferencesName(name: String): Companion {
            SPARED_PREFERENCES_NAME = name
            return this
        }


        fun getContext(): Context {
            if (context == null) {
                throw NullPointerException("Utils toolkit should be initialized first in application")
            }
            return context!!
        }

        fun getSharePreferencesName(): String {
            return SPARED_PREFERENCES_NAME
        }

    }

}
