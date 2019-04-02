package cn.enjoytoday.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import cn.enjoytoday.base.base.Common
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.utils.SPUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.request.RequestOptions
import cn.enjoytoday.base.utils.StatusBarUtil


/**
 * glide 拓展，设置glide加载的大小
 */
fun RequestOptions.miniThumb(size: Int): RequestOptions {
    return this.fitCenter()
            .override(100)
}


/**
 * View扩展
 */
fun View.onClick(method:()->Unit){
    this.setOnClickListener {
        method()
    }
}



/**
 * 登陆后调用
 */
fun  afterlogin(method: () -> Unit){
    SPUtils.getInstance()?.let {
        val token = it.get(Common.TOKEN,String::class.java) as String
        if (token.isNullOrEmpty()){
            //当前不是登陆状态
            ARouter.getInstance()
                    .build(RouterPath.UserCenter.PATH_USER_LOGIN)
                    .navigation()
        }else{
            //用户已登陆
            method()
        }
    }
}


/**
 * 通过反射的方式获取状态栏高度
 *
 * @return
 */
@SuppressLint("PrivateApi")
fun Activity.getStatusBarHeight(): Int {
    try {
        val c = Class.forName("com.android.internal.R\$dimen")
        val obj = c.newInstance()
        val field = c.getField("status_bar_height")
        val x = Integer.parseInt(field.get(obj).toString())
        return resources.getDimensionPixelSize(x)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}

fun Activity.initStatus(){

    StatusBarUtil.setRootViewFitsSystemWindows(this,true)
    //设置状态栏透明
    StatusBarUtil.setTranslucentStatus(this)
    //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
    //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
    if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
        //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
        //这样半透明+白=灰, 状态栏的文字能看得清
        StatusBarUtil.setStatusBarColor(this,resources.getColor(R.color.colorWhite))
    }
}


///**
// * 跳转Activity扩展
// */
//fun <T :Activity>Context.startActivity(clazz: Class<T>,vararg pair: Pair<String,String>){
//    if (clazz is Activity){
//        val intent = Intent(this.applicationContext,clazz)
//        pair.forEachIndexed { _, p ->
//            intent.putExtra(p.first,p.second)
//        }
//        startActivity(intent)
//    }
//}













