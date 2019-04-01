package cn.enjoytoday.base

/**
 * LiveData的事件发送包装器
 */
open class Event<T> (private var mContent:T){
    private var hasBeenHandled =  false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }
    }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }


}