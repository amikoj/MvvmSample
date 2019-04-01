package cn.enjoytoday.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.enjoytoday.base.data.base.BaseRepository

/**
 * 用户模块
 */
class UserViewModel:AndroidViewModel{
    constructor(context:Application,respository: BaseRepository):super(context){

    }
}