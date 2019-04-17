package com.worfu.peagang.base.annotation

/**
 * @ClassName TestRemoteApi
 * @Description 定义网络请求调用，辅助测试获取网络请求
 * @Author hfcai
 * @Date 2019/4/16 10:11
 * @Version 1.0
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestRemoteApi(val apiName:String = "default")