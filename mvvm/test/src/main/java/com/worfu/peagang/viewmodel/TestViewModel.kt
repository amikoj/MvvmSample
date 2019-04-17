package com.worfu.peagang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.worfu.peagang.api.GetCodeReq
import com.worfu.peagang.api.GetCodeResp
import com.worfu.peagang.api.TestApi
import com.worfu.peagang.base.Event
import com.worfu.peagang.base.annotation.AnnotationExpression
import com.worfu.peagang.base.annotation.AnnotationInfo
import com.worfu.peagang.base.base.BaseViewModel
import com.worfu.peagang.base.convert
import com.worfu.peagang.base.data.remote.BaseResp
import com.worfu.peagang.base.liveData.CallAdapterLiveData
import com.worfu.peagang.base.model.TestData
import com.worfu.peagang.base.utils.AppExecutors
import com.worfu.peagang.base.utils.DateUtils
import com.worfu.peagang.base.utils.LogUtils
import com.worfu.peagang.base.utils.LogUtils.e


/**
 * 测试viewmodle模块
 */
class TestViewModel: BaseViewModel<TestApi>() {


    /**
     * 添加注解的api详细内容
     */
    private var apiInfos:MutableMap<String,AnnotationInfo> = mutableMapOf<String,AnnotationInfo>()

    // 双向数据保定
    val title = MutableLiveData<String>()
    private val testData = MutableLiveData<TestData>().run {
        value = TestData()
        this
    }



    /**
     * 请求api所属类型
     */
    private val _types = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf("全部")
    }


    /**
     * 请求url地址
     */
    private val _paths = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf("暂无")
    }


    val paths:LiveData<MutableList<String>>
         get() = _paths


    val types:LiveData<MutableList<String>>
        get() = _types


    val onClick = MutableLiveData<Event<Unit>>().apply {
        value = Event(Unit)
    }


    val code:CallAdapterLiveData<BaseResp<GetCodeResp>> by lazy {
        api!!.getVerifyCode(GetCodeReq("18324711159", 123456, 16, DateUtils.curTime, null).convert())
    }

//    val code :LiveData<BaseResp<GetCodeResp>>
//        get() {
//           return _code
//        }


    val user:LiveData<TestData>
        get() = testData

    var a:Int =0

    val refresh = MutableLiveData<Event<Unit>>()

    fun start(){
       e("begin Start")

    }


    fun setTestData(){
        testData.postValue(TestData())
    }


    /**
     * 修改用户源头
     */
    fun checkPaths(type:String){

//        _paths.value =
    }








    fun <T>toJson(data: T)= Gson().toJson(data)

     fun getClick(){
         onClick.value = Event(Unit)
                 testData.value = testData.value?.apply {
            userName = "hfcai"
            sex = "woman"


        }
    }


    fun getVerifyCode(){
        LogUtils.e("----------------getVerifyCode---------------------: ")
        api!!.getVerifyCode(GetCodeReq("18324711159", 123456, 16, DateUtils.curTime, null).convert())


    }


}