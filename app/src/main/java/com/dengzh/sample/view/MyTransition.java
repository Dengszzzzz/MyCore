package com.dengzh.sample.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Interpolator;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qibin on 16-11-20.
 */

public class MyTransition extends Transition {

    private static final String TOP = "top";
    private static final String HEIGHT = "height";

    private long mPositionDuration;
    private long mSizeDuration;

    private TimeInterpolator mPositionInterpolator;
    private TimeInterpolator mSizeInterpolator;

    /**
     * 动画开启 view位置数据
     * @param transitionValues
     */
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);

        transitionValues.values.put(TOP, rect.top);
        transitionValues.values.put(HEIGHT, view.getHeight());

        Log.d("qibin", "start:" + rect.top + ";" + view.getHeight());
    }

    /**
     * 动画结束 view位置数据
     * @param transitionValues
     */
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(TOP, 0);
        transitionValues.values.put(HEIGHT, transitionValues.view.getHeight());

        Log.d("qibin", "end:" + 0 + ";" + transitionValues.view.getHeight());
    }

    /**
     * 获取数据后开启动画
     * @param sceneRoot
     * @param startValues
     * @param endValues
     * @return
     */
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (startValues == null || endValues == null) { return null;}

        final View endView = endValues.view;

        final int startTop = (int) startValues.values.get(TOP);
        final int startHeight = (int) startValues.values.get(HEIGHT);
        final int endTop = (int) endValues.values.get(TOP);
        final int endHeight = (int) endValues.values.get(HEIGHT);

        //因为我们的动画顺序是先移动, 后展开, 首先把view的高度设置为前一个界面上view的高度是
        //为了防止在移动的过程中view的高度是他自身的高度的.
        ViewCompat.setTranslationY(endView, startTop);
        endView.getLayoutParams().height = startHeight;
        endView.requestLayout();

        //位移动画
        ValueAnimator positionAnimator = ValueAnimator.ofInt(startTop, endTop);
        if (mPositionDuration > 0) { positionAnimator.setDuration(mPositionDuration);}
        positionAnimator.setInterpolator(mPositionInterpolator);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationY(endView, current);
            }
        });

        //展开动画
        ValueAnimator sizeAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        if (mSizeDuration > 0) { sizeAnimator.setDuration(mSizeDuration);}
        sizeAnimator.setInterpolator(mSizeInterpolator);

        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                endView.getLayoutParams().height = current;
                endView.requestLayout();
            }
        });

        //展开动画在位移动画之后
        AnimatorSet set = new AnimatorSet();
        set.play(sizeAnimator).after(positionAnimator);

        return set;
    }

    public void setPositionDuration(long duration) {
        mPositionDuration = duration;
    }

    public void setSizeDuration(long duration) {
        mSizeDuration = duration;
    }

    public void setPositionInterpolator(TimeInterpolator interpolator) {
        mPositionInterpolator = interpolator;
    }

    public void setSizeInterpolator(TimeInterpolator interpolator) {
        mSizeInterpolator = interpolator;
    }
}
