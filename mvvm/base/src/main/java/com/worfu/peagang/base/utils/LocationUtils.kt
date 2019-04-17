package com.worfu.peagang.base.utils

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption

/**
 * @author : sumao
 * @version : 1.0
 * @date : 2018/8/30 下午10:12
 * LocationUtils describe: 高德地图定位配置
 */

class LocationUtils private constructor() {

    private var mLocationOption: AMapLocationClientOption? = null

    private var mListener: OnLocationChangedListener? = null

    var mLocationClient: AMapLocationClient? = null

    init {
        initOption()
    }

    fun getLoacattion(listener: OnLocationChangedListener) {
        mListener = listener
        init()
    }

    private fun initOption() {
        //初始化定位参数
        mLocationOption = AMapLocationClientOption()
            .setNeedAddress(true)//设置是否返回地址信息（默认返回地址信息）
            .setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)//设置定位模式为高精度模式
            //   .setInterval(Constants.upload_position_time)//设置定位间隔,单位毫秒,默认为2000ms
            .setOnceLocation(true)//获取一次定位结果
            .setLocationCacheEnable(false)
    }

    private fun init() {
        mLocationClient = AMapLocationClient(Utils.getContext())
        mLocationClient?.let {
            it.setLocationListener { amapLocation ->
                if (amapLocation != null) {
                    if (amapLocation.errorCode == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        if (mListener != null) {
                            mListener!!.onSuccess(amapLocation.latitude, amapLocation.longitude, amapLocation.city)
                            stopSingleLocation()
                        }

                    } else {
                        if (mListener != null) {
                            mListener!!.onFail(amapLocation.errorCode, amapLocation.errorInfo)

                        }
                    }
                }
            }

            //设置定位参数
            it.setLocationOption(mLocationOption)
            it.startLocation()
        }
    }

    interface OnLocationChangedListener {
        /**
         * 成功
         *
         * @param latitude   纬度
         * @param longitude  精度
         * @param addressstr 地址
         */
        fun onSuccess(latitude: Double, longitude: Double, addressstr: String)

        /**
         * 失败
         *
         * @param errCode 错误码
         * @param errInfo 错误信息
         */
        fun onFail(errCode: Int, errInfo: String)
    }

    companion object {
        private var mLocationUtils: LocationUtils? = null

        val instance: LocationUtils
            get() {
                if (mLocationUtils == null) {
                    synchronized(LocationUtils::class.java) {
                        if (mLocationUtils == null) {
                            mLocationUtils = LocationUtils()
                        }
                    }
                }
                return this!!.mLocationUtils!!
            }
    }

    /**
     * 停止单次客户端
     */
    fun stopSingleLocation() {
        if (null != mLocationClient) {
            mLocationClient?.let {
                it.stopLocation()
                it.onDestroy()
            }
        }
    }

}
