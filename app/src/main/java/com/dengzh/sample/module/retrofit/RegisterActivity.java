package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.mvp.model.modelImp.RegisterModel;
import com.dengzh.sample.mvp.presenter.RegisterPresenter;
import com.dengzh.sample.mvp.view.IRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/9/7 0007.
 * mvp 简单例子
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter,RegisterModel> implements IRegisterView {

    @BindView(R.id.phoneEt)
    EditText phoneEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_register;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }



    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showRegisterStatus(int code) {
        if(code == 100){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.registerTv)
    public void onViewClicked() {
        if(!TextUtils.isEmpty(phoneEt.getText().toString())){
            mPresenter.handleRegister(phoneEt.getText().toString());
        }else{
            Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }
    }
}
