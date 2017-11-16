package com.dengzh.sample.mvp.presenter;

import com.dengzh.core.mvp.BasePresenter;
import com.dengzh.sample.mvp.model.modelImp.RegisterModel;
import com.dengzh.sample.mvp.model.modelInter.IRegisterModel;
import com.dengzh.sample.mvp.view.IRegisterView;

/**
 * Created by Administrator on 2017/9/7 0007.
 * MVP测试  IRegisterView 和 IRegisterModel 声明接口
 */

public class RegisterPresenter extends BasePresenter<IRegisterView,IRegisterModel> {

    @Override
    protected void requestNetData() {
        super.requestNetData();
    }

    /**
     * 注册处理
     * @param phone
     */
    public void handleRegister(String phone){
        mModel.submitRegister(phone, new IRegisterModel.CallBack() {
            @Override
            public void callBack(int code) {
                mView.showRegisterStatus(code);
            }
        });
    }

}
