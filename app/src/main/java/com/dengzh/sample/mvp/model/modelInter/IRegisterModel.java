package com.dengzh.sample.mvp.model.modelInter;

import com.dengzh.core.mvp.IBaseModel;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public interface IRegisterModel extends IBaseModel{


    /**
     * 提交手机注册
     * @param phone  手机号码
     * @param callBack  回调接口
     */
    void submitRegister(String phone,CallBack callBack);

    interface CallBack{
        void callBack(int code);
    }
}
