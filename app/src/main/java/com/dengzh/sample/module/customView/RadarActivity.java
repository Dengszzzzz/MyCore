package com.dengzh.sample.module.customView;

import android.os.Bundle;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.study.RadarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/10/31.
 * 雷达图
 */

public class RadarActivity extends BaseActivity {

    @BindView(R.id.radarView)
    RadarView radarView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_radar;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
}
