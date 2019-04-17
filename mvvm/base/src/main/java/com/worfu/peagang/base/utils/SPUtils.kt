package com.worfu.peagang.base.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @ClassName SPUtils
 * @Description sharedPreferences管理类 静态类
 * @Author hfcai
 * @Date 2019/4/1 16:11
 * @Version 1.0
 */
object SPUtils{

    private var sp: SharedPreferences =
        Utils.getContext().getSharedPreferences(Utils.getSharePreferencesName(), Context.MODE_PRIVATE)
    private var ed: SharedPreferences.Editor

    init {
        ed = sp.edit()
    }

    /*
        Boolean数据
     */
    fun putBoolean(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.commit()
    }

    /*
        默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    /*
        默认 true
     */
    fun getBooleanDefTrue(key: String): Boolean {
        return sp.getBoolean(key, true)
    }

    /*
        String数据
     */
    fun putString(key: String, value: String) {
        ed.putString(key, value)
        ed.commit()
    }

    /*
        默认 ""
     */
    fun getString(key: String): String {
        return sp.getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return sp.getString(key, defaultValue)
    }

    /*
        Int数据
     */
    fun putInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    /*
        Long数据
     */
    fun putLong(key: String, value: Long) {
        ed.putLong(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    /*
        Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        ed.putStringSet(key, localSet)
        ed.commit()
    }

    /*
        默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sp.getStringSet(key, set)
    }

    /*
        删除key数据
     */
    fun remove(key: String) {
        ed.remove(key)
        ed.commit()
    }

    /**
     * 获取
     */
    public fun <T>get(key:String,clazz: Class<T>):Any?{
        val value:Any?
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
        when (value) {
            is Int -> ed.putInt(key,value).apply()
            is String -> ed.putString(key,value).apply()
            is Boolean -> ed.putBoolean(key,value).apply()
            is Long -> ed.putLong(key,value).apply()
        }
    }
}