package com.worfu.peagang.base.liveData

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.worfu.peagang.base.utils.NetWorkUtils

/**
 * @ClassName NetStateLiveData
 * @Description 网络状态监听,通过监听NetStateLiveData监听网络状态改变
 * @Author hfcai
 * @Date 2019/4/4 9:56
 * @Version 1.0
 */
class NetStateLiveData(val context: Context) :LiveData<Boolean>(){

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            value = NetWorkUtils.isNetWorkAvailable(context)
        }
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(broadcastReceiver)
    }

    override fun onActive() {
        super.onActive()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }



    fun refresh(){


    }
}