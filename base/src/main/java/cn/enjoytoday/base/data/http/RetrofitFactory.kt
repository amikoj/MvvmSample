package cn.enjoytoday.base.data.http

import cn.enjoytoday.base.Common
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @ClassName RetrofitFactory
 * @Description Retrofit工厂，单例
 * @Author hfcai
 * @Date 2019/4/4 16:27
 * @Version 1.0
 */
class RetrofitFactory private constructor() {

    /**
     * 单例实现
     */
    companion object {
        const val CONTENT_TYPE = "Content-Type"
        const val JSON_TYPE = "application/json"
        const val CHARSET = "charset"
        const val UTF_ENCODE = "UTF-8"

        const val TIME_OUT = 30L
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }


    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, JSON_TYPE)
                .addHeader(CHARSET, UTF_ENCODE)
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


    /**
     * OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 日志拦截器
     */
    private fun initLogInterceptor(): HttpLogInterceptor {
        val interceptor = HttpLogInterceptor()
        interceptor.setLevel(HttpLogInterceptor.Level.BODY)
        return interceptor
    }

    /**
     * 具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}