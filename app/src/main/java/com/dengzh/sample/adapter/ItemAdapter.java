package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;

import java.util.List;

/**
 * Created by dengzh on 2017/11/17.
 */

public class ItemAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public ItemAdapter(@Nullable List<String> data) {
        super(R.layout.item_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.itemTv,item);
    }

}
