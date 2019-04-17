package com.worfu.peagang.base.annotation

import java.lang.reflect.Method

/**
 * @ClassName AnnotationInfo
 * @Description 解析注解类信息
 * @Author hfcai
 * @Date 2019/4/16 11:00
 * @Version 1.0
 */
data class AnnotationInfo (var name:String,val clazz: Class<*>,var list:MutableList<Method>)