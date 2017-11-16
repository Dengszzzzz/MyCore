package com.dengzh.shop.mvp;

/**
 * Created by Administrator on 2017/9/7 0007.
 *
 */

public abstract class BasePresenter <T extends IBaseView,M extends IBaseModel>{

    protected T mView;
    protected M mModel;

    /**
     * 在BaseActivity 绑定
     * @param v
     * @param m
     */
    public void attachVM(T v,M m) {
        mView = v;
        mModel = m;
        requestNetData();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

    /**
     * 某些页面一开启就要请求网络数据
     * 子类可重写此方法，在V-P-M绑定后调用
     */
    protected void requestNetData(){

    }


}
