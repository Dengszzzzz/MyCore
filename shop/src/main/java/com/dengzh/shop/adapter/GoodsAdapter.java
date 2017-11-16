package com.dengzh.shop.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.shop.R;
import com.dengzh.shop.base.AppConfig;
import com.dengzh.shop.bean.GoodsBean;
import com.dengzh.shop.utils.APPUtils;

import java.util.List;

/**
 * Created by dengzh on 2017/9/29.
 * 商品适配器
 */

public class GoodsAdapter extends BaseQuickAdapter<GoodsBean,BaseViewHolder> {

    private LinearLayout.LayoutParams leftParams;
    private LinearLayout.LayoutParams rightParams;

    public GoodsAdapter(@Nullable List<GoodsBean> data) {
        super(R.layout.shop_item_goods,data);
        int space = (AppConfig.screenWidth - 2* APPUtils.dipTopx(AppConfig.mContext,168))/3;
        leftParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rightParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        leftParams.setMargins(space,space,space/2,0);
        rightParams.setMargins(space/2,space,space,0);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {

        helper.setBackgroundRes(R.id.imageIv,item.getResId());
        helper.setText(R.id.nameTv,item.getName())
                .setText(R.id.priceTv,item.getPrice()+"");
        if((helper.getAdapterPosition()- getHeaderLayoutCount())%2==0){
            helper.getView(R.id.itemViewLl).setLayoutParams(leftParams);
        }else{
            helper.getView(R.id.itemViewLl).setLayoutParams(rightParams);
        }
    }
}
