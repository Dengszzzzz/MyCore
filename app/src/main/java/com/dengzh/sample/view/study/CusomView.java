package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dengzh on 2017/10/13 0013.
 * 自定义view基类
 * 1.构造函数
 * 2.onMeasure()->测量View大小
 * 3.onSizeChanged() -> 确定view大小
 * 4.onLayout() -> 确定子view布局
 * 5.onDraw() ->  绘图
 */

public class CusomView extends View {

    private Paint mPaint;

    public CusomView(Context context) {
        this(context,null);
    }

    public CusomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr  这个参数一般不用
     */
    public CusomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);   //设置画笔颜色
        mPaint.setAntiAlias(true);      //设置抗锯齿
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);  //设置画笔宽度为10px
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  //取出宽度的确切数值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //取出宽度的测量模式

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);  //取出高度的确切数值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);  //取出高度的测量模式

        //如果对View的宽高进行修改了，不要调用super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        // 要调用setMeasuredDimension(widthsize,heightsize);。
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 测量完View并使用setMeasuredDimension函数之后View的大小基本上已经确定了，
     * 那么为什么还要再次确定View的大小呢？
     * 这是因为View的大小不仅由View本身控制，而且受父控件的影响，
     * 所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);  //关注w和h,这两个参数就是View最终的大小

    }

    /**
     * 用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
     * 在自定义ViewGroup中，onLayout一般是循环取出子View，
     * 然后经过计算得出各个子View位置的坐标值，然后用child.layout(l, t, r, b)设置子View位置。
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * @param l  View左侧距父View左侧的距离  getLeft();
     * @param t  View顶部距父View顶部的距离  getTop();
     * @param r  View右侧距父View左侧的距离  getRight();
     * @param b  View底部距父View顶部的距离  getBottom();
     */
    @Override
    public void layout(@Px int l, @Px int t, @Px int r, @Px int b) {
        super.layout(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(200,200,mPaint);  //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{
                500,500,
                500,600,
                500,700},mPaint);

        canvas.drawLine(300,300,500,600,mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100,200,200,200,
                100,300,200,300
        },mPaint);


    }
}
