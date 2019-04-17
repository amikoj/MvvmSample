package com.worfu.peagang.api

import com.worfu.peagang.base.annotation.TestRemoteApi
import com.worfu.peagang.base.data.remote.BaseResp
import com.worfu.peagang.base.liveData.CallAdapterLiveData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ClassName TestApi
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/4 17:20
 * @Version 1.0
 */

@TestRemoteApi(apiName = "测试模块")
interface TestApi{
    /**
     * 获取验证码
     */
    @POST("Api/Account/getCode")
    fun getVerifyCode(@Body req: GetCodeReq): CallAdapterLiveData<BaseResp<GetCodeResp>>
}

//请求
data class GetCodeReq(val mobile: String, val verify: Int, val type: Int, val timestamp: Long, var SN: String?)

//返回
data class GetCodeResp(val isVerify : Int, val url:String)