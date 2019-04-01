package cn.enjoytoday.backup.user

import android.util.Log
import cn.enjoytoday.base.base.BaseApplication

/**
 * 用户模块applicaton
 */
class UserApplication :BaseApplication(){

    override fun onCreate() {
        Log.e("application","Application onCreate")
        super.onCreate()
    }
}