package com.dengzh.sample.module.sqlite;

import android.os.Bundle;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/4.
 */

public class SqliteTestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.ac_sqlite_test;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("数据库");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.clickBt1, R.id.clickBt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                startActivity(RelamActivity.class);
                break;
            case R.id.clickBt2:
                startActivity(GreenDaoActivity.class);
                break;
        }
    }
}
