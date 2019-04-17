package com.worfu.peagang.ui

import androidx.databinding.BindingAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.worfu.peagang.R
import com.worfu.peagang.api.JobDesc
import cn.enjoytoday.base.mvvm.BaseAdapter
import com.worfu.peagang.databinding.ItemJobBinding
import me.jessyan.autosize.utils.LogUtils

/**
 * @ClassName TestListAdapter
 * @Description 测试列表数据
 * @Author hfcai
 * @Date 2019/4/17 14:31
 * @Version 1.0
 */
class TestListAdapter :BaseAdapter<JobDesc, ItemJobBinding>(R.layout.item_job) {


    override fun observe(v:ItemJobBinding?, helper: BaseViewHolder?, item: JobDesc) {
        v?.apply {
            jobDesc = item
            executePendingBindings()
        }
        LogUtils.e("get job name:${v?.jobDesc?.job_name} and pca:${v?.jobDesc?.pca}")
    }


}