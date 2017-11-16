package com.dengzh.core.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/9/7 0007.
 * MVP 的基本View
 */

public interface IBaseView {
    void showLoading();
    void stopLoading();

    //为了让接口可以调用 RxFragmentActivity的bindToLifecycle()方法
    <T> LifecycleTransformer<T> bindToLifecycle();
}
