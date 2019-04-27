package cn.enjoytoday.test.ui


import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

/**
 * @ClassName SpinnerBinding
 * @Description spinner属性设置
 * @Author hfcai
 * @Date 2019/4/16 11:56
 * @Version 1.0
 */
object SpinnerBinding {


    /**
     * 设置spinner数据源
     */
    @BindingAdapter("app:entries")
    @JvmStatic fun setRemoteTypes(spinner:Spinner,items:List<String>){
        spinner.adapter = ArrayAdapter<String>(spinner.context,
            android.R.layout.simple_list_item_1,items.toTypedArray())
    }





}

