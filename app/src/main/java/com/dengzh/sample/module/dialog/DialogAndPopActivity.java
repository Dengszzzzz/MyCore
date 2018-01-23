package com.dengzh.sample.module.dialog;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.adapter.ItemAdapter2;
import com.dengzh.sample.bean.GoodsSpecBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.DialogUtils;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.dialog.GoodsSpecSelectDialog;
import com.dengzh.sample.view.dialog.ShareDialog;
import com.dengzh.sample.view.dialog.TopPopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/11/3.
 * 对话框和弹窗 测试
 */

public class DialogAndPopActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ItemAdapter2 adapter;

    private ShareDialog shareDialog;
    private GoodsSpecSelectDialog specSelectDialog;
    private TopPopupWindow popupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("Dialog和PopWindow弹窗");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        String[] strs = {"通用提示弹窗","通用确定弹窗","分享弹窗","商品规格弹窗","PopWindow弹窗"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter2(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        DialogUtils.showTipsWithOneButton(DialogAndPopActivity.this,"通用提示弹窗","通用提示内容",null);
                        break;
                    case 1:
                        DialogUtils.showTipsWithTwoButton(DialogAndPopActivity.this, "两个按钮弹窗", new DialogUtils.IDialogSelect() {
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
                    case 2: //分享弹窗
                        showShareDialog();
                        break;
                    case 3:  //商品规格弹窗
                        showSpecSelectDialog();
                        break;
                    case 4: //PopWindow弹窗
                        showPopWindow();
                        break;
                }
            }
        });
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
        popupWindow.showAsDropDown(toolbar,0,0);
        //popupWindow.showAtLocation(clickBt5,Gravity.RIGHT|Gravity.TOP,0,0);
    }
}
