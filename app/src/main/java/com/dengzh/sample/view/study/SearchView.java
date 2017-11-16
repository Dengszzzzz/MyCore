package com.dengzh.sample.view.study;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dengzh.core.net.BaseRespEntity;
import com.dengzh.core.utils.LogUtil;

import retrofit2.http.POST;

/**
 * Created by dengzh on 2017/11/1.
 *
 * PathMeasure 用来测量Path的类
 * 可以沿着Path的绘画路径 取其中的一段，加上属性动画，可以做酷炫效果
 * setPath(Path path, boolean forceClosed)   关联一个Path
 * isClosed()                                是否闭合
 * getLength()                               获取Path的长度
 * nextContour()                             跳转到下一个轮廓（跳转到下一个Path）
 * getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)   截取片段
 * getPosTan(float distance, float[] pos, float[] tan)    获取指定长度的位置坐标及该点切线值
 * getMatrix(float distance, Matrix matrix, int flags)    获取指定长度的位置坐标及该点Matrix
 */

public class SearchView extends View{

    private String TAG = "SearchView";

    private Paint mPaint;
    private int mViewWidth;
    private int mViewHeight;

    // 这个视图拥有的状态
    public static enum State {
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    // 当前的状态(非常重要)
    private State mCurrentState = State.NONE;

    // 放大镜与外部圆环
    private Path path_srarch;
    private Path path_circle;

    // 测量Path 并截取部分的工具
    private PathMeasure mMeasure;

    // 默认的动效周期 2s
    private int defaultDuration = 2000;

    // 控制各个过程的动画
    private ValueAnimator mStartingAnimator;
    private ValueAnimator mSearchingAnimator;
    private ValueAnimator mEndingAnimator;

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue = 0;

    // 动效过程监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    // 用于控制动画状态转换
    private Handler mAnimatorHandler;

    // 判断是否已经搜索结束
    private boolean isOver = false;
    private int count = 0;


    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAll();
    }

    private void initAll(){
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    /**
     * 初始化路径
     */
    private void initPath(){
        path_srarch = new Path();
        path_circle = new Path();
        mMeasure = new PathMeasure();

        RectF oval1 = new RectF(-50,-50,50,50);  //放大镜圆环
        path_srarch.addArc(oval1,45,359.9f);
        RectF oval2 = new RectF(-100,-100,100,100);  //外部圆环
        path_circle.addArc(oval2,45,-359.9f);

        float[] pos = new float[2];           //存储了path_circle上的(x，y)两个值
        mMeasure.setPath(path_circle,false);  //外圆的测量Path
        mMeasure.getPosTan(0, pos,null);      //得到路径上某一长度的位置以及该位置的正切值

        path_srarch.lineTo(pos[0],pos[1]); //内圆的结束点与外圆的起点连起来，这条线作为把手

        LogUtil.e(TAG,"pos=" + pos[0] + ":" + pos[1]);  //打印位置信息
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        //值更新
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // getHandle发消息通知动画状态更新
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initHandler(){
        mAnimatorHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState){
                    case STARTING:  // 从开始动画转换好搜索动画
                        isOver = false;
                        mCurrentState = State.SEARCHING;
                        mStartingAnimator.removeAllListeners(); //关闭开始动画监听
                        mSearchingAnimator.start();  //开启搜索动画
                        break;
                    case SEARCHING:
                        if(!isOver){  //动画未结束
                            mSearchingAnimator.start();
                            LogUtil.e(TAG,"Update -- RESTART");
                            count++;
                            if(count>2){   //count>2进入结束状态
                                isOver = true;
                            }
                        }else{
                            mCurrentState = State.ENDING;
                            mSearchingAnimator.removeAllListeners();  //关闭搜索动画监听
                            mEndingAnimator.start();  //开启结束动画
                        }
                        break;
                    case ENDING: //从结束动画转变成无状态
                        mCurrentState = State.NONE;
                        break;
                }
            }
        };
    }

    /**
     * 初始化属性动画
     */
    private void initAnimator(){

        mStartingAnimator = ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);  //值从0->1，耗时defaultDuration
        mSearchingAnimator = ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1,0).setDuration(defaultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSearch(canvas);
    }

    /**
     * 画图
     * @param canvas
     */
    private void drawSearch(Canvas canvas) {

        mPaint.setColor(Color.WHITE);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        canvas.drawColor(Color.parseColor("#0082D7"));  //设置画布颜色

        switch (mCurrentState){
            case NONE:  //普通状态,只画放大镜
                canvas.drawPath(path_srarch,mPaint);
                break;
            case STARTING:  // 从开始动画转换好搜索动画
                mMeasure.setPath(path_srarch,false);  //关联放大镜path，且不闭合
                Path dst = new Path();
                //用于获取Path的一个片段,四个参数分别是
                //startD = 开始截取位置距离 Path 起点的长度
                //stopD = 结束截取位置距离 Path 起点的长度
                //dst = 截取的 Path 将会添加到 dst 中
                //startWithMoveTo = 起始点是否使用 moveTo,用于保证截取的 Path 第一个点位置不变
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue,mMeasure.getLength(),dst,true);
                canvas.drawPath(dst,mPaint);
                break;
            case SEARCHING:  //查询中
                mMeasure.setPath(path_circle,false);  //关联外圆
                Path dst2 = new Path();
                float stop = mMeasure.getLength() * mAnimatorValue; //截取的结束位置
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 200f));  //造就stop - start的值不是一直不变的
                mMeasure.getSegment(start,stop,dst2,true);
                canvas.drawPath(dst2,mPaint);
                break;
            case ENDING:  //结束状态
                mMeasure.setPath(path_srarch,false);
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength()*mAnimatorValue,mMeasure.getLength(),dst3,true);
                canvas.drawPath(dst3,mPaint);
                break;
        }
    }

    /**
     * 开启动画
     */
    public void startAnimator(){
        mCurrentState = State.STARTING;
        mStartingAnimator.start();
    }
}
