package com.dengzh.sample.mvp.model.modelImp;

import com.dengzh.core.net.HttpObserver;
import com.dengzh.core.net.RetrofitManager;
import com.dengzh.core.utils.RxUtil;
import com.dengzh.sample.bean.User;
import com.dengzh.sample.mvp.model.modelInter.IGithubModel;
import com.dengzh.sample.net.api.GitHubAPI;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;


/**
 * Created by dengzh on 2017/9/12 0012.
 * 两个封装模式对比
 */

public class GithubModel implements IGithubModel{

    @Override
    public Observable<User> requestUserInfo(String user) {
        return RetrofitManager.getInstance()
                .createReq(GitHubAPI.class)
                .userInfoByRxJava3(user) //到此是找到Observable对象
                .compose(RxUtil.<User>rxSchedulerHelper()); //此处封装访问在io线程，回调在主线程
    }

    @Override
    public Observable<User> requestUserInfo3(String user,LifecycleTransformer<User> life) {
        return RetrofitManager.getInstance()
                .createReq(GitHubAPI.class)
                .userInfoByRxJava3(user) //到此是找到Observable对象
                .compose(RxUtil.rxSchedulerHelper(life)); //此处封装访问在io线程，回调在主线程,绑定生命周期
    }

    @Override
    public void requestUserInfo2(String user,LifecycleTransformer<User> life, HttpObserver<User> observer) {
        RetrofitManager.getInstance()
                .createReq(GitHubAPI.class)
                .userInfoByRxJava3(user)   //到此是找到Observable对象
                .compose(RxUtil.rxSchedulerHelper(life))
                .subscribe(observer);
    }

}
