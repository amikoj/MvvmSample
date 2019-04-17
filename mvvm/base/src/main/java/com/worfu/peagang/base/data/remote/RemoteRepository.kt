package com.worfu.peagang.base.data.remote

/**
 * @ClassName RemoteRepository
 * @Description 在线数据获取
 * @Author hfcai
 * @Date 2019/4/4 14:25
 * @Version 1.0
 */
abstract class RemoteRepository{
    abstract fun setApi(clazz:Class<*>)
}