package com.dengzh.sample.module.retrofit;

import android.os.Bundle;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.core.rx.RxBus;
import com.dengzh.core.rx.RxEvents;

import butterknife.OnClick;

/**
 * Created by dengzh on 2017/9/21 0021.
 * rxBus 使用例子
 */

public class RxMsg2Activity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.ac_rxmsg2;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.sendRxData)
    public void onViewClicked() {
        //发送事件
        RxBus.getIntanceBus().post(RxEvents.TestRxCode,"这是一个String对象");
    }
}
