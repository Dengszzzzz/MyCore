package com.dengzh.sample.utils;

import android.content.Context;

import com.dengzh.sample.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dengzh on 2018/1/5.
 */

public class TimeUtils {


    public static long SECOND = 1;
    public static long MINUTE = 60 * SECOND;
    public static long HOUR = 60 * MINUTE;
    public static long DAY = 24 * HOUR;
    public static long MONTH = 30 * DAY;
    public static long YEAR = 12 * MONTH;

    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static String TIME_FORMAT_Day = "yyyy-MM-dd";
    public static String TIME_FORMAT_OTHER = "MM月dd日 HH:mm";
    public static String TIME_FORMAT_TODAY = "HH:mm";

    /**
     * 获取时间格式
     * @param time 时间  秒
     * @return HH:mm
     */
    public static String getTimeFormatToday(long time){
        return new SimpleDateFormat(TIME_FORMAT_TODAY).format(new Date(time));
    }

    /**
     * 获取时间格式
     * @param time 时间  秒
     * @return MM月dd日 HH:mm
     */
    public static String getTimeFormatOther(long time){
        return new SimpleDateFormat(TIME_FORMAT_OTHER).format(new Date(time));
    }

    /**
     * 获取时间格式
     * @param time 时间  秒
     * @return yyyy-MM-dd HH:mm
     */
    public static String getTimeFormat(long time){
        return new SimpleDateFormat(TIME_FORMAT).format(new Date(time));
    }

    /**
     * 获取时间格式
     * @param time 时间  秒
     * @return yyyy-MM-dd
     */
    public static String getTimeFormatDay(long time){
        return new SimpleDateFormat(TIME_FORMAT_Day).format(new Date(time));
    }


    /**
     * 获取时间间隔格式  例：5分钟前 ，5小时前 ，5天前，5月前，2016年
     * @param
     * @return
     */
    public static String getTimeDistance(Context context, long time){
        String distanceTime = null;
        long distanceTimes = System.currentTimeMillis() / 1000 - time / 1000;
        if(distanceTimes < HOUR){
            distanceTime = distanceTimes / MINUTE + context.getResources().getString(R.string.time_before_minute);
        }else if(distanceTimes < DAY){
            distanceTime = distanceTimes / HOUR + context.getResources().getString(R.string.time_before_hour);
        }else if(distanceTimes < MONTH){
            distanceTime = distanceTimes / DAY + context.getResources().getString(R.string.time_before_day);
        }else if(distanceTimes < YEAR){
            distanceTime = distanceTimes / MONTH + context.getResources().getString(R.string.time_before_month);
        }else if(distanceTimes >= YEAR){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(time));
            distanceTime = calendar.get(Calendar.YEAR) + context.getResources().getString(R.string.time_year);
        }
        return distanceTime;
    }


    /**
     * 根据秒数转化为时分秒   00:00:00
     * @param second
     */
    public static String changeFormSecond(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }

}
