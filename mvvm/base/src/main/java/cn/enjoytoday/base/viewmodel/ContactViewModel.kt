package cn.enjoytoday.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.enjoytoday.base.data.base.BaseRepository

/**
 * 通讯录模块viewModel
 */
class ContactViewModel:AndroidViewModel {

    constructor(context: Application, respository: BaseRepository):super(context){

    }
}