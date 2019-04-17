package cn.enjoytoday.base

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.request.RequestOptions
import cn.enjoytoday.base.base.Common
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.utils.*


const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(T::class.java)


fun <T : ViewModel> AppCompatActivity.obtainViewModel(clazz: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(clazz)


/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}


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
fun View.onClick(method: () -> Unit) {
    this.setOnClickListener {
        method()
    }
}

/**
 * View扩展，是否显示view
 * */
fun View.visibility(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
}

/**
 * 登陆后调用
 */
fun afterlogin(method: () -> Unit) {

    val token = SPUtils.getInstance()?.getString(Common.TOKEN)
    if (token.isNullOrEmpty()) {
        //当前不是登陆状态
        ARouter.getInstance()
            .build(RouterPath.UserCenter.PATH_USER_LOGIN)
            .navigation()
    } else {
        //用户已登陆
        method()
    }

}










