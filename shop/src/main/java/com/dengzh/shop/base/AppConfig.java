package com.dengzh.shop.base;

import android.content.Context;

/**
 * Created by dengzh on 2017/10/17 0017.
 * 商城module所需主module的数据配置
 * library注意事项：
 * 1.资源文件不能和主module资源文件重名，即是R.XX文件这种（如布局文件、图片等）
 * 2.要有自己的一套流程，网络框架，基类等等。
 *
 * 在主module中 设置一些相应数据，比如sessionId，username等等
 */

public class AppConfig {

    public static Context mContext;
    public static String curVersion; //主module当前版本
    public static int screenWidth;
    public static int screenHeight;

}
