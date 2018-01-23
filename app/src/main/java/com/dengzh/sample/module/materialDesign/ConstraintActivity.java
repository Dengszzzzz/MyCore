package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/21.
 * 约束布局简单使用
 */

public class ConstraintActivity extends BaseActivity {

    @BindView(R.id.toggleButton2)
    ToggleButton toggleButton2;
    @BindView(R.id.checkedTextView2)
    CheckedTextView checkedTextView2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;
    @BindView(R.id.quickContactBadge)
    QuickContactBadge quickContactBadge;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.switch1)
    Switch switch1;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_constraint_layout;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                switch(compoundButton.getId()){
                    case R.id.switch1:
                        ToastUtil.showToast(isChecked? "开":"关");
                        break;
                }
            }
        });
    }

}
