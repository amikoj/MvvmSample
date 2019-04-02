package cn.enjoytoday.backup.user.login

/**
 * @ClassName LoginListener
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/1 18:23
 * @Version 1.0
 */
interface LoginListener {

    /**
     * 登陆
     */
    open fun login()

    /**
     * 忘记密码
     */
    fun forgetPwd()

}