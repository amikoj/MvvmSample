package com.worfu.peagang.base.utils

import android.text.TextUtils
import com.worfu.peagang.base.base.Common
import java.security.MessageDigest
import java.util.*



/**
 * @author ： summo
 * @version ：1.0
 * @date ：2018/8/1 8:23
 *  <p> 描述:
 *  </p>
 */
/**
 * @author : sumao
 * @version : 1.0
 * @date : 2018/6/1 下午9:50
 *  EncodeUtils describe: 字符串加密
 */
object EncodeUtils {

    /**
     * MD5 加密
     *
     * @param targetStr 被加密的字段
     * @param fixed 混淆字段
     * @return
     * @throws Exception
     */
    fun md5Encode(targetStr: String, fixed: String): String {
        val target = String.format("%s#%s", fixed, targetStr)
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest!!.reset()
            val byteArray: ByteArray = messageDigest.digest(target.toByteArray(charset("UTF-8")))
            val md5StrBuff = StringBuffer()
            for (b in byteArray) {
                val i: Int = b.toInt() and 0xff
                var hexString = Integer.toHexString(i)
                if (hexString.length == 1) {
                    hexString = "0$hexString"
                }
                md5StrBuff.append(hexString)
            }
            return md5StrBuff.toString()
        } catch (e: Exception) {
            LogUtils.e("MD5转换异常！" + e.message)
        }
        return ""
    }

    /**
     * MD5 加密
     *
     * @param targetStr 被加密的字段
     * @param fixed 混淆字段
     * @return
     * @throws Exception
     */
    fun md5Encode(targetStr: String): String {
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest!!.reset()
            val byteArray: ByteArray = messageDigest.digest(targetStr.toByteArray(charset("UTF-8")))
            val md5StrBuff = StringBuffer()
            for (b in byteArray) {
                val i: Int = b.toInt() and 0xff
                var hexString = Integer.toHexString(i)
                if (hexString.length == 1) {
                    hexString = "0$hexString"
                }
                md5StrBuff.append(hexString)
            }
            return md5StrBuff.toString()
        } catch (e: Exception) {
            LogUtils.e("MD5转换异常！" + e.message)
        }
        return ""
    }


    fun snEncode(data: TreeMap<*, *>): String {
       return md5Encode(toQueryString(data)).substring(8,24).toUpperCase()
    }

    /**
     * SN算法
     */
    fun snEncode(obj: Any): String {
        val fields = obj.javaClass.declaredFields
        val data = TreeMap<String,Any>()
        for (field in fields) {
            try {
                field.isAccessible = true
                val key = field.name
                val value = field.get(obj)
                if (key != null && value != null && !TextUtils.isEmpty(key) ) {
                    data[key] = value
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return md5Encode(toQueryString(data)).substring(8,24).toUpperCase()
    }


    /**
     * 获取请求的算法
     * @param data 参数
     * @return
     */
    private fun toQueryString(data: TreeMap<*, *>): String {
        val queryString = StringBuffer()
        for (key in data.keys) {
            queryString.append(key.toString() + "=")
            queryString.append(data[key].toString()+ "&")
//            LogX.i("key")
        }
        queryString.append("key="+ Common.APP_KEY_WANDOUJOB)
        return queryString.toString()
    }
}
