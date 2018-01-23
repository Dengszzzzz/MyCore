package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.TantanBean;
import com.dengzh.sample.utils.glideUtils.GlideUtils;

import java.util.List;

/**
 * Created by dengzh on 2018/1/17.
 */

public class TantanAdapter extends BaseQuickAdapter<TantanBean,BaseViewHolder>{

    private String TAG = "TantanAdapter";

    public TantanAdapter(@Nullable List<TantanBean> data) {
        super(R.layout.item_swipe_card,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TantanBean item) {
        helper.setText(R.id.nameTv,item.getName())
                .setText(R.id.precentTv,item.getPostition() + "/" + mData.size());
        GlideUtils.loadImg(mContext, (ImageView) helper.getView(R.id.avatarIv),item.getUrl());
    }

}
