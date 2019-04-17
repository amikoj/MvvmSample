package com.worfu.peagang.api

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

/**
 * @author ： summo
 * @version ：1.0
 * @date ：2018/8/27 16:46
 *  <p> 描述:
 *      获取职位列表接口请求参数
 *      @param page 页码 默认为1
 *      @param page_num 分页数 默认10
 *      @param keywords 搜索职位或企业
 *      @param category_id 职位分类
 *      @param area_id 区域
 *      @param distance_id 距离
 *      @param salary_id 薪资范围
 *      @param pay_type 结算方式
 *      @param sort 智能排序 1最新 2最热 3附近 默认为1
 *      @param lng 经度 distance不为空时需或sort为3传
 *      @param lat 纬度 distance不为空时需或sort为3传
 *      @param company_id 企业下职位
 *
 *      {"area_id":0,"category_id":0,"company_id":0,"distance_id":0,
 *      "keywords":"","lat":31.805516,"lng":117.223621,
 *      "page":1,"page_num":10,"pay_type":0,
 *      "region_id":0,"salary_id":0,"sort":1}
 *
 *  </p>
 */
data class GetJobListReq(val region_id: Int = 0, var page: Int = 0,
                         val page_num: Int = 0, var keywords: String = "",
                         var category_id: Int = 0, var area_id: Int = 0,
                         var distance_id: Int = 0, var salary_id: Int = 0,
                         var pay_type: Int = 0, var sort: Int = 1,
                         var lng: Double = 0.0, var lat: Double = 0.0,
                         var company_id: Int = 0)



/**
 * @author ： summo
 * @version ：1.0
 * @date ：2018/8/27 16:46
 *  <p> 描述:
 *      获取职位列表接口请求参数
 *
 *      @property list  array[job] 职位
 *      @property page 页码
 *      @property count 搜索职位或企业
 *      @property page_cnt 职位分
 *
 *  </p>
 */
data class GetJobListResp(
    val list: MutableList<JobDesc>,
    val page: Int,
    val count: Int,
    val page_cnt: Int
)

/**
 * @author ： summo
 * @version ：1.0
 * @date ：2018/8/27 17:37
 *  <p> 描述: 职位简介
 *  @property job_id 职位id
 *  @property job_name 职位名称
 *  @property pca 省市区
 *  @property address 工作地址
 *  @property salary_unit 薪资单位
 *  @property salary_low 薪资范围
 *  @property salary_high 薪资范围
 *  @property update_time 更新时间 时间戳
 *  @property logo 企业logo
 *  @property c_name 企业名称
 *  @property icon_string 活动文字 不为空则为活动职位
 *  @property icon_img 活动角标
 *  @property distance 距离米
 *  </p>
 */
class JobDesc(
    val job_id: String,
    val job_name: String,
    val pca: String,
    val address: String,
    val salary_unit: String,
    val salary_low: String,
    val salary_high: String,
    val update_time: Long,
    val logo: String,
    val c_name: String,
    val icon_string: String,
    val icon_img: String,
    val distance: String,
    val salary_unit_str: String
)


