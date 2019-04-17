package com.worfu.peagang.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.enjoytoday.base.mvvm.BaseMvvmFragment
import com.worfu.peagang.databinding.FragmentJobListBinding
import com.worfu.peagang.ui.TestListAdapter
import com.worfu.peagang.viewmodel.TestListViewModel
import kotlinx.android.synthetic.main.fragment_job_list.*
import me.jessyan.autosize.utils.LogUtils

/**
 * @ClassName TestListFragment
 * @Description
 *
 * @Author hfcai
 * @Date 2019/4/11 18:04
 * @Version 1.0
 */
class TestListFragment:BaseMvvmFragment<FragmentJobListBinding,TestListViewModel>() {


    private lateinit var  jobAdapter:TestListAdapter

    /**
     * 观察处理
     */
    override fun observe(v: TestListViewModel) {
//        v.apply {
//            jobList.observe(this@TestListFragment, Observer {
//                if (jobRecyclerView!=null) {
//                    val count = jobRecyclerView.childCount
//                    if (count == 0) {
//                        jobAdapter.setNewData(it.data.list)
//                    } else {
//                        jobAdapter.addData(it.data.list)
//                    }
//                }
//            })
//        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()

        with(viewModel){
            jobList.observe(this@TestListFragment, Observer {
                if (jobRecyclerView!=null) {
                    val count = jobRecyclerView.childCount
                    if (count == 0) {
                        jobAdapter.setNewData(it.data.list)
                    } else {
                        jobAdapter.addData(it.data.list)
                    }
                }
            })
        }
    }



    private fun setupAdapter(){
        jobAdapter = TestListAdapter()
        jobRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jobAdapter
        }
    }



    companion object {
        fun newInstance()= TestListFragment()
    }


}