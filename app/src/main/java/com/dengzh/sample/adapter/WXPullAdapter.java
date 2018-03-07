package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.WXPullBean;

import java.util.List;

/**
 * Created by dengzh on 2018/2/26.
 */

public class WXPullAdapter extends BaseQuickAdapter<WXPullBean,BaseViewHolder>{


    public WXPullAdapter(@Nullable List<WXPullBean> data) {
        super(R.layout.item_mark,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WXPullBean item) {
        helper.setText(R.id.titleTv,item.getName());
    }
}
