package com.dengzh.shop.net;

import com.dengzh.shop.base.AppConfig;
import com.dengzh.shop.base.ShopModule;
import java.io.File;
import okhttp3.Cache;

/**
 * Created by dengzh on 2017/9/10 0010.
 * Http 的配置信息
 */

public class HttpConfig {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppConfig.mContext.getCacheDir().getAbsolutePath() + File.separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }


    public static String BASE_URL = "https://api.github.com/";
    public static final int TIMEOUT_READ = 20;
    public static final int TIMEOUT_CONNECTION = 10;


}
