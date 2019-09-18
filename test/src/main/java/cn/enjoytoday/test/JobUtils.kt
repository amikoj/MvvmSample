package cn.enjoytoday.test

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
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