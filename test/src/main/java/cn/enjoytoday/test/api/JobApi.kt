package cn.enjoytoday.test.api

import cn.enjoytoday.base.data.remote.BaseResp
import cn.enjoytoday.base.liveData.CallAdapterLiveData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ClassName JobApi
 * @Description 职位请求
 * @Author hfcai
 * @Date 2019/4/17 11:46
 * @Version 1.0
 */
interface JobApi {
    @POST("/Api/Job/jobList")
    fun getJobList(@Body req: GetJobListReq): CallAdapterLiveData<BaseResp<GetJobListResp>>
}


data class GetJobListReq(val id:String)




data class GetJobListResp(
        val list: MutableList<JobDesc>,
        val page: Int,
        val count: Int,
        val pageCnt: Int
)

class JobDesc(
    val id: String,
    val jobName: String
)


