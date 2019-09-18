package cn.enjoytoday.base.pattern

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @ClassName BaseHolder
 * @Description adapter基础的holder
 * @Author hfcai
 * @Date 2019/5/7 19:31
 * @Version 1.0
 */
class BaseHolder<V: ViewDataBinding>(val view: View): BaseViewHolder(view){

    var binding:V = DataBindingUtil.bind<V>(view)!!.apply{
        executePendingBindings()
    }
}