package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.premission.CallActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/4.
 */

public class RxAndMvpActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.ac_rx_and_mvp;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.clickBt1, R.id.clickBt2, R.id.clickBt3, R.id.clickBt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                startActivity(RegisterActivity.class);
                break;
            case R.id.clickBt2:
                startActivity(GitHubActivity.class);
                break;
            case R.id.clickBt3:
                startActivity(RxMsg1Activity.class);
                break;
            case R.id.clickBt4:
                startActivity(CallActivity.class);
                break;
        }
    }
}
