package com.dengzh.sample.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.dengzh.core.view.BaseDialog;
import com.dengzh.sample.R;
import com.dengzh.sample.utils.ToastUtil;

/**
 * Created by dengzh on 2017/11/3.
 * 分享弹窗
 */

public class ShareDialog extends BaseDialog implements View.OnClickListener{

    public ShareDialog(Context mContext) {
        super(mContext, R.layout.dialog_share, Gravity.BOTTOM, true,true);
        initData();
    }

    private void initData(){
        getView(R.id.wxFriendLl).setOnClickListener(this);
        getView(R.id.pyqLl).setOnClickListener(this);
        getView(R.id.qqLl).setOnClickListener(this);
        getView(R.id.qqZoneLl).setOnClickListener(this);
        getView(R.id.cancelTv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wxFriendLl:
                ToastUtil.showToast("微信好友");
                break;
            case R.id.pyqLl:
                ToastUtil.showToast("朋友圈");
                break;
            case R.id.qqLl:
                ToastUtil.showToast("QQ");
                break;
            case R.id.qqZoneLl:
                ToastUtil.showToast("QQ空间");
                break;
            case R.id.cancelTv:
                toggleDialog();
                break;
        }
    }
}
