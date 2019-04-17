package com.worfu.peagang

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.amap.api.maps2d.AMap
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.jeremyliao.liveeventbus.LiveEventBus
import com.worfu.message.MyJPushManager
import com.worfu.peagang.base.base.BaseTakePhotoActivity
import com.worfu.peagang.base.base.Common
import com.worfu.peagang.base.toastLongOnUi
import com.worfu.peagang.base.toastShortOnUi
import com.worfu.peagang.base.utils.*
import com.worfu.peagang.base.widget.EasyDialog
import com.worfu.peagang.databinding.ActivityWxxBinding
import com.worfu.peagang.viewmodel.WxxViewModel
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.SchoolPick
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.adapter.OnSchoolPickListener
import com.zaaach.citypicker.db.DBManager
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.LocateState
import com.zaaach.citypicker.model.LocatedCity
import com.zaaach.citypicker.model.School
import kotlinx.android.synthetic.main.activity_wxx.*
import java.util.*


class WxxActivity : BaseTakePhotoActivity<ActivityWxxBinding, WxxViewModel>(), PlatformActionListener {

    private lateinit var wxxViewModel: WxxViewModel

    private lateinit var aMap: AMap

    override fun observe(v: WxxViewModel) {
        wxxViewModel = v
        v.imageUrl.value = "http://img2.imgtn.bdimg.com/it/u=4285587126,3302189430&fm=26&gp=0.jpg"

        wxxViewModel.url.value =
            "读取数据：${SPUtils.getString(Common.LATITUDE)} ${SPUtils.getString(Common.LONGITUDE)} ${SPUtils.getString(
                Common.LOCATION_CITY_NAME
            )}"

        v.apply {

            event.observe(this@WxxActivity, Observer {
                when (it.getContentIfNotHandled()) {
                    // 选择图片
                    R.id.btn1 -> takeList(10, null)
                    // 选择时间
                    R.id.btn2 -> TimePickerBuilder(
                        context,
                        OnTimeSelectListener { date, _ ->
                            wxxViewModel.url.value = "${DateUtils.format(date, DateUtils.FORMAT_SHORT_CN)}"
                        }).build().show()
                    // 选择地区
                    R.id.btn3 -> AddressPickUtils().addressPickView(context) { p1, p2, p3 ->
                        wxxViewModel.url.value = "$p1 $p2 $p3"
                    }
                    // 选择学校
                    R.id.btn4 -> SchoolPick.from(context)
                        .enableAnimation(true)
                        .setOnPickListener(object : OnSchoolPickListener {
                            override fun onPick(position: Int, data: School) {
                                LogUtils.i("name : ${data.region_name} , id : ${data.region_id}")

                                wxxViewModel.url.value = "name : ${data.region_name} , id : ${data.region_id}"

                            }

                            override fun onCancel() {
                                LogUtils.i("取消选择")
                            }
                        })
                        .show()
                    // 定位
                    R.id.btn5 -> {
                        rxPermissions!!
                            .request(Manifest.permission.ACCESS_FINE_LOCATION)
                            .subscribe { granted ->
                                if (granted) { // Always true pre-M
                                    location()
                                } else {
                                    toastLongOnUi("缺少权限")
                                }
                            }
                    }

                    // 选择所在地区
                    R.id.btn6 -> {
                        pickCitys()
                    }

                    // 分享
                    R.id.btn7 -> {
                        ShareUtil.doShare(
                            context,
                            "我在分享",
                            "http://pic1.nipic.com/2008-08-14/2008814183939909_2.jpg",
                            "分享文本",
                            "https://www.jianshu.com/p/158aa484da13"
                        )
                    }

                    // 设置极光别名
                    R.id.btn8 -> {
                        toastShortOnUi("开始设置极光别名")
                        MyJPushManager.setTagAlias("我是极光别名")
                    }

                    // bugly 异常捕获测试
                    R.id.btn9 -> {

                        EasyDialog.Builder(this@WxxActivity).setMessage("此操作将会主动抛出一个异常，并使得app崩溃，确定要这样操作吗？")
                            .setTitle("重要提示").setRightText("确定") { dialog, _ ->
                                dialog.dismiss()
                                throw IllegalAccessException("主动抛出异常")
                            }.setLeftText("取消") { dialog, view ->

                                EasyDialog.Builder(context).setTitle("").setLeftText("").setRightText("知道了")
                                    .setMessage("果然，你很优秀").show()

                                dialog.dismiss()

                            }.show()

                    }

                    // 消息总线
                    R.id.btn10 -> {
                        LiveEventBus.get().with("key_name").post("当前时间：${DateUtils.getNow(DateUtils.FORMAT_LONG_CN)}")
                    }

                    // 微信登录
                    R.id.btn11 -> {
                        loginByWechat()
                    }

                    // qq登录
                    R.id.btn12 -> {
                        loginByQQ()
                    }

                    else -> toastLongOnUi("不知道")
                }
            })
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map.onCreate(savedInstanceState)
        aMap = map.map

        initPermission()


        LiveEventBus.get()
            .with("key_name", String::class.java)
            .observe(context, Observer {
                EasyDialog.Builder(context).setTitle("收到一个新消息").setLeftText("").setRightText("确认收到")
                    .setMessage(it).show()
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }


    override fun takeSuccess(images: List<String>) {

        wxxViewModel.imageUrl.value = images[0]
        wxxViewModel.url.value = images[0]

        // 上传完成后调用,清除缓存
        cleanCache()
    }

    private fun location(isPick: Boolean = false) {

        LogUtils.i("开始定位")

        LocationUtils.instance.getLoacattion(object : LocationUtils.OnLocationChangedListener {
            override fun onSuccess(latitude: Double, longitude: Double, addressstr: String) {
                LogUtils.i("定位成功:$addressstr")
                LocationUtils.instance.stopSingleLocation()

                SPUtils.putString(Common.LATITUDE, "$latitude")
                SPUtils.putString(Common.LONGITUDE, "$longitude")
                SPUtils.putString(
                    Common.LOCATION_CITY_NAME,
                    addressstr.subSequence(0, addressstr.length - 1).toString()
                )

                if (isPick) {
                    val searchCity =
                        DBManager(context).searchCity(addressstr.subSequence(0, addressstr.length - 1).toString())

                    if (searchCity.size > 0) {
                        CityPicker.from(context).locateComplete(
                            LocatedCity(
                                "${addressstr.subSequence(0, addressstr.length - 1)}",
                                searchCity[0].code
                            ), LocateState.SUCCESS
                        )
                    } else {
                        CityPicker.from(context).locateComplete(LocatedCity("错误", "0"), LocateState.FAILURE)
                    }
                } else { // 主动定位
                    wxxViewModel.url.value = addressstr.subSequence(0, addressstr.length - 1).toString()
                }

            }

            override fun onFail(errCode: Int, errInfo: String) {
                LocationUtils.instance.stopSingleLocation()

                LogUtils.i("定位失败： $errCode  $errInfo")

                if (isPick) {
                    CityPicker.from(context).locateComplete(LocatedCity("错误", "0"), LocateState.FAILURE)
                }
            }

        })
    }


    private fun pickCitys() {
        CityPicker.from(this)
            .enableAnimation(true)
//                .setAnimationStyle(R.style.CustomAnim)
            .setLocatedCity(null)
//                .setHotCities(hotCities)
            .setOnPickListener(object : OnPickListener {
                override fun onPick(position: Int, data: City) {
                    wxxViewModel.url.value = data.name
                }

                override fun onCancel() {
//                        toast("取消选择")
                    LogUtils.i("取消选择")
                }

                @SuppressLint("CheckResult")
                override fun onLocate() {
                    //开始定位
                    rxPermissions!!
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe { granted ->
                            if (granted) { // Always true pre-M
                                location(true)
                            } else {
                                CityPicker.from(context).locateComplete(LocatedCity("错误", "0"), LocateState.FAILURE)
                            }
                        }
                }
            })
            .show()
    }

    private fun loginByQQ() {
        val qq = ShareSDK.getPlatform(QQ.NAME)
        qq.platformActionListener = this
        qq.SSOSetting(false)
        if (!qq.isClientValid) {
            toastShortOnUi("QQ未安装,请先安装QQ")
        }
        authorize(qq)
    }

    private fun loginByWechat() {
        val wechat = ShareSDK.getPlatform(Wechat.NAME)
        wechat.platformActionListener = this
        wechat.SSOSetting(false)
        if (!wechat.isClientValid) {
            toastShortOnUi("微信未安装,请先安装微信")
        }
        authorize(wechat)
    }

    private fun authorize(platform: Platform?) {

        if (platform == null) {
            return
        }
        if (platform.isAuthValid) {  //如果授权就删除授权资料
            platform.removeAccount(true)
        }

        platform.showUser(null) //授权并获取用户信息
    }

    override fun onCancel(p0: Platform?, p1: Int) {
        toastShortOnUi("已取消登录")
    }

    override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
      runOnUiThread {
          toastShortOnUi("登录失败，请稍后重试 ${p2.toString()}")
      }
    }

    // 该方法返回在子线程
    override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
       runOnUiThread {
           if (p0?.db?.platformNname == Wechat.NAME) {
               val unitId = p0?.db?.get("unionid")

               unitId?.let {
                   viewModel.url.value = "微信：$it"
               }


           } else if (p0?.db?.platformNname == QQ.NAME) {
               val userId = p0?.db?.get("unionid")

               userId?.let {
                   viewModel.url.value = "QQ：$it"
               }
           }
       }
    }


}
