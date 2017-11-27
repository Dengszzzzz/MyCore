package com.dengzh.sample.module.customView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/3.
 */

public class CustomViewActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_view;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("自定义view");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.clickBt1, R.id.clickBt2, R.id.clickBt3, R.id.clickBt4, R.id.clickBt5, R.id.clickBt6,R.id.clickBt7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                startActivity(new Intent(this, PieChartActivity.class));
                break;
            case R.id.clickBt2:
                startActivity(new Intent(this, LeafLoadingActivity.class));
                break;
            case R.id.clickBt3:
                startActivity(new Intent(this, EditTextActivity.class));
                break;
            case R.id.clickBt4:
                startActivity(new Intent(this, RadarActivity.class));
                break;
            case R.id.clickBt5:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.clickBt6:
                startActivity(new Intent(this, MeasurePathActivity.class));
                break;
            case R.id.clickBt7:
                startActivity(LevelViewActivity.class);
                break;
        }
    }
}
