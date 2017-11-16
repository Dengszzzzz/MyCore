package com.dengzh.shop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.shop.R;
import com.dengzh.shop.bean.GoodsClassifyBean;

import java.util.List;

/**
 * Created by dengzh on 2017/9/29.
 * 首页分类适配器
 */

public class HomeClassifyAdapter extends BaseQuickAdapter<GoodsClassifyBean,BaseViewHolder> {

    public HomeClassifyAdapter(@Nullable List<GoodsClassifyBean> data) {
        super(R.layout.shop_item_goods_classify,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsClassifyBean item) {
        helper.setBackgroundRes(R.id.iconIv,item.getResId());
    }
}
