package cn.enjoytoday.base

import android.view.View
import cn.enjoytoday.base.base.Common
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.utils.SPUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.request.RequestOptions

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