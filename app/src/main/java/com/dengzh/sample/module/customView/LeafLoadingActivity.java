package com.dengzh.sample.module.customView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.study.LeafLoadingView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/10/15 0015.
 */

public class LeafLoadingActivity extends BaseActivity {


    private static final int REFRESH_PROGRESS = 0x10;

    @BindView(R.id.leaf_loading)
    LeafLoadingView mLeafLoadingView;
    @BindView(R.id.seekBar_ampair)
    SeekBar seekBarAmpair;
    @BindView(R.id.seekBar_distance)
    SeekBar seekBarDistance;
    @BindView(R.id.seekBar_float_time)
    SeekBar seekBarFloatTime;
    @BindView(R.id.seekBar_rotate_time)
    SeekBar seekBarRotateTime;
    @BindView(R.id.text_progress)
    TextView textProgress;
    @BindView(R.id.fan_pic)
    ImageView fanPic;
    private int mProgress = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_leaf_loading;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shan_rotate);
        fanPic.startAnimation(animation);
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
                        mProgress += 1;
                        // 随机800ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(800));
                        mLeafLoadingView.setProgress(mProgress);
                    } else if (mProgress < 100) {
                        mProgress += 1;
                        // 随机1200ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(1200));
                        mLeafLoadingView.setProgress(mProgress);
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @OnClick({R.id.clear_progress, R.id.add_progress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_progress:
                break;
            case R.id.add_progress:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
