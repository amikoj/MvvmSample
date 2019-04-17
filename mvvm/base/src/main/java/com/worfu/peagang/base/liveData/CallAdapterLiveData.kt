package com.worfu.peagang.base.liveData

import androidx.lifecycle.LiveData
import retrofit2.Call
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName CallAdapterLiveData
 * @Description 网络请求LiveData
 * @Author hfcai
 * @Date 2019/4/15 18:21
 * @Version 1.0
 */
abstract class CallAdapterLiveData<T>(var callHttp: Call<T>): LiveData<T>() {

    //这个作用是业务在多线程中,业务处理的线程安全问题,确保单一线程作业
    val flag = AtomicBoolean(false)
    var code:Int? = null
    var msg:String?=null

    /**
     * retrofit 请求数据
     */
    override fun onActive() {
        super.onActive()
        request(callHttp)
    }


    /**
     * 请求回调重写
     */
    abstract fun request(callHttp: Call<T>)


    fun refresh(call: Call<T>){
        callHttp = call
        request(callHttp)
    }


}