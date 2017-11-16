package com.dengzh.shop.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dengzh.shop.base.AppManager;
import com.dengzh.shop.mvp.BasePresenter;
import com.dengzh.shop.mvp.IBaseModel;
import com.dengzh.shop.mvp.IBaseView;
import com.dengzh.shop.utils.LogUtil;
import com.dengzh.shop.utils.TUtil;
import com.dengzh.shop.utils.rx.RxBus;
import com.dengzh.shop.utils.rx.RxEvents;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/9/7 0007.
 * 基类
 * 1.加入Activity Stack
 * 2.接收子类，填充contentView
 * 3.导航栏统一处理，如置顶
 * 4.打印进入的哪个activity界面
 */

public abstract class BaseActivity<T extends BasePresenter, M extends IBaseModel> extends RxFragmentActivity {

    protected T mPresenter;
    private M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
        initUI(savedInstanceState);
        initData();
    }

    private void init(){
        //1.加入管理栈
        AppManager.getAppManager().addActivity(this);
        //2.打印activity信息
        printRunningActivity(this, true);
        //3.V-P-M 绑定
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof IBaseView) {
            mPresenter.attachVM((IBaseView) this, mModel);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initUI(Bundle savedInstanceState);

    protected abstract void initData();


    /**
     * 打印activity信息
     *
     * @param ac
     * @param isRunning
     */
    protected void printRunningActivity(Activity ac, boolean isRunning) {
        String contextString = ac.toString();
        String s = contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
        if (isRunning) {
            LogUtil.d("Activity", "app:当前正在加入的界面是:" + s);
        } else {
            LogUtil.d("Activity", "app:当前销毁的界面是:" + s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MVP解绑
        if (mPresenter != null) mPresenter.detachVM();
        //RxBus解绑
        RxBus.getIntanceBus().unSubscribe(this);
        //移除activity
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 注册RxBus
     * @param action
     * @param <T>
     */
    public <T> void registerRxBus(Consumer<RxEvents> action) {
        Disposable disposable = RxBus.getIntanceBus().doSubscribe(RxEvents.class, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtil.e("RxBusAccept", throwable.toString());
            }
        });
        RxBus.getIntanceBus().addSubscription(this,disposable);
    }



}
