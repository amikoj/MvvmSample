package cn.enjoytoday.base.mvvm

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @ClassName BaseAdapter
 * @Description  列表绑定
 * @Author hfcai
 * @Date 2019/4/17 15:05
 * @Version 1.0
 */
abstract class BaseAdapter<T,V: ViewDataBinding>(@LayoutRes val layoutResId:Int):
    BaseQuickAdapter<T, TestHodler<V>>(layoutResId) {




    override fun convert(helper: TestHodler<V>?, item: T) {
        observe(helper?.binding,helper,item)
    }


    abstract fun observe(v:V?,helper: BaseViewHolder?, item: T)
}


class TestHodler<V:ViewDataBinding>(val view: View):BaseViewHolder(view){
     var binding:V = DataBindingUtil.bind(view)!!
}