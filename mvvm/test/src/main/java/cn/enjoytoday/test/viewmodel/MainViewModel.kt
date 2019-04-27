package cn.enjoytoday.test.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.enjoytoday.test.api.TestApi
import cn.enjoytoday.base.Event
import cn.enjoytoday.base.base.BaseViewModel

/**
 * @ClassName MainViewModel
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/11 17:28
 * @Version 1.0
 */
class MainViewModel:BaseViewModel<TestApi>() {

    private val _clicked = MutableLiveData<Event<Int>>()


    val showFragment = MutableLiveData<Boolean>().apply {
        value = false
    }

    /**
     * 点击事件
     */
    val clicked:LiveData<Event<Int>>
        get() = _clicked

    fun onClick(view: View){
        _clicked.value = Event(view.id)
    }

}