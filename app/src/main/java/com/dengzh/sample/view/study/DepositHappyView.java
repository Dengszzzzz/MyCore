package com.dengzh.sample.view.study;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;

/**
 * Created by dengzh on 2018/1/5.
 * 存存乐的动画图层
 */

public class DepositHappyView  extends View {

    private Context mContext;
    private Resources mResources;

    //背景圈Bitmap
    private Bitmap mCircleBgBitmap;
    private int  mCircleBgBitmapWidth,mCircleBgBitmapHeight;  //背景圈的宽高
    private int mCircleRadius;                    //背景圈Bitmap的半径
    private Rect mOuterSrcRect, mOuterDestRect;
    //圈bitmap
    private Bitmap mCircleBitmap;
    private int mCircleBitmapWidth,mCircleBitmapHeight;  //圈宽高
    private Rect mCircleSrcRect;

    //不亮灯Bitmap
    private Bitmap unLightBitmap;
    private int unLightBitmapRadius;  //不亮灯bitmap的半径
    //亮灯Bitmap
    private Bitmap mLightBitmap;
    private int mLightBitmapRadius;  //亮灯bitmap的半径


    //可控参数
    private int centerX;   //整个View的中心坐标X
    private int centerY;   //整个View的中心坐标Y
    private int mLightCircleRadius;    //灯圆圈半径
    private int distance = 30;      //灯与圈的距离
    private int mCount = 30;        //灯的总数量
    private int mLightCount = 5;    //亮灯的数量  随时变化
    private float radian = (float) (Math.PI*2/mCount);  //Math.PI=π ，此处计算的是每个角度对应的弧度
    private int[] mLightX = new int[mCount];   //亮灯的位置坐标，其实是计录灯圈的的各个点的位置，X轴坐标
    private int[] mLightY = new int[mCount];   //亮灯的位置坐标，其实是计录灯圈的的各个点的位置，Y轴坐标

    private Paint mBitmapPaint;
    private int mTotalWidth, mTotalHeight;  //总的宽高


    public DepositHappyView(Context context) {
        this(context,null);
    }

    public DepositHappyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mResources = getResources();
        initPaint();
        initBitmap();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);  //设置是否抖动
        mBitmapPaint.setFilterBitmap(true); //对位图进行滤波处理，如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
    }

    /**
     * 初始化bitmap
     */
    private void initBitmap(){
        mCircleBgBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.despoit_game_circle_bg);
        mCircleBgBitmapWidth = mCircleBgBitmap.getWidth();
        mCircleBgBitmapHeight = mCircleBgBitmap.getHeight();

        mCircleBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.desposit_game_circle);
        mCircleBitmapWidth = mCircleBitmap.getWidth();
        mCircleBitmapHeight = mCircleBitmap.getHeight();

        unLightBitmap = BitmapFactory.decodeResource(mResources,R.mipmap.desposit_game_unlight);
        unLightBitmapRadius = unLightBitmap.getWidth()/2;

        mLightBitmap = BitmapFactory.decodeResource(mResources,R.mipmap.desposit_game_light);
        mLightBitmapRadius = mLightBitmap.getWidth()/2;

       // mCircle = BitmapFactory.decodeResource(mResources,R.mipmap.desposit_game_circle);

       // mLight =

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        centerX = w/2;  //中心点X
        centerY = h/2;  //中心点Y
        mCircleRadius = (int) (Math.min(mTotalWidth,mTotalHeight)/2 * 0.7); //背景圈半径
        mLightCircleRadius = (int) (Math.min(mTotalWidth,mTotalHeight)/2 * 0.8);  //灯圆圈半径

        //计算灯圈的各个点的位置，在画灯的时候使用
        //Math.cos(x) 详解，x指的是弧度而非角度 此处不加 (Math.PI*2/360)*90) 则是从水平方向正右边点开始
        //目的从正上方开始画，需要cos90度，转换成弧度就以下公式
        for(int i=0;i<mCount;i++) {
            mLightX[i] = (int) (centerX + mLightCircleRadius * Math.cos(radian * i - (Math.PI*2/360)*90));
            mLightY[i] = (int) (centerY + mLightCircleRadius * Math.sin(radian * i - (Math.PI*2/360)*90));
        }

        //mTotalWidth，mTotalHeight是真正的宽高，可能在xml文件设定了值，
        // 则onDraw()中的canvas.drawBitmap(...)就是将src做一系列转化到des
        mOuterSrcRect = new Rect(0,0,mCircleBgBitmapWidth,mCircleBgBitmapHeight);
        mOuterDestRect = new Rect(centerX - mCircleRadius,centerY - mCircleRadius,centerX + mCircleRadius, centerY + mCircleRadius);
        mCircleSrcRect = new Rect(0,0,mCircleBitmapWidth,mCircleBitmapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*if(isInit){
            //1.画圈背景
            canvas.drawBitmap(mCircleBgBitmap,mOuterSrcRect,mOuterDestRect,mBitmapPaint);
            //2.画圈动画
            canvas.drawBitmap(mCircleBitmap,mCircleSrcRect,mOuterDestRect,mBitmapPaint);
            //3.画灯
            drawUnLight(canvas);
            isInit = false;
        }else{
            //已初始化过了，开启圈圈动画
            canvas.rotate(rotateDegree);
        }*/

        //1.画圈背景
        canvas.drawBitmap(mCircleBgBitmap,mOuterSrcRect,mOuterDestRect,mBitmapPaint);
        //2.画灯
        drawUnLight(canvas);
        //3.画圈动画 把坐标系旋转一定角度
        if(anim!=null && anim.isStarted()){
            canvas.rotate(rotateDegree,centerX,centerY);
        }
        canvas.drawBitmap(mCircleBitmap,mCircleSrcRect,mOuterDestRect,mBitmapPaint);

        /*if(anim!=null && anim.isStarted()){
            canvas.rotate(rotateDegree);
        }*/
       // postInvalidate();
    }


    private boolean isInit = true;
    private ValueAnimator anim;
    private float rotateDegree;
    /**
     * 画圈 赋予动画效果 效果不好，卡顿
     */
    public void startRotateAnim(){
        anim = ValueAnimator.ofFloat(0.0f,360.0f);
        anim.setRepeatCount(ValueAnimator.INFINITE);  //设置无限重复
        anim.setRepeatMode(ValueAnimator.RESTART);    //设置重复模式
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(60 * 1000);     //5s执行完一个流程
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateDegree = (float) valueAnimator.getAnimatedValue(); //角度
                LogUtil.e("onDraw","画角度"+rotateDegree);
                postInvalidate();
            }
        });

        anim.start();
    }

    /**
     * 画不亮灯
     * @param canvas
     */
    private void drawUnLight(Canvas canvas){
        for(int i=0;i<mCount;i++){
            int transX = mLightX[i];
            int transY = mLightY[i];
            Matrix matrix = new Matrix();
            if(i<mLightCount){ //画亮灯
                transX = transX - mLightBitmapRadius; //考虑亮灯bitmap中心点
                transY = transY - mLightBitmapRadius;
                matrix.postTranslate(transX, transY);
                canvas.drawBitmap(mLightBitmap, matrix, mBitmapPaint);
            }else{   //画不亮灯
                transX = transX - unLightBitmapRadius; //考虑不亮灯bitmap中心点
                transY = transY - unLightBitmapRadius;
                matrix.postTranslate(transX, transY);
                canvas.drawBitmap(unLightBitmap, matrix, mBitmapPaint);
            }

        }
    }



}
