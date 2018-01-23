package com.dengzh.sample.net.api;

import com.dengzh.core.net.BaseRespEntity;
import com.dengzh.sample.bean.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by dengzh on 2017/9/8 0008.
 * //创建接口，声明GitHub的API
 */

public interface GitHubAPI {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: RetrofitBean-Sample-App",
            "name:ljd"
    })

    /*
       请求该接口：https://api.github.com/users/baiiu
     */
    @GET("users/{user}")
    Call<User> userInfo(@Path("user") String user);


    @GET("users/{user}")
    Observable<User> userInfoByRxJava(@Path("user") String user);

    @GET("users/{user}")
    Observable<BaseRespEntity<User>> userInfoByRxJava2(@Path("user") String user);

    @GET("users/{user}")
    Observable<User> userInfoByRxJava3(@Path("user") String user);


}
