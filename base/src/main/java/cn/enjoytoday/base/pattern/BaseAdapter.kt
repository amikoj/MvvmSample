package cn.enjoytoday.base.pattern

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * @ClassName BaseAdapter
 * @Description  列表绑定
 * @Author hfcai
 * @Date 2019/4/17 15:05
 * @Version 1.0
 */
abstract class BaseAdapter<T,V: ViewDataBinding>(@LayoutRes val layoutResId:Int,list:List<T>?=null):
        BaseQuickAdapter<T, BaseHolder<V>>(layoutResId,list) {

    override fun convert(helper: BaseHolder<V>?, item: T) {
        observe(helper?.binding,helper,item)
    }


    abstract fun observe(v:V?, helper: BaseHolder<V>?, item: T)
}