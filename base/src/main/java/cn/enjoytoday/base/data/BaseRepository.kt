package cn.enjoytoday.base.data

import cn.enjoytoday.base.data.local.LocalBaseRepository
import cn.enjoytoday.base.data.remote.RemoteRepository

/**
 * @ClassName BaseRepository
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/3 16:41
 * @Version 1.0
 */
class BaseRepository:Repository {

    private  var localResp:LocalBaseRepository = LocalBaseRepository()
    private  var remoteResp:RemoteRepository = object:RemoteRepository(){
        override fun setApi(clazz: Class<*>) {

        }
    }



}