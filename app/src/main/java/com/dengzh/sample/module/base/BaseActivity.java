package com.dengzh.sample.module.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dengzh.core.base.AppManager;
import com.dengzh.core.mvp.BasePresenter;
import com.dengzh.core.mvp.IBaseModel;
import com.dengzh.core.mvp.IBaseView;
import com.dengzh.core.rx.RxBus;
import com.dengzh.core.rx.RxEvents;
import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.utils.TUtil;
import com.dengzh.sample.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/9/7 0007.
 * 基类
 * 1.加入Activity Stack
 * 2.接收子类，填充contentView
 * 3.导航栏统一处理，如置顶
 * 4.打印进入的哪个activity界面
 */

public abstract class BaseActivity<T extends BasePresenter, M extends IBaseModel> extends RxFragmentActivity {

    protected String TAG;
    FrameLayout mainView;
    protected Toolbar toolbar;

    protected T mPresenter;
    private M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_toolbar_base);

        //设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //加入activity管理器
        AppManager.getAppManager().addActivity(this);
        //打印activity信息
        printRunningActivity(this, true);
        //V-P-M 绑定
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof IBaseView) {
            mPresenter.attachVM((IBaseView) this, mModel);
        }

        initBaseUI();
        initUI(savedInstanceState);
        initData();

        //友盟推送
        PushAgent.getInstance(this).onAppStart();
    }

    private void initBaseUI(){
        //1.绑定contentView
        mainView = (FrameLayout) findViewById(R.id.mainView);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        if (view != null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mainView.addView(view);
        }
        ButterKnife.bind(this, mainView);
        //2.标题toolBar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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


    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected abstract int getLayoutId();

    protected abstract void initUI(Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

    /********************************** IBaseView通用接口实现 *********************************/
    public void showLoading(){}
    public void stopLoading(){}



}
