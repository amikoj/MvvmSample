package cn.enjoytoday.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.enjoytoday.base.data.base.BaseRepository

/**
 * 测试viewmodle模块
 */
class TestViewModel:AndroidViewModel {

    constructor(context:Application,respository:BaseRepository):super(context){

    }
}