package com.dengzh.sample.module.customView;

import android.os.Bundle;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.study.SearchView;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/11/1.
 * 路径测量学习
 */

public class MeasurePathActivity extends BaseActivity {

    @BindView(R.id.searchView)
    SearchView searchView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_measure_path;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        searchView.startAnimator();
    }

    @Override
    protected void initData() {

    }

}
