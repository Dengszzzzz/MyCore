package com.dengzh.sample.module.umeng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.PhoneUtils;
import com.dengzh.sample.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/2.
 * 友盟各项功能测试
 */

public class UmengActivity extends BaseActivity {

    private TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_umeng;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("友盟");
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.clickBt1, R.id.clickBt2, R.id.clickBt3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                tv.setText("制造一个空指针");
                break;
            case R.id.clickBt2:
                initUmengShare();
                break;
            case R.id.clickBt3:
                break;
        }
    }

    private void initUmengShare(){
        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param throwable 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            ToastUtil.showToast("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast("取消分享");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
