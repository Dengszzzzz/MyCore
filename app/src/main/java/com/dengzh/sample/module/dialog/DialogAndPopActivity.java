package com.dengzh.sample.module.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.Button;

import com.dengzh.core.view.BasePopupWindow;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.GoodsSpecBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.DialogUtils;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.dialog.GoodsSpecSelectDialog;
import com.dengzh.sample.view.dialog.ShareDialog;
import com.dengzh.sample.view.dialog.TopPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/3.
 * 对话框和弹窗 测试
 */

public class DialogAndPopActivity extends BaseActivity {

    @BindView(R.id.clickBt5)
    Button clickBt5;

    private ShareDialog shareDialog;
    private GoodsSpecSelectDialog specSelectDialog;
    private TopPopupWindow popupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_dialog_and_pop;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.clickBt1, R.id.clickBt2, R.id.clickBt3,R.id.clickBt4,R.id.clickBt5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickBt1:
                DialogUtils.showTipsWithOneButton(this,"通用提示弹窗","通用提示内容",null);
                break;
            case R.id.clickBt2:
                DialogUtils.showTipsWithTwoButton(this, "两个按钮弹窗", new DialogUtils.IDialogSelect() {
                    @Override
                    public void onSelected(int i) {
                        switch (i){
                            case 0:
                                ToastUtil.showToast("点击确认");
                                break;
                            case 1:
                                ToastUtil.showToast("点击取消");
                                break;
                        }
                    }
                });
                break;
            case R.id.clickBt3: //分享弹窗
                showShareDialog();
                break;
            case R.id.clickBt4: //商品规格弹窗
                showSpecSelectDialog();
                break;
            case R.id.clickBt5:  //PopWindow弹窗
                showPopWindow();
                break;
        }
    }

    /**
     * 显示分享对话框
     */
    private void showShareDialog(){
        if(shareDialog == null){
            shareDialog = new ShareDialog(this);
        }
        shareDialog.toggleDialog();
    }


    /**
     * 规格弹窗
     */
    private void showSpecSelectDialog(){
        if(specSelectDialog == null){
            List<GoodsSpecBean> specList = new ArrayList<>();
            for(int i = 0;i< 5;i++){
                GoodsSpecBean bean = new GoodsSpecBean();
                bean.setGoodsSize("规格"+i);
                bean.setGoodsValueId(""+i);
                specList.add(bean);
            }
            specSelectDialog = new GoodsSpecSelectDialog(this);
            specSelectDialog.setDialogData("10001","null","100","剩余660件",specList);
        }
        specSelectDialog.toggleDialog();
    }

    private void showPopWindow(){
        if(popupWindow==null){
            popupWindow = new TopPopupWindow(this);
        }
        popupWindow.showAsDropDown(clickBt5,0,0);
        //popupWindow.showAtLocation(clickBt5,Gravity.RIGHT|Gravity.TOP,0,0);
    }
}
