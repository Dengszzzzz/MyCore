package com.dengzh.sample.net.api;

import com.dengzh.core.net.BaseRespEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dengzh on 2017/9/12 0012.
 */

public interface RegisterApi {

    @GET("app/is_phone_register.htm")
    Observable<BaseRespEntity<String>> reqIsPhoneRegister(@Query("mobile") String mobile);



}
