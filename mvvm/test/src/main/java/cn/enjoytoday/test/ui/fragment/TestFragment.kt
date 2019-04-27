package cn.enjoytoday.test.ui.fragment

import cn.enjoytoday.base.mvvm.BaseMvvmFragment
import com.worfu.peagang.databinding.FragmentTestBinding
import cn.enjoytoday.test.viewmodel.TestViewModel

/**
 * @ClassName TestFragment
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/10 10:17
 * @Version 1.0
 */
class TestFragment:BaseMvvmFragment<FragmentTestBinding, TestViewModel>(){


    override fun observe(v: TestViewModel) {

    }


    override fun onResume() {
        super.onResume()
        viewModel.start()
    }



    companion object {
        fun newInstance() = TestFragment()
    }


}