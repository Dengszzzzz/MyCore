package com.dengzh.sample.module.gitHubView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.dengzh.sample.R;
import com.dengzh.sample.bean.GoodsTypeBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.GestureLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/1/23.
 */

public class WXPullToRefreshActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.gesture_layout)
    GestureLayout mGestureLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.ac_wx_pulltorefresh;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        List<GoodsTypeBean> markList = new ArrayList<>();
        if (markList == null || markList.isEmpty()) {
            markList.add(new GoodsTypeBean("我的掘金主页", 0));
            markList.add(new GoodsTypeBean("GitHub地址", 0));
            markList.add(new GoodsTypeBean("百度", 0));
        }
    }

}
