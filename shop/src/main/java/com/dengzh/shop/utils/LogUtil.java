package com.dengzh.shop.utils;

import android.util.Log;

/**
 * Created by dengzh on 2016/11/15.g
 * 日志工具类
 */

public class LogUtil {

    public static boolean isDebug;


    /**
     * 红色日志
     *
     * @param tag 标记
     * @param msg 消息
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag,msg);
        }
    }

    /**
     * 白色日志
     *
     * @param tag 标记
     * @param msg 消息
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag,msg);
        }
    }
}
