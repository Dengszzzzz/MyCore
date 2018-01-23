package com.dengzh.sample.module.gitHubView;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.MyTransition;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/1/23.
 */

public class MyTransitionActivityB extends BaseActivity{

    @BindView(R.id.msgTv)
    TextView msgTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transition_b;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("转场界面B");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        msgTv.setText(getIntent().getStringExtra("msg"));
        executeTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void executeTransition() {
        postponeEnterTransition();

        final View decorView = getWindow().getDecorView();
        getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                supportStartPostponedEnterTransition();
                return true;
            }
        });

        MyTransition transition = new MyTransition();
        transition.setPositionDuration(300);
        transition.setSizeDuration(300);
        transition.setPositionInterpolator(new FastOutLinearInInterpolator());
        transition.setSizeInterpolator(new FastOutSlowInInterpolator());
        transition.addTarget("message");

        getWindow().setSharedElementEnterTransition(transition);
    }
}
