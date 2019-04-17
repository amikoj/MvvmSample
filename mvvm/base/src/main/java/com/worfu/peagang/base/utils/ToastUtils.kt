package com.worfu.peagang.base.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.worfu.peagang.base.R


/**
 * create by http://me.xuxiao.wang on 2019/4/12 0012 17:41
 * toast工具类
 */
class ToastUtils private constructor() {

    private var mToast: Toast? = null
    private var mTvToast: TextView? = null

    companion object {
        val instance: ToastUtils by lazy { ToastUtils() }
    }

    fun showShortToast(msg: String) {
        show(msg, Toast.LENGTH_SHORT)
    }

    fun showLongToast(msg: String) {
        show(msg, Toast.LENGTH_LONG)
    }

    private fun show(content: String, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast(Utils.getContext())
            mToast!!.setGravity(Gravity.BOTTOM, 0, 50)//设置toast显示的位置，这是居中
            mToast!!.duration = duration//设置toast显示的时长
            val root = LayoutInflater.from(Utils.getContext()).inflate(R.layout.toast_custom_common, null)//自定义样式，自定义布局文件
            mTvToast = root.findViewById(R.id.tvCustomToast) as TextView
            mToast!!.view = root//设置自定义的view
//            mToast = Toast.makeText(Utils.getContext(), "", duration)

        }
        mTvToast!!.text = content //设置文本
        mToast!!.show()//展示toast

    }

}