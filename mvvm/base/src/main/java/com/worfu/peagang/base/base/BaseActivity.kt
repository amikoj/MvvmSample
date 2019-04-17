package com.worfu.peagang.base.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.baidu.mobstat.StatService
import com.gyf.barlibrary.ImmersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.worfu.peagang.base.R


/**
 * activity基类
 */
@SuppressLint("CheckResult")
abstract class BaseActivity : AppCompatActivity() {
    var rxPermissions: RxPermissions? = null

    lateinit var context: AppCompatActivity

    //移动统计名称
    protected var label:String = ""

    // 设置layout视图
    abstract fun inflateLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        //获取manifest配置名
        inflateLayout()
        // 沉浸式状态栏
        ImmersionBar.with(this).statusBarView(R.id.mStatusView).init()
    }

    /**
     * create by http://me.xuxiao.wang on 2019/4/8 0008 上午 10:36
     * 权限管理
     */
    fun initPermission() {
        rxPermissions = RxPermissions(this)
    }


    /**
     * 获取activity获得的类名
     */
    private fun getActivityLabel():String{

        var    label = ""
        try {
            val className = javaClass.canonicalName
            val componentName = ComponentName(packageName, className)
            val activityInfo = packageManager.getActivityInfo(componentName, PackageManager.MATCH_DEFAULT_ONLY)
            label = if (activityInfo.labelRes!=0) {
                getString(activityInfo.labelRes)
            }else{
               ""
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

        return label
    }


    override fun onResume() {
        super.onResume()
        getTagName()
        if (!label.isNullOrEmpty()){
            StatService.onPageStart(applicationContext, label)
        }
    }


    override fun onPause() {
        if (!label.isNullOrEmpty()) {
            StatService.onPageEnd(applicationContext, label)
        }
        super.onPause()
    }


    fun getTagName(){
        findViewById<TextView>(R.id.mHeadBarViewTitle)?.apply {
            label = text.toString()
        }
        if (label.isNullOrEmpty()){
            label = getActivityLabel()
        }
    }



    fun requestCamera() {

        rxPermissions!!
            .request(Manifest.permission.CAMERA)
            .subscribe { granted ->
                if (granted) { // Always true pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            }
    }

    fun requestCameraAndStore() {
        rxPermissions!!
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
            )
            .subscribe { granted ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        ImmersionBar.with(this).destroy()
    }
}