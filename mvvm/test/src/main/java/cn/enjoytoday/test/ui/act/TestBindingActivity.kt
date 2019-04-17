package com.worfu.peagang.ui.act

import android.os.Bundle
import androidx.lifecycle.Observer
import com.worfu.peagang.R
import cn.enjoytoday.base.mvvm.BaseMvvmActivity
import cn.enjoytoday.base.utils.LogUtils
import com.worfu.peagang.databinding.ActivityTestBinding
import com.worfu.peagang.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast
import kotlin.jvm.internal.Reflection

/**
 * @ClassName TestBindingActivity
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/11 16:53
 * @Version 1.0
 */
class TestBindingActivity :BaseMvvmActivity<ActivityTestBinding, TestViewModel>(R.layout.activity_test){

    override fun observe(v: TestViewModel) {
        v.apply {
            //标题变化监听
            title.observe(this@TestBindingActivity, androidx.lifecycle.Observer { t ->
                toast("title changed to:$t")
            }  )


            refresh.observe(this@TestBindingActivity, Observer { t ->

                if(!t.hasBeenHandled){
                    LogUtils.e("数据已刷新！")

                }

            })

            code.observe(this@TestBindingActivity, Observer {
                LogUtils.e("code 数据更新！")
            })

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 类型选择
         */
        modelSpinner?.setOnItemClickListener { parent, view, position, id ->
            //path源联动

        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.start()
    }






}