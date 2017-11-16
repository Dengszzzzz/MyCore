package com.dengzh.sample.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dengzh.sample.module.base.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class APPUtils {

    /**
     * 获取当前的应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;

    }

    /**
     * 创建桌面快捷方式
     *
     * @param context
     */
    public static void createShortCut(Context context, int resId) {
        // 发送广播的意图，要创建快捷图标了
        Intent intent = new Intent();
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式 要包含3个重要的信息 1，名称 2.图标 3.干什么事情
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, APPUtils.getAppName(context));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(context.getResources(), resId));
        // 桌面点击图标对应的意图。
        Intent shortcutIntent = new Intent();
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.setClassName(context, context.getClass().getName());
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(intent);
    }

    /**
     * 获取版本号
     *
     * @param isV true V1.0.0 false 1.0.0
     * @return 当前应用的版本号
     * <p/>
     * String
     * @auther snubee
     */
    public static String getVersion(Context context, boolean isV) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info    = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            if (isV) {
                version = String.format("%s%s", "V", version);
            }
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "can not get app version";
        }
    }

    /**
     * 获取渠道
     *
     * @return
     */
    public static String getChannel(Context context) {
        ApplicationInfo appInfo;
        String channel = "http://www.loovee.com";
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo == null || appInfo.metaData == null) return channel;
            String msg = appInfo.metaData.getString("UMENG_CHANNEL");
            if (TextUtils.isEmpty(msg)) return channel;
            channel = msg;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 跳转到应用市场
     *
     * @param context
     */
    public static void goAppStore(Context context) {
        Uri uri        = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + context.getPackageName())));
        }
    }


    /**
     * 跳转到应用市场
     *
     * @param context
     */
    public static void goAppStore(Context context, String packageName) {
        Uri uri        = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + packageName)));
        }
    }


    /**
     * 判断是否安装了某个应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstallPackage(Context context, String packageName) {
        boolean checkResult = false;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo == null) {
                checkResult = false;
            } else {
                checkResult = true;
            }
        } catch (Exception e) {
            checkResult = false;
        }
        return checkResult;
    }


    /**
     * 隐藏键盘
     *
     * @param ac
     */
    public static void hideInputMethod(Activity ac) {
        if (ac.getCurrentFocus() != null) {
            ((InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ac.getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 显示键盘
     */
    public static void showInputMethod(EditText view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.requestFocusFromTouch();
        view.setSelection(view.getText().toString().length());
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }


    /**
     * 跳转到外部浏览器
     *
     * @param context
     * @param url
     */
    public static void goWebByUrl(Context context, String url) {
        Uri uri    = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    /**
     * 把文本复制到粘贴板
     *
     * @param context
     * @param data    复制的文本
     */
    public static void copyToClipboard(Context context, String data) {
        // 调用系统的黏贴版
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip      = ClipData.newPlainText("text", data);
        clipboard.setPrimaryClip(clip);
    }


    public static void setMusicMute(Context context, boolean isMute) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, isMute);
    }

    /**
     * 判断软键盘 弹出
     */
    public static void showSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 比较版本号
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            try {
                throw new Exception("compareVersion error:illegal params.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        if (version1.equals(version2)) {
//            return 0;
//        }
        String[] version1Array = version1.split("//.");  //注意此处为正则匹配，不能用"."；
        String[] version2Array = version2.split("//.");
        int      index         = 0;
        int      minLen        = Math.min(version1Array.length, version2Array.length);  //取最小长度值
        int      diff          = 0;
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 获取MD5值
     *
     * @param content
     * @return
     */
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取hash字符串
     *
     * @param digest
     * @return
     */
    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    /**
     * 测试渠道号是否打入
     *
     * @param name
     * @return
     */
    public static String getApplicationMetaValue(String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = App.ctx.getPackageManager().getApplicationInfo(App.ctx.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info    = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager     = context.getPackageManager();
            PackageInfo info        = manager.getPackageInfo(context.getPackageName(), 0);
            int            versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    /**
     * 根据时间格式获取时间戳
     * yyyy-MM-dd HH:mm:ss SSS
     */
    public static long getData(String dateFormat, String data) {
        long d = 0;
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateFormat);
            Date newData        = dateTimeFormat.parse(data);
            d = newData.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 5.0以上版本手机动态设置通知栏颜色的方法
     *
     * @param activity 需要设置颜色的Activity
     * @param colorRes 设置的颜色
     */
    public static void setStatusBarColor(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusColor = activity.getResources().getColor(colorRes);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(statusColor);
        }
    }

}
