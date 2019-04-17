package com.worfu.peagang.base.data.remote

import com.worfu.peagang.base.base.Common
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @ClassName RetrofitFactory
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/4 16:27
 * @Version 1.0
 */

/*
    Retrofit工厂，单例
 */
class RetrofitFactory private constructor() {

    /*
        单例实现
     */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()

            chain.proceed(request)
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
            .baseUrl(Common.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(initClient())
            .build()
    }

    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /*
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /*
        具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}