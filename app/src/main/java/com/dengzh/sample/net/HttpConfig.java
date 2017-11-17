package com.dengzh.sample.net;

import com.dengzh.core.net.BaseHttpConfig;
import com.dengzh.sample.module.base.App;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by dengzh on 2017/9/10 0010.
 * Http 的配置信息
 */

public class HttpConfig {

    public static String BASE_URL = "https://api.github.com/";
    public static final int TIMEOUT_READ = 20;
    public static final int TIMEOUT_CONNECTION = 10;
    public static String cachePath = App.ctx.getCacheDir().getAbsolutePath() + File.separator + "data/NetCache";


}
