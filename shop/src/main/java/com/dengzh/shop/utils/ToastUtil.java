package com.dengzh.shop.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.dengzh.shop.base.AppConfig;


/**
 * Created by dengzh on 2016/2/15 0015.
 */
public class ToastUtil {

    public static void showToast(String text) {
        showToastAvoidRepeated(text);
    }

    public static void showToast(int resId) {
        showToastAvoidRepeated(resId);
    }

    public static void showToastCenter(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastCenter(Context context, int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 避免Toast重复显示
     * 之前显示的内容
     */
    private static String oldMsg;
    private static Toast toast   = null;
    private static long  oneTime = 0;
    private static long  twoTime = 0;
    public static void showToastAvoidRepeated(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(AppConfig.mContext, message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToastAvoidRepeated(int res) {
        if (StringUtils.isEmpty(AppConfig.mContext.getString(res))) {
            return;
        }
        String message = AppConfig.mContext.getString(res);
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(AppConfig.mContext, message, Toast.LENGTH_SHORT);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (message.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = message;
                    toast.setText(message);
                    toast.show();
                }
            }
            oneTime = twoTime;
        }
    }

}
