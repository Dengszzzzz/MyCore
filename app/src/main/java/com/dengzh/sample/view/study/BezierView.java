package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dengzh.sample.R;

import io.reactivex.internal.queue.MpscLinkedQueue;

/**
 * Created by dengzh on 2017/10/31.
 * 贝塞尔曲线
 * Path 学习 二阶贝塞尔曲线
 * 一阶曲线  lineTo
 * 二阶曲线  quadTo
 * 三阶曲线  cubicTo
 */

public class BezierView extends View {

    private Paint mPaint;
    private int centerX, centerY;
    private Context mContext;
    private PointF startPoint, endPoint, controlPoint;

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

        startPoint = new PointF(0,0);
        endPoint = new PointF(0,0);
        controlPoint = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        // 初始化数据点和控制点的位置
        startPoint.x = centerX - 200;
        startPoint.y = centerY;
        endPoint.x = centerX + 200;
        endPoint.y =  centerY;
        controlPoint.x = centerX;
        controlPoint.y = centerY-100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(startPoint.x,startPoint.y,mPaint);
        canvas.drawPoint(endPoint.x,endPoint.y,mPaint);
        canvas.drawPoint(controlPoint.x,controlPoint.y,mPaint);

        //2.绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(startPoint.x,startPoint.y,controlPoint.x,controlPoint.y,mPaint);
        canvas.drawLine(endPoint.x,endPoint.y,controlPoint.x,controlPoint.y,mPaint);

        //3.贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(startPoint.x,startPoint.y);
        path.quadTo(controlPoint.x,controlPoint.y,endPoint.x,endPoint.y);
        canvas.drawPath(path,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        controlPoint.x = event.getX();
        controlPoint.y = event.getY();
        invalidate();
        return true;
    }
}
