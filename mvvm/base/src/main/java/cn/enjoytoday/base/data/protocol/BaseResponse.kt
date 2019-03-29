package cn.enjoytoday.base.data.protocol

/**
 * 网络数据请求格式
 */
data class BaseResponse<T>(val code:Int,
                        val message:String,
                        var data:T?=null)