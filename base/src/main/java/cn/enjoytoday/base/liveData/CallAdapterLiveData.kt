package cn.enjoytoday.base.liveData

import androidx.lifecycle.MutableLiveData
import cn.enjoytoday.base.NetState
import cn.enjoytoday.base.data.http.BaseResp
import cn.enjoytoday.base.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName CallAdapterLiveData
 * @Description 网络请求LiveData
 * @Author hfcai
 * @Date 2019/4/15 18:21
 * @Version 1.0
 */
open class CallAdapterLiveData<T> : MutableLiveData<T>() {

    //这个作用是业务在多线程中,业务处理的线程安全问题,确保单一线程作业
    val flag = AtomicBoolean(false)
    var code: Int = NetState.UNKNOWN_ERROR
    var msg: String = "未知错误"

    private fun postDefault() {
        LogUtils.e("postDefault")
        val resp = BaseResp<Unit>(code, msg)
        postValue(resp as T)
    }

    /**
     * 请求回调重写
     */
    fun <R> request(callHttp: Call<R>) {
        if (flag.compareAndSet(false, true)) {
            callHttp.enqueue(object : Callback<R> {
                override fun onFailure(call: Call<R>?, t: Throwable?) {
                    t?.printStackTrace()
                    if(t is IllegalStateException){
                        code = NetState.JSON_ERROR
                        LogUtils.e(t?.message ?: "json解析异常")
                        msg = ""
                    }else {
                        code = NetState.NET_ERROR
                        LogUtils.e(t?.message ?: "网络异常")
                        msg = "网络异常"
                    }
                    postDefault()
                }

                override fun onResponse(call: Call<R>?, response: Response<R>?) {
                    code = response?.code() ?: NetState.REQUEST_ERROR
                    msg = "请求出错"
                    try {
                        LogUtils.e(response?.message() ?: "请求出错")
                        if (response?.code()==NetState.RESULT_OK){
                            val a = response?.body() as T
                            postValue(a)
                        }else{
                            postDefault()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        code = NetState.REQUEST_ERROR
                        LogUtils.e(e.message ?: "请求出错")
                        msg = "请求出错"
                        postDefault()
                    }
                }
            })
        }
    }


    fun <R> refresh(call: Call<R>) {
        request(call)
    }

    /**
     * 快捷获取
     */
    fun <S> getDataValue(): S? {
        val a = super.getValue() as BaseResp<S>
        return a.data
    }


}