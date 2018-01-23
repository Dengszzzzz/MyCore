package com.dengzh.sample.mvp.model.modelInter;

import com.dengzh.core.mvp.IBaseModel;
import com.dengzh.core.net.HttpObserver;
import com.dengzh.sample.bean.User;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;

/**
 * Created by dengzh on 2017/9/12 0012.
 */

public interface IGithubModel extends IBaseModel {

    Observable<User> requestUserInfo(String user);

    Observable<User> requestUserInfo3(String user,LifecycleTransformer<User> life);

    void requestUserInfo2(String user,LifecycleTransformer<User> life, HttpObserver<User> observer);

}
