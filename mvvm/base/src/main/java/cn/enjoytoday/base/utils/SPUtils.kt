package cn.enjoytoday.base.utils

import android.annotation.SuppressLint
import android.content.Context
import cn.enjoytoday.base.ViewModelFactory
import cn.enjoytoday.base.base.Common

/**
 * @ClassName SPUtils
 * @Description TODO
 * @Author hfcai
 * @Date 2019/4/1 16:11
 * @Version 1.0
 */
class SPUtils private constructor(val context: Context,val SP_NAME:String = Common.APP_SP_NAME){




    public fun putString(key:String,value:String){
        context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit()
                .putString(key,value)
                .apply()
    }

    public fun getString(key:String):String?{
       return context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .getString(key,null)

    }


    /**
     * 获取
     */
    public fun <T>get(key:String,clazz: Class<T>):Any?{
        val sp= context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
        var value:Any? =null
        when(clazz){
            String::class.java ->{
                value = sp.getString(key,null)
            }

            Int::class.java ->{
                value = sp.getInt(key,-1)
            }

            Long::class.java ->{
                value = sp.getLong(key,-1)
            }
            Boolean::class.java -> {
                value = sp.getBoolean(key,false)
            }
            else -> value=null
        }
        return value
    }


    /**
     * 设置
     */
    public fun put(key:String,value:Any){
        val sp= context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
        when (value) {
            is Int -> sp.edit().putInt(key,value).apply()
            is String -> sp.edit().putString(key,value).apply()
            is Boolean -> sp.edit().putBoolean(key,value).apply()
            is Long -> sp.edit().putLong(key,value).apply()
        }
    }






    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SPUtils?=null


        fun init(context: Context,name:String){
            if (SPUtils.INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (SPUtils.INSTANCE == null) {
                        SPUtils.INSTANCE = SPUtils(context.applicationContext,name)
                    }
                }
            }
        }

        fun getInstance(): SPUtils? {
            return SPUtils.INSTANCE
        }
    }
}