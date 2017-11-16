package com.dengzh.sample.view.dialog;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengzh.core.view.BaseDialog;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.GoodsSpecBean;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.flowlayout.FlowLayout;
import com.dengzh.sample.view.flowlayout.TagAdapter;
import com.dengzh.sample.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzh on 2017/10/20.
 * 商品规格选择弹窗
 */

public class GoodsSpecSelectDialog extends BaseDialog implements View.OnClickListener{

    private ImageView goodsIv;
    private TextView goodsPriceTv;
    private TextView surplusTv;
    private TagFlowLayout tabFlowView;
    private TextView lessTv;
    private EditText countEt;
    private TextView addTv;
    private TextView confirmTv;

    //规格数据
    private String goodsId;
    private String goodsPicUrl;
    private String goodsPrice;
    private String goodsDes;
    private List<GoodsSpecBean> specList;

    private int curSelectedPos = -1; //被选择的item position
    private GoodsSpecAdapter mAdapter;
    private int curCount = 1;   //当前数量
    private int maxCount = 10;  //最大数量


    public GoodsSpecSelectDialog(Activity context) {
        //super(context, R.layout.dialog_goods_spec_select,true);
        //向上动画去掉，否则editText获得焦点后，键盘弹起不再挤压布局
        super(context, R.layout.dialog_goods_spec_select, Gravity.BOTTOM,true,false);
        initView();
    }

    private void initView(){
        specList = new ArrayList<>();
        goodsIv = getView(R.id.goodsIv);
        goodsPriceTv = getView(R.id.goodsPriceTv);
        surplusTv = getView(R.id.surplusTv);
        tabFlowView = getView(R.id.tabFlowView);
        lessTv = getView(R.id.lessTv);
        countEt = getView(R.id.countEt);
        addTv = getView(R.id.addTv);
        confirmTv = getView(R.id.confirmTv);

        addTv.setOnClickListener(this);
        lessTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);
        countEt.setOnClickListener(this);

        countEt.setText(String.valueOf(curCount));
        countEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    curCount = Integer.parseInt(countEt.getText().toString().trim());
                    if (curCount > maxCount) {
                        curCount = maxCount;
                        if(curCount == 0){
                            curCount = 1;
                        }
                        countEt.setText(String.valueOf(curCount));
                        countEt.setSelection(countEt.getText().toString().length());
                    }
                    if(curCount == 0){
                        curCount = 1;
                        countEt.setText(String.valueOf(curCount));
                        countEt.setSelection(countEt.getText().toString().length());
                    }
                }else{
                    //设置为1，光标保留在前面
                    curCount = 1;
                    countEt.setText(String.valueOf(curCount));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addTv:
                if(curCount<maxCount){
                    curCount++;
                    countEt.setText(curCount+"");
                }
                break;
            case R.id.lessTv:
                if(curCount>1){
                    curCount--;
                    countEt.setText(curCount+"");
                }
                break;
            case R.id.confirmTv:
                ToastUtil.showToast("加入购物车");
                break;
            case R.id.countEt:
                break;
        }
    }

    /**
     * 设置相关数据
     */
    public void setDialogData(String goodsId,String goodsPicUrl,String goodsPrice,String goodsDes,List<GoodsSpecBean> specList){
        this.goodsId = goodsId;
        this.goodsPicUrl = goodsPicUrl;
        this.goodsPrice = goodsPrice;
        this.goodsDes = goodsDes;
        this.specList.addAll(specList);

        mAdapter = new GoodsSpecAdapter(specList);
        tabFlowView.setAdapter(mAdapter);

    }

    /**
     * 标签适配器
     */
    public class GoodsSpecAdapter extends TagAdapter<GoodsSpecBean> {

        private List<CheckBox> views;

        public GoodsSpecAdapter(List<GoodsSpecBean> datas) {
            super(datas);

            views = new ArrayList<>();
            if (null != datas && datas.size() > 0) {
                curSelectedPos = 0;
            }
        }

        @Override
        public View getView(FlowLayout parent, final int position, GoodsSpecBean goodsSpecBean) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_spec,null,false);
            final CheckBox textView = view.findViewById(R.id.sizeTv);
            views.add(textView);

            if (position == curSelectedPos) {
                textView.setChecked(true);
                textView.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            }else{
                textView.setChecked(false);
                textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_black));
            }
            textView.setText(goodsSpecBean.getGoodsSize());

            textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (position != curSelectedPos) {
                        //选择其他，清空当前选中状态，设置选中文字橙色
                        clearAllCheckedState();
                        textView.setChecked(isChecked);
                        textView.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
                        curSelectedPos = position;
                    }else{
                        textView.setChecked(isChecked);
                        textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_black));
                    }
                }
            });
            return view;
        }

        /**
         * 清空所有选中状态
         */
        private void clearAllCheckedState() {
            for (CheckBox cb : views) {
                cb.setChecked(false);
                cb.setTextColor(ContextCompat.getColor(mContext,R.color.text_black));
            }
        }

        //判断目前是否有勾选，没有勾选任何一个选项将 curSelectedPos设置为-1
        private void checkSelect() {
            boolean noCheck = true;
            for (CheckBox cb : views) {
                if (cb.isChecked()) {
                    noCheck = false;
                    break;
                }
            }
            if (noCheck) {
                curSelectedPos = -1;
            }
        }

    }
}
