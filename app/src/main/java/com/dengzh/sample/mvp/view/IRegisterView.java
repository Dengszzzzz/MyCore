package com.dengzh.sample.mvp.view;

import com.dengzh.core.mvp.IBaseView;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public interface IRegisterView extends IBaseView{

    /**
     * 注册返回，更新UI
     * @param code
     */
    void showRegisterStatus(int code);
}
