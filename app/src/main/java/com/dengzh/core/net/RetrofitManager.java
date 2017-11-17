package com.dengzh.core.net;

import com.dengzh.core.utils.InterceptorUtil;
import com.dengzh.core.utils.SSLFactoryUtils;
import com.dengzh.sample.net.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dengzh on 2017/9/10 0010.
 * Retrofit管理类
 */

public class RetrofitManager {

    private static RetrofitManager instance;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance(){
        if(instance == null){
            synchronized (RetrofitManager.class){
                if(instance == null)
                    instance = new RetrofitManager();
            }
        }
        return instance;
    }

    private RetrofitManager(){
        //1.创建okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //SSL证书
                .sslSocketFactory(SSLFactoryUtils.getUnsafeOkHttpClient())
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                //拦截器，addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
                //addinterceptor添加的是aplication拦截器，他只会在response被调用一次。
                .addInterceptor(InterceptorUtil.HeaderInterceptor()) //添加请求头
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                //设置缓存
                .addNetworkInterceptor(InterceptorUtil.CacheInterceptor())  //缓存拦截器
                .addInterceptor(InterceptorUtil.CacheInterceptor())
                .cache(BaseHttpConfig.getInstance().getCache())
                //time out
                .connectTimeout(BaseHttpConfig.getInstance().getTimeoutConnection(), TimeUnit.SECONDS)
                .readTimeout(BaseHttpConfig.getInstance().getTimeoutRead(), TimeUnit.SECONDS)
                .writeTimeout(BaseHttpConfig.getInstance().getTimeoutRead(), TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true)
                .build();

        //2.构造retrofit
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //设置baseUrl,注意，baseUrl必须后缀"/"
                .baseUrl(BaseHttpConfig.getInstance().getBaseUrl())
                //addConverterFactory提供Gson支持，可以添加多种序列化Factory，
                // 但是GsonConverterFactory必须放在最后,否则会抛出异常。
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 考虑到 网络请求可能分多个类写
     * @param reqServer
     * @param <T>
     * @return
     */
    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }
}
