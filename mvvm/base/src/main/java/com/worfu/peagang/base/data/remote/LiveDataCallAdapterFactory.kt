package com.worfu.peagang.base.data.remote

import androidx.lifecycle.MutableLiveData
import com.worfu.peagang.base.liveData.CallAdapterLiveData
import com.worfu.peagang.base.utils.LogUtils
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName LiveDataCallAdapterFactory
 * @Description LiveData retrofit适配器
 * @Author hfcai
 * @Date 2019/4/4 16:17
 * @Version 1.0
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {



    private val map:MutableMap<String, CallAdapterLiveData<*>> = mutableMapOf()





    /**
     * 如果你要返回
     * LiveData<?>
     */
    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*>? {
        LogUtils.e("liveData onActive get")
        if(returnType !is ParameterizedType){
            throw IllegalArgumentException("返回值需为参数化类型")
        }
        //获取returnType的class类型
        val returnClass = CallAdapter.Factory.getRawType(returnType)
        if(returnClass != CallAdapterLiveData::class.java){
            throw IllegalArgumentException("MutableLiveData")
        }
        //先解释一下getParameterUpperBound
        //官方例子
        //For example, index 1 of {@code Map<String, ? extends Runnable>} returns {@code Runnable}.
        //获取的是Map<String,? extends Runnable>参数列表中index序列号的参数类型,即0为String,1为Runnable
        //这里的0就是LiveData<?>中?的序列号,因为只有一个参数
        //其实这个就是我们请求返回的实体

        val type = CallAdapter.Factory.getParameterUpperBound(0, returnType)
        LogUtils.e("liveData onActive---------before get adapter")
        val a = LiveDataCallAdapter(map,type)
        LogUtils.e("get request data:${a.responseType()}")
        return a
    }
    /**
     * 请求适配器
     */
    class LiveDataCallAdapter(val map: MutableMap<String,CallAdapterLiveData<*>>,var type:Type):CallAdapter<CallAdapterLiveData<*>>{


        override fun <R>adapt(call: Call<R>?): CallAdapterLiveData<R>? {

            var mutableLiveData:CallAdapterLiveData<R>? =null
            call?.let {
                LogUtils.e("开始请求数据")
                val requestUrl =call.request().url().encodedPath()
                LogUtils.e("请求url:$requestUrl")


                if (map.containsKey(requestUrl)){
                    LogUtils.e("请求数据已有缓存")
                     mutableLiveData = map[requestUrl] as CallAdapterLiveData<R>?
                     mutableLiveData?.flag?.set(false)
                    mutableLiveData?.refresh(call)
                }else{
                    LogUtils.e("不存在当前请求")
                    mutableLiveData = object:CallAdapterLiveData<R>(call){

                        override fun request(callHttp: Call<R> ) {
                            if(flag.compareAndSet(false,true)){
                                callHttp.enqueue(object: Callback<R> {
                                    override fun onFailure(call: Call<R>?, t: Throwable?) {
                                        LogUtils.e("CallAdapter onFailure:${t?.message}")
                                        postValue(null)
                                    }
                                    override fun onResponse(call: Call<R>?, response: Response<R>?) {
                                        LogUtils.e("CallAdapter onResponse:${response?.body().toString()}")
                                        postValue(response?.body())
                                    }
                                })
                            }
                        }

                    }

                    map.put(requestUrl, mutableLiveData as CallAdapterLiveData<R>)

                }


            }



            return mutableLiveData
        }


        override fun responseType(): Type = type

    }
}
