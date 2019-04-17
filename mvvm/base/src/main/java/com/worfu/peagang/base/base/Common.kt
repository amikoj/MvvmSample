package com.worfu.peagang.base.base

import com.worfu.peagang.base.BuildConfig

/**
 * @ClassName Common
 * @Description 常量
 * @Author hfcai
 * @Date 2019/4/1 16:13
 * @Version 1.0
 */
object Common {

    //在线服务器地址
    const val IMAGE_SERVER_ADDRESS = BuildConfig.IMAGE_SERVER_ADDRESS
    const val SERVER_ADDRESS = BuildConfig.SERVER_ADDRESS
    const val SERVER_h5 = BuildConfig.SERVER_h5

    const val LOG_TAG = "====="


    /**
     * 手机轻量级配置数据保存
     */
    const val APP_SP_NAME = BuildConfig.APPLICATION_ID + "_app_configure"


    const val TOKEN = "token"
    const val USER_NAME = "userName"
    const val NICK_NAME = "nickName"


    // 通过命令控制日志打印
    const val LOG_KEY = "worfu_debug"

    /**
     * app加密
     */
    const val APP_KEY_WANDOUJOB = "802a657325e71d88"


    const val DEFAUTL_BINDING_VIEW_MODEL_NAME = "mViewmodel"


    const val LATITUDE = "latitude" // 纬度
    const val LONGITUDE = "longitude" // 经度
    const val LOCATION_CITY_NAME = "location_city_name" // 定位到的城市名

}