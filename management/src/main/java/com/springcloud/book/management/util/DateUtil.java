package com.springcloud.book.management.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期转换工具
 */
public class DateUtil {

    public static void main(String[] args){
        Date startDay = getEndOfDay(DateUtil.parse("2019-01-01","yyyy-MM-dd"));
        String time = DateUtil.format(startDay, "yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
    }

    /**
     * 获取某天的开始时间
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取某天的结束时间
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startDay = localDateTime.with(LocalTime.MAX);
        return Date.from(startDay.atZone(ZoneId.systemDefault()).toInstant());
    }


    //获取该年的第一天
    public static Date getFirstYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,1-1,1,0,0,0);
        return calendar.getTime();
    }

    //获取该年的最后一天
    public static Date getLastYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,12-1,31,23,59,59);
        Date lastYear = calendar.getTime();
        return lastYear;
    }

    public static Date parseSolrDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseYearDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串日期转换
     * @param date  日期字符串
     * @param format    转换格式
     * @return
     */
    public static Date parse(String date,String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date parseDateTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseLongTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatCurrentDateTime(Long num) {
        Date dt = null;
        if (num!=null){
            dt = new Date(num);
        }else {
            dt = new Date(System.currentTimeMillis());
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(dt);
        return currentDate;
    }


    public static String formatCurrentDateTime() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(dt);
        return currentDate;
    }

    public static String formatCurrentDate() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(dt);
        return currentDate;
    }


    public static String formatDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    public static String formatDate1(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        return sf.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(date);
    }

    public static String formatLongTime(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(date);
    }

    public static String formatSolrDataStr(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sf.format(date);
    }

    public static String format(Date date, String dateformat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateformat);
        return sf.format(date);
    }


    public boolean dateEquals(Date date1, Date date2) {
        return date1.getTime() == date2.getTime();
    }

    /**
     * 获取minute分钟前的时间
     * @param date
     * @param minute
     * @return
     */
    public static Date getPreDate(Date date, int minute) {
        long preTime = date.getTime() - minute*60*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(preTime);
        try {
            return sdf.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判定日期date是否过期,date为null表示永不过期
     * @param date
     * @return
     */
    public static boolean isExpire(Date date) {
        if (date == null) {
            return false;
        }
        Date now = new Date();
        if (!now.after(date)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取当前年
     * @return
     */
    public static int getCurrentYear() {
        Calendar date = Calendar.getInstance();
        return date.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的年
     * @return
     */
    public static int getCurrentDateYear(Date d) {
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        return date.get(Calendar.YEAR);
    }


}
