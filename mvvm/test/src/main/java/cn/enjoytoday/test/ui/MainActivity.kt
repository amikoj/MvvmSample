package com.worfu.peagang.ui

import androidx.annotation.IntegerRes
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.worfu.peagang.R
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.mvvm.BaseMvvmActivity
import com.worfu.peagang.databinding.ActivityMainBinding
import com.worfu.peagang.ui.act.TestBindingActivity
import com.worfu.peagang.ui.fragment.OtherFragment
import com.worfu.peagang.ui.fragment.TestFragment
import com.worfu.peagang.ui.fragment.TestListFragment
import com.worfu.peagang.viewmodel.MainViewModel
import org.jetbrains.anko.startActivity


const val FRAGMENT_0 = "fragment_0"
const val FRAGMENT_1 = "fragment_1"
const val FRAGMENT_LIST = "fragment_list"
const val DEFAULT = "default"
const val ACTIVITY_0 = "activity_0"
const val WXX_ACTIVITY = "wxx"
@Route(path = RouterPath.UserCenter.PATH_USER_LOGIN)
class MainActivity : BaseMvvmActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    private var currentIndex = DEFAULT
    override fun observe(v: MainViewModel) {

        v.apply {
            clicked.observe(this@MainActivity, Observer {event ->
                if (!event.hasBeenHandled){
                    turn(event.peekContent())
                }

            })
        }
    }



    private fun turn(@IntegerRes id:Int){
        when(id){
            R.id.bindingActivity_0 -> {
                currentIndex = ACTIVITY_0
                startActivity<TestBindingActivity>()
            }
            R.id.bindingFragment_0 ->{
                currentIndex = FRAGMENT_0
                setupFragment(FRAGMENT_0)
            }
            R.id.bindingWx -> {
                currentIndex = WXX_ACTIVITY
//                startActivity<WxxActivity>()
            }
            R.id.bindingFragmentList_0 -> {
                currentIndex = FRAGMENT_LIST
                setupFragment(FRAGMENT_LIST)
            }
        }
    }

    override fun onBackPressed() {
        if (currentIndex == FRAGMENT_0 || currentIndex == FRAGMENT_LIST || currentIndex == FRAGMENT_1){
            viewModel.showFragment.value = false
            currentIndex = DEFAULT
        }else{
            super.onBackPressed()
        }
    }


    /**
     * 加载Fragment
     */
    private fun setupFragment(tag:String){
        viewModel.showFragment.value = true
        supportFragmentManager.beginTransaction()
            .add(R.id.contentFragment,
            when(tag){
                FRAGMENT_0 -> TestFragment.newInstance()
                FRAGMENT_LIST -> TestListFragment.newInstance()
                else -> OtherFragment.newInstance()
            })
            .commit()

    }

}
