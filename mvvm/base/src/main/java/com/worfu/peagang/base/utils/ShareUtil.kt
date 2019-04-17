package com.worfu.peagang.base.utils

import android.content.Context
import cn.sharesdk.onekeyshare.OnekeyShare
import com.worfu.peagang.base.R

/**
 * @author ： summo
 * @version ：1.0
 * @date ：2018/11/13 17:38
 *  <p> 描述: 共享工具类
 *  </p>
 */
object ShareUtil {

    fun doShare(context: Context, tile: String, image: String, text: String, resultUrl: String) {
        val oks = OnekeyShare()
        //关闭sso授权
        oks.disableSSOWhenAuthorize()
//        val imageData = getBitmap(image)
//        oks.setImageData(imageData)
        oks.setImageUrl(image)
        oks.setTitle(tile)
        oks.setTitleUrl(resultUrl)
        oks.text = text
        oks.setUrl(resultUrl)
        oks.setSite(context.getString(R.string.app_name))
        oks.setSiteUrl("http://sharesdk.cn")
        // 启动分享GUI
        oks.show(context)
    }

}