package com.dengzh.core.net;

import com.dengzh.sample.module.base.App;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by dengzh on 2017/9/10 0010.
 * Http 的配置信息
 */

public class BaseHttpConfig {

    private  int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;
    private  int timeoutRead = 20;
    private  int timeoutConnection = 10;
    private  String baseUrl;
    private  String cachePath;

    private static BaseHttpConfig instance;

    private BaseHttpConfig(){}

    public static BaseHttpConfig getInstance(){
        if(instance == null){
            synchronized (BaseHttpConfig.class){
                if(instance == null)
                    instance = new BaseHttpConfig();
            }
        }
        return instance;
    }

    public void init(int timeoutRead, int timeoutConnection, String baseUrl, String cachePath) {
        this.timeoutRead = timeoutRead;
        this.timeoutConnection = timeoutConnection;
        this.baseUrl = baseUrl;
        this.cachePath = cachePath;
    }

    public Cache getCache() {
        return new Cache(new File(cachePath),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }

    public int getTimeoutRead() {
        return timeoutRead;
    }

    public int getTimeoutConnection() {
        return timeoutConnection;
    }

    public String getBaseUrl() {
        return baseUrl;
    }


    /**
     * 建造者模式
     */
    public static class Builder {
        private  int timeoutRead = 20;
        private  int timeoutConnection = 10;
        private  String baseUrl;
        private  String cachePath;

        public Builder setTimeoutRead(int timeoutRead) {
            this.timeoutRead = timeoutRead;
            return this;
        }

        public Builder setTimeoutConnection(int timeoutConnection) {
            this.timeoutConnection = timeoutConnection;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public void build(){
            BaseHttpConfig.getInstance().init(
                    timeoutRead,timeoutConnection,baseUrl,cachePath);
        }
    }

}
