package com.dengzh.sample.module.customView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengzh.core.utils.LogUtils;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.TimeUtils;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.study.DepositHappyView;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/1/6.
 * 倒计时view
 */

public class CountDownActivity extends BaseActivity {

    @BindView(R.id.publishTimeTv)
    TextView publishTimeTv;
    @BindView(R.id.depositHappyView)
    DepositHappyView depositHappyView;
    @BindView(R.id.circleIv)
    ImageView circleIv;

    private MyCountTimer myCountTimer; //倒计时
    private int totalTime = 30 * 1000 * 5; //剩余总时间，30分钟
    private int lightCount;  //目前点亮灯的数量

    @Override
    protected int getLayoutId() {
        return R.layout.ac_count_down;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        lightCount = 30 - totalTime/(1000 * 60);  //目前该亮灯数量
        depositHappyView.setLightCount(lightCount);

        myCountTimer = new MyCountTimer(totalTime, 1000);  //30s 倒计时
        myCountTimer.start();  //倒计时开始
        //depositHappyView.startRotateAnim();

        //旋转动画
        RotateAnimation rotate = new RotateAnimation(0f,359f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setRepeatCount(-1);  //设置重复次数
        rotate.setDuration(5000);   //5s一圈
        rotate.setInterpolator(new LinearInterpolator());
        circleIv.setAnimation(rotate);
        rotate.start();
    }

    /**
     * 倒计时
     * 每隔1秒调用一次onTick(long millisUntilFinished)方法, 倒计时结束时调用onFinish()方法.
     */
    class MyCountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    要倒计时的总时间, 单位ms
         * @param countDownInterval 要倒计时的间隔时间, 单位ms
         */
        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //将时间long 转出 分秒格式
            String time = millisUntilFinished / 1000 + "";
            LogUtils.e(TAG, time);
            publishTimeTv.setText(TimeUtils.changeFormSecond((int) (millisUntilFinished / 1000)));

            //秒数%60 是算是不是正好整分钟，每个整分钟点亮一颗灯
            if((millisUntilFinished/1000)%60 == 0){
                lightCount = 30 - (int) (millisUntilFinished/(1000 * 60));
                depositHappyView.setLightCount(lightCount);
            }

        }

        @Override
        public void onFinish() {
            ToastUtil.showToast("倒计时结束");
            publishTimeTv.setText("00:00");
            depositHappyView.setLightCount(0);
        }
    }

    @Override
    protected void onDestroy() {
        //结束前关闭倒计时
        if (myCountTimer != null) {
            myCountTimer.cancel();
        }
        super.onDestroy();
    }
}
