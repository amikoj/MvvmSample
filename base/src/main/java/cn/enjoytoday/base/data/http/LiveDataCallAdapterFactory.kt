package cn.enjoytoday.base.data.http

import cn.enjoytoday.base.liveData.CallAdapterLiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName LiveDataCallAdapterFactory
 * @Description LiveData retrofit适配器
 * @Author hfcai
 * @Date 2019/4/4 16:17
 * @Version 1.0
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {


    override fun get(returnType: Type?, annotations: Array<out Annotation>?,
                     retrofit: Retrofit?): CallAdapter<*>? {
        if(returnType !is ParameterizedType){
            throw IllegalArgumentException("返回值需为参数化类型")
        }
        val returnClass = getRawType(returnType)
        if(returnClass != CallAdapterLiveData::class.java){
            throw IllegalArgumentException("MutableLiveData")
        }
        val type =  getParameterUpperBound(0, returnType)
        return LiveDataCallAdapter(type)
    }


    class LiveDataCallAdapter(var type:Type):CallAdapter<CallAdapterLiveData<*>>{

        override fun <R>adapt(call: Call<R>?): CallAdapterLiveData<*>? {
            var mutableLiveData: CallAdapterLiveData<*>? = null
            call?.let {
                mutableLiveData = object : CallAdapterLiveData<R>(){
                    override fun onActive() {
                        super.onActive()
                        request(call)
                    }
                }
            }
            return mutableLiveData
        }

        override fun responseType(): Type = type

    }
}
