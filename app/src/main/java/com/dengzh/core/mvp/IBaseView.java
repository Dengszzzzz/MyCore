package com.dengzh.core.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/9/7 0007.
 * MVP 的基本View
 * 1.推荐 各个view共用的方法在BaseActivity 以同样命名写实现方法
 * 这样每个IView引用可以使用这些方法，而不用每个view都写showLoading()这些
 */

public interface IBaseView {
    void showLoading();   //显示加载框
    void stopLoading();   //取消加载框

    //为了让接口可以调用 RxFragmentActivity的bindToLifecycle()方法
    <T> LifecycleTransformer<T> bindToLifecycle();
}
