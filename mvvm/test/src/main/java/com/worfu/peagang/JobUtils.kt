package com.worfu.peagang

import android.annotation.SuppressLint
import android.widget.ListView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worfu.peagang.base.mvvm.BaseAdapter
import me.jessyan.autosize.utils.LogUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName JobUtils
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/17 16:26
 * @Version 1.0
 */
object JobUtils {



    /**
     * 转化为薪资显示字符串
     */
    @JvmStatic
    fun toSalaryString(salaryLow:String?,salaryHigh:String?,salaryUnit:String?):String{

        LogUtils.e("get toSalaryString")
        return "$50/天"
    }


    /**
     * 格式化时间
     */
    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun toTimes(timestamp:Long):String{

        LogUtils.e("get toTimes")
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.format(Date(timestamp*1000))
    }










}