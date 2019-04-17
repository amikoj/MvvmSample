package com.worfu.peagang.ui

import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.worfu.message.MyJPushManager
import com.worfu.peagang.BuildConfig
import com.worfu.peagang.base.base.BaseApplication

class App:BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        MyJPushManager.initJPush(this, BuildConfig.DEBUG)

        Beta.autoInit = true
        Beta.autoCheckUpgrade = false
        Beta.enableHotfix = false
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Bugly.init(applicationContext, "1662bd3ce9", false)

    }
}