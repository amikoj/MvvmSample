package cn.enjoytoday.base.pojo

/**
 * @ClassName UserInfo
 * @Description 用户数据
 * @Author Administrator
 * @Date 2019/4/1 14:42
 * @Version 1.0
 */
data class UserInfo(var name:String,
                    var nickName:String?=null,
                    var logo:String?=null,
                    var pwd:String?,
                    var token:String?=null)