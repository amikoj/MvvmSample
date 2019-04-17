package com.worfu.peagang.base.base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mobstat.StatService
import com.jeremyliao.liveeventbus.LiveEventBus
import com.worfu.peagang.base.BuildConfig
import com.worfu.peagang.base.utils.Utils

/**
 * Application基类
 */
open class BaseApplication : Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        initARouter()
        initBaidu()
        Utils.init(context).setSharePreferencesName(Common.APP_SP_NAME)


        LiveEventBus.get().config()
//            .supportBroadcast(this) //配置支持跨进程、跨APP通信
            .lifecycleObserverAlwaysActive(true) // 配置LifecycleObserver（如Activity）接收消息的模式：
        // true：整个生命周期（从onCreate到onDestroy）都可以实时收到消息
        // false：激活状态（Started）可以实时收到消息，非激活状态（Stoped）无法实时收到消息，需等到Activity重新变成激活状态，方可收到消息

    }


    private fun initARouter() {
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)// 尽可能早，推荐在Application中初始化
    }


    private fun initBaidu() {
        //百度移动统计
        StatService.setDebugOn(BuildConfig.DEBUG)
        StatService.autoTrace(applicationContext)
    }



}