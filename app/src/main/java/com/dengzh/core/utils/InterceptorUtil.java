package com.dengzh.core.utils;

import com.dengzh.sample.module.base.App;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by dengzh on 2017/9/10 0010.
 * 拦截器工具类
 * okhttp 设置各种拦截器 如日志打印、请求头、缓存
 */

public class InterceptorUtil {

    public static String TAG = "InterceptorUtil";

    /**
     * 日志拦截器
     * @return
     */
    public static HttpLoggingInterceptor LogInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e(TAG,"http-log: "+ message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY); //设置打印数据的级别
    }

    /**
     * 请求头配置
     * @return
     */
    public static Interceptor HeaderInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest=chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等
                Request.Builder builder = mRequest.newBuilder();
                builder.addHeader("isapp","true");

                return chain.proceed(builder.build());
            }
        };
    }

    /**
     * 缓存配置
     * 有网络的时候我们获取网络的数据或者自己设置一定的缓存，没有网络的时候我们获取缓存的数据。
     * @return
     */
    public static Interceptor CacheInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isConnected(App.ctx)){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtils.isConnected(App.ctx)) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
    }


}
