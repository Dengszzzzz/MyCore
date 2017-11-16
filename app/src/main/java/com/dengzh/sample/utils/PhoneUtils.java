package com.dengzh.sample.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by dengzh on 2017/11/2.
 */

public class PhoneUtils {

    /**
     * 获取设备id
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
