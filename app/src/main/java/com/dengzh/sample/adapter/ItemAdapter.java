package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.ClazzBean;

import java.util.List;

/**
 * Created by dengzh on 2017/11/17.
 */

public class ItemAdapter extends BaseQuickAdapter<ClazzBean,BaseViewHolder>{

    public ItemAdapter(@Nullable List<ClazzBean> data) {
        super(R.layout.item_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClazzBean item) {
        helper.setText(R.id.itemTv,item.getClazzName());
    }
}
