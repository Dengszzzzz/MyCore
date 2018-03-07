package com.dengzh.core.net;


import android.accounts.NetworkErrorException;
import android.content.Context;

import com.dengzh.core.utils.LogUtils;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by dengzh on 2017/9/10 0010.
 * 网络交互 观察者
 */

public abstract class HttpObserver<T> implements Observer<T> {

    public static String TAG = "HttpObserver";

    protected Context mContext;

    public HttpObserver(Context cxt) {
        this.mContext = cxt;
    }

    public HttpObserver() {}

    /**
     * 当观察和被观察者连接到一起（订阅）的时候，就会得到一个Disposable 对象
     * 可用此对象的方法解除订阅关系
     * 首先走这个方法
     * @param d
     */
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onRequestStart();
    }

    /**
     * 当订阅Observable的时候,它有变化或者新的数据的时候就调用
     * 网络请求中使接收到数据后调用
     * @param tBaseEntity
     */
    @Override
    public void onNext(@NonNull T tBaseEntity) {
        onRequestEnd();
        try {
            onSuccees(tBaseEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常死亡
     * 当调用这个方法的时候disposable isDispose()返回的都是true;
     * @param e
     */
    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.e(TAG,e.toString());
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 正常死亡
     * 执行完全部onNext()后执行
     */
    @Override
    public void onComplete() {}


    /*********************************** 调用以下方法即可满足条件 ***********************************/
    protected void onRequestStart() {}
    protected void onRequestEnd() {}

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(T t) throws Exception;


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;





}
