package com.dengzh.shop.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dengzh on 2017/4/18.
 * 异常捕获类，会打印三次
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "TEST";
    // CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的 Context 对象
    private Context mContext;
    // 系统默认的 UncaughtException 处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    // 用来显示Toast中的信息
    private static String error = "程序错误，额，不对，我应该说，服务器正在维护中，请稍后再试";
    private static final Map<String, String> regexMap = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
            Locale.CHINA);

    private String crash_path;

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        initMap();
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        Log.d("TEST", "Crash:init");
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
           // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
            Log.d("TEST", "defalut");
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            // mDefaultHandler.uncaughtException(thread, ex);
            System.exit(0);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        // collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfoFile(ex);
        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfoFile(Throwable ex) {
        StringBuffer sb = getTraceInfo(ex);
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                //6.0以上 需要申请权限
                String path = Environment.getExternalStorageDirectory() + "/Core/Crash";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if(dir.exists()){
                    FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 整理异常信息
     *
     * @param e
     * @return
     */
    public static StringBuffer getTraceInfo(Throwable e) {
        StringBuffer sb = new StringBuffer();
        Throwable ex = e.getCause() == null ? e : e.getCause();
        StackTraceElement[] stacks = ex.getStackTrace();
        for (int i = 0 ; i < stacks.length; i++) {
            if (i == 0) {
                setError(ex.toString());
            }
            sb.append("class: ").append(stacks[i].getClassName())
                    .append("; method: ").append(stacks[i].getMethodName())
                    .append("; line: ").append(stacks[i].getLineNumber())
                    .append("; Exception: ").append(ex.toString() + "\n");
        }
        Log.d(TAG, sb.toString());
        return sb;
    }

    /**
     * 设置错误的提示语
     *
     * @param e
     */
    public static void setError(String e) {
        Pattern pattern;
        Matcher matcher;
        for (Entry<String, String> m : regexMap.entrySet()) {
            Log.d(TAG, e + "key:" + m.getKey() + "; value:" + m.getValue());
            pattern = Pattern.compile(m.getKey());
            matcher = pattern.matcher(e);
            if (matcher.matches()) {
                error = m.getValue();
                break;
            }
        }
    }

    /**
     * 初始化错误的提示语
     */
    private static void initMap() {
        regexMap.put(".*NullPointerException.*", "NullPointerException");
        regexMap.put(".*ClassNotFoundException.*", "ClassNotFoundException");
        regexMap.put(".*ArithmeticException.*", "ArithmeticException");
        regexMap.put(".*ArrayIndexOutOfBoundsException.*", "ArrayIndexOutOfBoundsException");
        regexMap.put(".*IllegalArgumentException.*", "IllegalArgumentException");
        regexMap.put(".*IllegalAccessException.*", "IllegalAccessException");
        regexMap.put(".*SecturityException.*", "SecturityException");
        regexMap.put(".*NumberFormatException.*", "NumberFormatException");
        regexMap.put(".*OutOfMemoryError.*", "OutOfMemoryError");
        regexMap.put(".*StackOverflowError.*", "StackOverflowError");
        regexMap.put(".*RuntimeException.*", "RuntimeException");
    }
}
