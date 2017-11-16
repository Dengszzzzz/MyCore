package com.dengzh.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.customView.BezierActivity;
import com.dengzh.sample.module.customView.CustomViewActivity;
import com.dengzh.sample.module.customView.EditTextActivity;
import com.dengzh.sample.module.customView.LeafLoadingActivity;
import com.dengzh.sample.module.customView.MeasurePathActivity;
import com.dengzh.sample.module.customView.PieChartActivity;
import com.dengzh.sample.module.customView.RadarActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.module.retrofit.RxAndMvpActivity;
import com.dengzh.sample.module.sqlite.SqliteTestActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.shop.module.home.HomeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.clickBt1, R.id.clickBt2,R.id.clickBt3,R.id.clickBt4,R.id.clickBt5,R.id.clickBt6,R.id.clickBt7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                startActivity(new Intent(this, GitHubActivity.class));
                break;
            case R.id.clickBt2:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.clickBt3:
                startActivity(new Intent(this, UmengActivity.class));
                break;
            case R.id.clickBt4:
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.clickBt5:
                startActivity(new Intent(this, DialogAndPopActivity.class));
                break;
            case R.id.clickBt6:
                startActivity(new Intent(this, RxAndMvpActivity.class));
                break;
            case R.id.clickBt7:
                startActivity(SqliteTestActivity.class);
                break;
        }
    }
}
