package com.dengzh.sample.module.utilsDemo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.SoftKeyBroadManager;
import com.dengzh.sample.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/2/25.
 * 软键盘
 */

public class SoftInputActivity extends BaseActivity implements SoftKeyBroadManager.SoftKeyboardStateListener{

    @BindView(R.id.contentEt)
    EditText contentEt;
    @BindView(R.id.contentLl)
    LinearLayout contentLl;

    private SoftKeyBroadManager mSoftKeyBroadManager;
    private View rootView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_soft_input;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        mSoftKeyBroadManager = new SoftKeyBroadManager(rootView);
        mSoftKeyBroadManager.addSoftKeyboardStateListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoftKeyBroadManager.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        //键盘弹起
        ToastUtil.showToast("键盘弹起"+ keyboardHeightInPx);
        rootView.scrollTo(0,keyboardHeightInPx);
    }

    @Override
    public void onSoftKeyboardClosed() {
        //键盘关闭
        ToastUtil.showToast("键盘关闭");
        rootView.scrollTo(0,0);
    }
}
