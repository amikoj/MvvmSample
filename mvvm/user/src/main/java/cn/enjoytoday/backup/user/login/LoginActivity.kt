package cn.enjoytoday.backup.user.login

import android.os.Bundle
import android.os.Handler
import cn.enjoytoday.backup.user.ForgetActivity
import cn.enjoytoday.base.base.BaseActivity
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.onClick
import cn.enjoytoday.base.pojo.UserInfo
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path=RouterPath.UserCenter.PATH_USER_LOGIN)
class LoginActivity : BaseActivity(),LoginListener{


    override fun login() {
        //登陆


    }

    override fun forgetPwd() {
       startActivity<ForgetActivity>()
    }

//    private lateinit var binder:ActivityLoginBinding
//    private lateinit var binder:
    private lateinit var user:UserInfo

    private val handler = Handler()
    private var pressCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binder = DataBindingUtil.setContentView(this, R.layout.activity_login)
//        user = UserInfo(name = "hfcai",pwd = "hfcai")
//        binder.userInfo = user
//        binder.listener = this
        headerBar.getLeftView().onClick {
            //退出
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        if (pressCount==0){
            toast("再按一次退出")
            handler.postDelayed({
                pressCount++
            },600)
        }else {
            super.onBackPressed()
        }
    }




}
