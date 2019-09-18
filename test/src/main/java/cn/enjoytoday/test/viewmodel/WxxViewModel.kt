package cn.enjoytoday.test.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.enjoytoday.test.api.TestApi
import cn.enjoytoday.base.Event
import cn.enjoytoday.base.base.BaseViewModel

class WxxViewModel : BaseViewModel<TestApi>() {

    var imageUrl = MutableLiveData<String>()

    var dataMsg = MutableLiveData<String>()

    var url = MutableLiveData<String>().apply {
        value = "这是初始数据"
    }

    private val _event = MutableLiveData<Event<Int>>()

    val event: LiveData<Event<Int>>
        get() = _event


    fun onClick(v: View) {
        _event.postValue(Event(v.id))

    }


}