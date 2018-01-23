package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.utils.glideUtils.GlideUtils;

import java.util.List;

/**
 * Created by dengzh on 2018/1/18.
 */

public class GalleryAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public GalleryAdapter(@Nullable List<String> data) {
        super(R.layout.item_gallery,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv);
        GlideUtils.loadImg(mContext, (ImageView) helper.getView(R.id.iv),item);
    }
}
