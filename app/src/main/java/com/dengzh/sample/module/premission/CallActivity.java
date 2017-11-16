package com.dengzh.sample.module.premission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by dengzh on 2017/9/13 0013.
 * rxPremission 和  原生判断权限 对比
 *  如需测试rxPermission，请设定 targetSdk>=23
 */

public class CallActivity extends BaseActivity {

    private final int PREMISSION_REQ_CODE_CALL_PHONE = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_call;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.callTv)
    public void onViewClicked() {
       // testPremission();
        testRxPremission();
    }

    private void testPremission() {
        //1.检查是否已获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //2.没有获取权限，那么是否用户拒绝过该权限？
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.CALL_PHONE)) {
                //3.用户曾经拒绝过该权限，则弹出提示
                ToastUtil.showToast("你已经拒绝过该权限，请到设置页面设置");
            } else {
                //3.用户没拒绝过，则申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, PREMISSION_REQ_CODE_CALL_PHONE);
            }
        } else {
            callPhone();
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "13728778986");
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PREMISSION_REQ_CODE_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    ToastUtil.showToast("用户拒绝权限申请");
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 测试RxPremission用法  如需测试，请设定 targetSdk>=23
     */
    private void testRxPremission(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission  permission) throws Exception {
                        if(permission.granted){
                            //点击『允许』
                            callPhone();
                        }else if (permission.shouldShowRequestPermissionRationale) {
                            //点击『拒绝』，不选中了『不再询问』
                            ToastUtil.showToast("用户此次拒绝了权限申请");
                        }else{
                            //点击『拒绝』，选中了『不再询问』
                            ToastUtil.showToast("用户永久拒绝了权限申请");
                        }
                    }
                });
    }
}
