package cn.enjoytoday.backup.user.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cn.enjoytoday.backup.user.R
import cn.enjoytoday.backup.user.databinding.ActivityLoginBinding
import cn.enjoytoday.base.base.BaseActivity
import cn.enjoytoday.base.base.RouterPath
import cn.enjoytoday.base.pojo.UserInfo
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path=RouterPath.UserCenter.PATH_USER_LOGIN)
class LoginActivity : BaseActivity() {

    private lateinit var binder:ActivityLoginBinding
    private lateinit var user:UserInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_login)
        user = UserInfo(name = "hfcai",pwd = "hfcai")
        binder.userInfo = user
    }
}
