package com.worfu.peagang.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期

 */
object DateUtils {
    /**
     * 英文简写（默认）如：12-01
     */
    var FORMAT_MONTH_DAY = "MM-dd"
    /**
     * 英文简写（默认）如：2010-12-01
     */
    var FORMAT_SHORT = "yyyy-MM-dd"
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    /**
     * 获得默认的 date pattern
     */
    var datePattern = "yyyy-MM-dd HH:mm:ss"

    var FORMAT_LONG_NEW = "yyyy-MM-dd HH:mm"
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    var FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S"
    /**
     * 中文简写 如：2010年12月01日
     */
    var FORMAT_SHORT_CN_MINI = "MM月dd日 HH:mm"
    /**
     * 中文简写 如：2010年12月01日
     */
    var FORMAT_SHORT_CN = "yyyy年MM月dd日"
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    var FORMAT_LONG_CN = "yyyy年MM月dd日 HH时mm分ss秒"
    /**
     * 精确到毫秒的完整中文时间
     */
    var FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"
    /**
     * 精确到毫秒的完整中文时间
     */
    var FORMAT_SPEFULL_CN = "yyyy年MM月dd日 HH:mm"
    /**
     * 英文简写（默认）如：2010-12-01
     */
    var FORMAT_SHORT_SPE = "yyyyMMdd"
    var FORMAT_SHORT_SPE_ = "HH:mm"

    val FORMAT_LONG_POINT = "yyyy.MM.dd HH:mm"

    var TIMEZONE = "Asia/Shanghai"

    /**
     * 根据预设格式返回当前日期

     * @return
     */
    val now: String
        get() = format(Date())

    /**
     * 根据用户格式返回当前日期

     * @param format
     * *
     * @return
     */
    fun getNow(format: String): String {
        return format(Date(), format)
    }


    val defTimeZone: TimeZone
        get() = TimeZone.getTimeZone(TIMEZONE)

    /**
     * 使用用户格式格式化日期

     * @param date
     * *            日期
     * *
     * @param pattern
     * *            日期格式
     * *
     * @return
     */
    @JvmOverloads
    fun format(date: Date?, pattern: String = datePattern): String {
        var returnValue = ""
        if (date != null) {
            val df = SimpleDateFormat(pattern)
            df.timeZone = defTimeZone
            returnValue = df.format(date)
        }
        return returnValue
    }

    /**
     * 使用用户格式提取字符串日期

     * @param strDate
     * *            日期字符串
     * *
     * @param pattern
     * *            日期格式
     * *
     * @return
     */
    @JvmOverloads
    fun parse(strDate: String, pattern: String = datePattern): Date? {
        val df = SimpleDateFormat(pattern)
        df.timeZone = defTimeZone
        try {
            return df.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 时间戳转date str
     */
    fun convertTimeToString(time: Long, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.CHINA)
        sdf.timeZone = defTimeZone

        return sdf.format(time)
    }

    /**
     * 获取当前时间的前一天时间
     * @param cl
     * *
     * @return
     */
    fun getBeforeDay(cl: Calendar): Calendar {
        val day = cl.get(Calendar.DATE)
        cl.set(Calendar.DATE, day - 1)
        return cl
    }

    /**
     * 获取当前时间的后一天时间
     * @param cl
     * *
     * @return
     */
    fun getAfterDay(cl: Calendar): Calendar {
        val day = cl.get(Calendar.DATE)
        cl.set(Calendar.DATE, day + 1)
        return cl
    }

    fun getWeek(c: Calendar): String {
        var Week = ""

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "周天"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "周一"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "周二"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "周三"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "周四"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "周五"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "周六"
        }
        return Week
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    fun dateToString(date: Date, formatType: String): String {
        val sdf = SimpleDateFormat(formatType)
        sdf.timeZone = defTimeZone
        return sdf.format(date)
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    @Throws(ParseException::class)
    fun longToString(currentTime: Long, formatType: String): String {
        val date = longToDate(currentTime, formatType) // long类型转成Date类型
        val strTime = dateToString(date, formatType) // date类型转成String
        return strTime
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    @Throws(ParseException::class)
    fun stringToDate(strTime: String, formatType: String): Date {
        val formatter = SimpleDateFormat(formatType)
        formatter.timeZone = defTimeZone
        var date: Date? = null
        date = formatter.parse(strTime)
        return date
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    @Throws(ParseException::class)
    fun longToDate(currentTime: Long, formatType: String): Date {
        val dateOld = Date(currentTime) // 根据long类型的毫秒数生命一个date类型的时间
        val sDateTime = dateToString(dateOld, formatType) // 把date类型的时间转换为string
        val date = stringToDate(sDateTime, formatType) // 把String类型转换为Date类型
        return date
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    fun stringToLong(strTime: String, formatType: String): Long {
        var date: Date? = null // String类型转成date类型
        try {
            date = stringToDate(strTime, formatType)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (date == null) {
            0
        } else {
            dateToLong(date)
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    fun dateToLong(date: Date): Long {
        return date.time
    }

    //当前时间秒数
    val curTime: Long
        get() {
            val c = Calendar.getInstance(defTimeZone)
            return c.timeInMillis / 1000
        }
    //当前时间毫秒数
    val curMillTime: Long
        get() {
            val c = Calendar.getInstance(defTimeZone)
            return c.timeInMillis
        }


    fun fromNow(oldTime: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = oldTime

        val ago = curTime - oldTime / 1000
        return when {
            ago <= ONE_MINUTE -> "刚刚"
            ago <= ONE_HOUR -> (ago / ONE_MINUTE).toString() + "分钟前"
            ago <= ONE_DAY -> ((ago / ONE_HOUR).toString() + "小时前")
//            ago <= ONE_DAY * 2 -> return ("昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
//                    + calendar.get(Calendar.MINUTE) + "分")
//            ago <= ONE_DAY * 3 -> return ("前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
//                    + calendar.get(Calendar.MINUTE) + "分")
            ago <= ONE_DAY * 7 -> {
                val day = ago / ONE_DAY
                (day.toString() + "天前")
            }

            else -> {
                ("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DATE)}")
            }
        }

    }

    private val ONE_MINUTE: Long = 60
    private val ONE_HOUR: Long = 3600
    private val ONE_DAY: Long = 86400
    private val ONE_MONTH: Long = 2592000
    private val ONE_YEAR: Long = 31104000
}
