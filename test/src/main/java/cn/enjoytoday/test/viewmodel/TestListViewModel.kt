package cn.enjoytoday.test.viewmodel

import cn.enjoytoday.test.api.GetJobListReq
import cn.enjoytoday.test.api.GetJobListResp
import cn.enjoytoday.test.api.JobApi
import cn.enjoytoday.base.base.BaseViewModel
import cn.enjoytoday.base.data.remote.BaseResp
import cn.enjoytoday.base.liveData.CallAdapterLiveData

/**
 * @ClassName TestListViewModel
 * @Description list列表数据操作viewModel
 * @Author hfcai
 * @Date 2019/4/17 11:45
 * @Version 1.0
 */
class TestListViewModel:BaseViewModel<JobApi>() {

//{"lat":31.805516,"lng":117.223621,"page":1,"page_num":10,"pay_type":0,"region_id":0,"salary_id":0,"sort":1}
    /**
     * 列表数据
     */
    val jobList:CallAdapterLiveData<BaseResp<GetJobListResp>> by lazy {
        api!!.getJobList(GetJobListReq(page = 1,page_num = 10,sort = 1,lng=117.223621,lat=31.805516))
    }







}

