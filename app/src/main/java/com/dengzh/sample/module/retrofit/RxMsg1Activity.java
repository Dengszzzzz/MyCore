package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.core.rx.RxEvents;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by dengzh on 2017/9/21 0021.
 * rxBus 使用例子
 */

public class RxMsg1Activity extends BaseActivity {

    @BindView(R.id.contentTv)
    TextView contentTv;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_rxmsg1;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        initRxBus();
    }

    @OnClick(R.id.goNextBt)
    public void onViewClicked() {
        startActivity(RxMsg2Activity.class);
    }

    private void initRxBus(){
        registerRxBus(new Consumer<RxEvents<String>>() {
            @Override
            public void accept(RxEvents<String> events) throws Exception {
                if(events.code == RxEvents.TestRxCode){
                    contentTv.setText(events.content);
                }
            }
        });
    }
}
