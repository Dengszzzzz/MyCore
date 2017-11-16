package com.dengzh.sample.module.customView;

import android.os.Bundle;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.study.Bezier3View;
import com.dengzh.sample.view.study.BezierLoveView;
import com.dengzh.sample.view.study.BezierMagicCircleView;
import com.dengzh.sample.view.study.BezierView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/10/31.
 */

public class BezierActivity extends BaseActivity {

    @BindView(R.id.bezierView)
    BezierView bezierView;
    @BindView(R.id.bezier3View)
    Bezier3View bezier3View;
    @BindView(R.id.bezierLoveView)
    BezierLoveView bezierLoveView;
    @BindView(R.id.bezierMagicCicleView)
    BezierMagicCircleView bezierMagicCicleView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_bezier;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.controlBt1, R.id.controlBt2,R.id.startAnimBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.controlBt1:
                bezier3View.setMode(true);
                break;
            case R.id.controlBt2:
                bezier3View.setMode(false);
                break;
            case R.id.startAnimBt:
                bezierMagicCicleView.startAnimation();
                break;
        }
    }


}
