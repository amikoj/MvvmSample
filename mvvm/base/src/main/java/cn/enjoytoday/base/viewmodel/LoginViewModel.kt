package cn.enjoytoday.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.enjoytoday.base.data.base.BaseRepository

/**
 * @ClassName LoginViewModel
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/2 11:44
 * @Version 1.0
 */
class LoginViewModel:AndroidViewModel {

    constructor(context: Application, respository: BaseRepository):super(context){

    }
}