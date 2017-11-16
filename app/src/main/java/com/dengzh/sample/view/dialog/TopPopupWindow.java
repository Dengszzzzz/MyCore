package com.dengzh.sample.view.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.dengzh.core.view.BasePopupWindow;
import com.dengzh.sample.R;
import com.dengzh.sample.utils.ToastUtil;

/**
 * Created by dengzh on 2017/11/3.
 * BasePopupWindow 测试类
 *
 */

public class TopPopupWindow extends BasePopupWindow{

    public TopPopupWindow(Context mContext) {
        //1.不设置宽高，则是wrap_content
        //super(mContext, R.layout.pop_test,true);
        //2.应该和R.layout.pop_test 布局根view 宽高保持一致
        super(mContext,R.layout.pop_test, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        init();
    }

    private void init(){
        setText(R.id.textTv,"添加了点击事件");
        getView(R.id.textTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("点击了popwindow");
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ToastUtil.showToast("执行了onDismiss");
            }
        });
    }



}
