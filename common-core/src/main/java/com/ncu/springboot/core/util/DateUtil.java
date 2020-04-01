package com.ncu.springboot.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dengwei06015 on 2016/1/20.
 */
public class DateUtil {
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String MM_DD = "MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String MM_DD_EN = "MM/dd";
    public static final String YYYY_MM_EN = "yyyy/MM";
    public static final String YYYY_MM_DD_EN = "yyyy/MM/dd";
    public static final String MM_DD_HH_MM_EN = "MM/dd HH:mm";
    public static final String MM_DD_HH_MM_SS_EN = "MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_EN = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_EN = "yyyy/MM/dd HH:mm:ss";
    public static final String MM_DD_CN = "MM月dd日";
    public static final String YYYY_MM_CN = "yyyy年MM月";
    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";
    public static final String MM_DD_HH_MM_CN = "MM月dd日 HH:mm";
    public static final String MM_DD_HH_MM_SS_CN = "MM月dd日 HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String YYYY_MM_DD_EEEE_HH_MM_SS_CN = "yyyy年MM月dd日 EEEE HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYMMDDHHMMSSFFF = "yyMMddHHmmssSSS";
    public static final String year = "yyyy";
    public static final String month = "MM";
    public static final String day = "dd";

    public static final SimpleDateFormat getYyyyMMddHHmmssSDF() {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    }

    public static final SimpleDateFormat getYmdSDF() {
        return new SimpleDateFormat(YYYY_MM_DD);
    }

    public static final SimpleDateFormat getYearSDF() {
        return new SimpleDateFormat(year);
    }

    public static final SimpleDateFormat getMonthSDF() {
        return new SimpleDateFormat(month);
    }

    public static final SimpleDateFormat getDaySDF() {
        return new SimpleDateFormat(day);
    }

    public static final SimpleDateFormat getYyyyMMddHHmmSDF() {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM);
    }

    public static final SimpleDateFormat getYyyyMMddSDF() {
        return new SimpleDateFormat(YYYY_MM_DD);
    }

    public static final SimpleDateFormat getYyyyMMddHH_NOT_() {
        return new SimpleDateFormat(YYYYMMDD);
    }

    public static final long DATEMM = 86400L;

    private static final String ERR_MSG_INVALID_FORMAT = "Invalid format.";

    private static final String ERR_MSG_PARSE_FAILED = "Parse date failed.";

    public static Date cloneDate(Date date) {
        return null == date ? null : (Date) date.clone();
    }

    public static Date cloneDate(Date date, Date defaultDate) {
        return null == date ? cloneDate(defaultDate) : cloneDate(date);
    }

    /**
     * 获得当前时间
     * 格式：2014-12-02 10:38:53
     *
     * @return String
     */
    public static final String getCurrentTime() {
        return DateUtil.getCurrentTime(DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定格式当前时间
     *
     * @param format
     * @return
     */
    public static final String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 可以获取昨天的日期
     * 格式：2014-12-01
     *
     * @return String
     */
    public static final String getYesterdayYYYYMMDD() {
        SimpleDateFormat yyyyMMddSDF = getYyyyMMddSDF();
        Date date = new Date(System.currentTimeMillis() - DATEMM * 1000L);
        String str = yyyyMMddSDF.format(date);
        try {
            date = getYyyyMMddHHmmssSDF().parse(str + " 00:00:00");
            return yyyyMMddSDF.format(date);
        } catch (ParseException e) {
        }
        return "";
    }

    /**
     * 可以获取后退N天的日期
     * 格式：传入2 得到2014-11-30
     *
     * @param backDay
     * @return String
     */
    public String getStrDate(String backDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt("-" + backDay));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String back = sdf.format(calendar.getTime());
        return back;
    }

    /**
     * 获取当前的年、月、日
     *
     * @return String
     */
    public static final String getCurrentYear() {
        return getYearSDF().format(new Date());
    }

    public static final String getCurrentMonth() {
        return getMonthSDF().format(new Date());
    }

    public static final String getCurrentDay() {
        return getDaySDF().format(new Date());
    }

    /**
     * 获取年月日 也就是当前时间
     * 格式：2014-12-02
     *
     * @return String
     */
    public static final String getCurrentymd() {
        return getYmdSDF().format(new Date());
    }

    /**
     * 获取今天0点开始的秒数
     *
     * @return long
     */
    public static long getTimeNumberToday() {
        SimpleDateFormat yyyyMMddSDF = getYyyyMMddSDF();
        Date date = new Date();
        String str = yyyyMMddSDF.format(date);
        try {
            date = yyyyMMddSDF.parse(str);
            return date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取今天的日期
     * 格式：20141202
     *
     * @return String
     */
    public static final String getTodateStr() {
        return getYyyyMMddHH_NOT_().format(new Date());
    }

    /**
     * 获取昨天的日期
     * 格式：20141201
     *
     * @return String
     */
    public static final String getYesterdayStr() {
        Date date = new Date(System.currentTimeMillis() - DATEMM * 1000L);
        return getYyyyMMddHH_NOT_().format(date);
    }

    /**
     * 获得昨天零点
     *
     * @return Date
     */
    public static Date getYesterDayZeroHour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }

    /**
     * 把long型日期转String
     *
     * @param date   long型日期；
     * @param format 日期格式；
     * @return
     */
    public static final String longToStr(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt2 = new Date(date * 1000L);
        String sDateTime = sdf.format(dt2); // 得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    /**
     * 获得今天零点
     *
     * @return Date
     */
    public static Date getTodayZeroHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }

    /**
     * 获得昨天23时59分59秒
     *
     * @return
     */
    public static Date getYesterDay24Hour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR, 23);
        return cal.getTime();
    }

    /**
     * String To Date
     *
     * @param date   待转换的字符串型日期；
     * @param format 转化的日期格式
     * @return 返回该字符串的日期型数据；
     */
    public static Date strToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Date To String
     *
     * @param date   待转换的日期；
     * @param format 转化的日期格式
     * @return 返回该字符串的日期型数据；
     */
    public static final String dateToStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获得指定日期所在的自然周的第一天，即周日
     *
     * @param date 日期
     * @return 自然周的第一天
     */
    public static Date getStartDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在的自然周的最后一天，即周六
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 7);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在当月第一天
     *
     * @param date
     * @return
     */
    public static Date getStartDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期的下一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getStartDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期的下一个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 2);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 求某一个时间向前多少秒的时间(currentTimeToBefer)
     *
     * @param givedTime        给定的时间
     * @param interval         间隔时间的毫秒数；计算方式 ：n(天)*24(小时)*60(分钟)*60(秒)(类型)
     * @param format_Date_Sign 输出日期的格式；如yyyy-MM-dd、yyyyMMdd等；
     */
    public static final String givedTimeToBefer(String givedTime, long interval,
                                                String format_Date_Sign) {
        String tomorrow = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_Date_Sign);
            Date gDate = sdf.parse(givedTime);
            long current = gDate.getTime(); // 将Calendar表示的时间转换成毫秒
            long beforeOrAfter = current - interval * 1000L; // 将Calendar表示的时间转换成毫秒
            Date date = new Date(beforeOrAfter); // 用timeTwo作参数构造date2
            tomorrow = new SimpleDateFormat(format_Date_Sign).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tomorrow;
    }

    /**
     * 把String 日期转换成long型日期，单位毫秒
     *
     * @param date   String 型日期；
     * @param format 日期格式；
     * @return
     */
    public static long strToLong(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt2 = null;
        long lTime = 0;
        try {
            dt2 = sdf.parse(date);
            lTime = dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lTime;
    }

    /**
     * 得到两个日期间的之间的日期；
     *
     * @param endTime   结束时间
     * @param beginTime 开始时间
     * @param isEndTime 是否包含结束日期；
     * @return
     */
    public static List<String> getTwoDay(String beginTime, String endTime, boolean isEndTime) {
        List<String> result = new ArrayList<String>();
        if (StringUtil.isEmpty(endTime) || StringUtil.isEmpty(beginTime)) {
            return null;
        }
        try {
            SimpleDateFormat ymdSDF = getYmdSDF();
            Date date = ymdSDF.parse(endTime);
            endTime = ymdSDF.format(date);
            Date mydate = ymdSDF.parse(beginTime);
            long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
            result = getDate(endTime, Integer.parseInt(day + ""), isEndTime);
        } catch (Exception e) {
        }
        return result;
    }


    /**
     * 得到二个日期间的间隔天数；
     *
     * @param endTime   结束时间
     * @param beginTime 开始时间
     * @param isEndTime 是否包含结束日期；
     * @return
     */
    public static Integer getTwoDayInterval(String beginTime, String endTime, boolean isEndTime, String format) {
        if (StringUtil.isEmpty(endTime) || StringUtil.isEmpty(beginTime))
            return 0;
        return getTwoDayInterval(DateUtil.strToDate(beginTime, format), DateUtil.strToDate(endTime, format), isEndTime);
    }

    /**
     * 得到二个日期间的间隔天数；
     *
     * @param endTime   结束时间
     * @param beginTime 开始时间
     * @param isEndTime 是否包含结束日期；
     * @return
     */
    public static Integer getTwoDayInterval(Date beginTime, Date endTime, boolean isEndTime) {
        if (endTime == null || beginTime == null)
            return 0;
        long day = 0L;
        try {
            day = (endTime.getTime() - beginTime.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0;
        }
        return Integer.parseInt(day + "");
    }

    /**
     * 根据结束时间以及间隔差值，求符合要求的日期集合；
     *
     * @param endTime
     * @param interval
     * @param isEndTime
     * @return
     */
    public static List<String> getDate(String endTime, Integer interval, boolean isEndTime) {
        List<String> result = new ArrayList<String>();
        if (isEndTime && (interval == 0 || isEndTime)) {
            result.add(endTime);
        }
        if (interval > 0) {
            int begin = 0;
            for (int i = begin; i < interval; i++) {
                endTime = givedTimeToBefer(endTime, DATEMM, YYYY_MM_DD);
                result.add(endTime);
            }
        }
        return result;
    }

    public static Date toDateOrNull(String dateString, String format) {
        if (StringUtil.isBlank(dateString)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    public static final String toStringOrNull(Date date, String format) {
        if (null == date) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}