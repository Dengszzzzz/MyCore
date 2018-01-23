package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/14.
 */

public class TabFragment extends BaseFragment {

    @BindView(R.id.contentTv)
    TextView contentTv;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.fr_tab;
    }

    @Override
    protected void initUI() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            content = bundle.getString("Content");
            contentTv.setText(content);
        }
    }

    @Override
    protected void initData() {

    }

}
