package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dengzh on 2017/10/31.
 */

public class BezierLoveView extends View {

    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private Paint mPaint;
    private int mCenterX, mCenterY;

    private Context mContext;

    private PointF mCenter = new PointF(0,0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius*C;        // 圆形的控制点与数据点的差值

    private float[] mData = new float[8];               // 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];              // 顺时针记录绘制圆形的八个控制点

    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长

    private PointF[] mDatas = new PointF[4];
    private PointF[] mCtrls = new PointF[8];


    public BezierLoveView(Context context) {
        this(context,null);
    }

    public BezierLoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);


        mDatas[0] = new PointF(0,mCircleRadius);
        mDatas[1] = new PointF(mCircleRadius,0);
        mDatas[2] = new PointF(0,-mCircleRadius);
        mDatas[3] = new PointF(-mCircleRadius,0);

        mCtrls[0] = new PointF(mDatas[0].x+mDifference,mDatas[0].y);
        mCtrls[1] = new PointF(mDatas[1].x,mDatas[1].y+mDifference);
        mCtrls[2] = new PointF(mDatas[1].x,mDatas[1].y-mDifference);
        mCtrls[3] = new PointF(mDatas[2].x+mDifference,mDatas[2].y);
        mCtrls[4] = new PointF(mDatas[2].x-mDifference,mDatas[2].y);
        mCtrls[5] = new PointF(mDatas[3].x,mDatas[3].y-mDifference);
        mCtrls[6] = new PointF(mDatas[3].x,mDatas[3].y+mDifference);
        mCtrls[7] = new PointF(mDatas[0].x-mDifference,mDatas[0].y);

       /* // 初始化数据点
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        // 初始化控制点
        mCtrl[0]  = mData[0]+mDifference;
        mCtrl[1]  = mData[1];

        mCtrl[2]  = mData[2];
        mCtrl[3]  = mData[3]+mDifference;

        mCtrl[4]  = mData[2];
        mCtrl[5]  = mData[3]-mDifference;

        mCtrl[6]  = mData[4]+mDifference;
        mCtrl[7]  = mData[5];

        mCtrl[8]  = mData[4]-mDifference;
        mCtrl[9]  = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7]-mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7]+mDifference;

        mCtrl[14] = mData[0]-mDifference;
        mCtrl[15] = mData[1];*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystem(canvas);       // 绘制坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1,-1);                 // 翻转Y轴

        drawAuxiliaryLine(canvas);

        //绘制贝塞尔曲线
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        Path path = new Path();

        path.moveTo(mDatas[0].x,mDatas[0].y);
        path.cubicTo(mCtrls[0].x,mCtrls[0].y,mCtrls[1].x,mCtrls[1].y,mDatas[1].x,mDatas[1].y);
        path.cubicTo(mCtrls[2].x,mCtrls[2].y,mCtrls[3].x,mCtrls[3].y,mDatas[2].x,mDatas[2].y);
        path.cubicTo(mCtrls[4].x,mCtrls[4].y,mCtrls[5].x,mCtrls[5].y,mDatas[3].x,mDatas[3].y);
        path.cubicTo(mCtrls[6].x,mCtrls[6].y,mCtrls[7].x,mCtrls[7].y,mDatas[0].x,mDatas[0].y);
        canvas.drawPath(path, mPaint);
        mCurrent += mPiece;
        if(mCurrent < mDuration){  //计算公式
            mDatas[0].y -= 120/mCount;
            mCtrls[3].y += 80/mCount;
            mCtrls[4].y += 80/mCount;

            mCtrls[2].x -= 20/mCount;
            mCtrls[5].x += 20/mCount;

            postInvalidateDelayed((long) mPiece);
        }



        /*
        path.moveTo(mData[0],mData[1]);

        path.cubicTo(mCtrl[0],mCtrl[1],mCtrl[2],mCtrl[3],mData[2],mData[3]);
        path.cubicTo(mCtrl[4],  mCtrl[5],  mCtrl[6],  mCtrl[7],     mData[4], mData[5]);
        path.cubicTo(mCtrl[8],  mCtrl[9],  mCtrl[10], mCtrl[11],    mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15],    mData[0], mData[1]);

        canvas.drawPath(path, mPaint);

        mCurrent += mPiece;
        if(mCurrent < mDuration){  //计算公式
            mData[1] -= 120/mCount;
            mCtrl[7] += 80/mCount;
            mCtrl[9] += 80/mCount;

            mCtrl[4] -= 20/mCount;
            mCtrl[10] += 20/mCount;

            postInvalidateDelayed((long) mPiece);
        }*/

    }

    /**
     * 画坐标系
     * @param canvas
     */
    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();                      // 绘制做坐标系
        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1,-1);                 // 翻转Y轴

        Paint fuzhuPaint = new Paint();
        fuzhuPaint.setColor(Color.RED);
        fuzhuPaint.setStrokeWidth(5);
        fuzhuPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(0, -2000, 0, 2000, fuzhuPaint);
        canvas.drawLine(-2000, 0, 2000, 0, fuzhuPaint);

        canvas.restore();  //恢复之前save的canvas，此处即是坐标系没移，也没翻转
    }


    /**
     * 绘制辅助线
     * @param canvas
     */
    private void drawAuxiliaryLine(Canvas canvas) {

        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);

        for(int i=0;i<4;i++){
            canvas.drawPoint(mDatas[i].x,mDatas[i].y,mPaint);
        }
        for(int i=0;i<8;i++){
            canvas.drawPoint(mCtrls[i].x,mCtrls[i].y,mPaint);
        }
        // 绘制辅助线
        mPaint.setStrokeWidth(4);

        for(int i=1,j=1;i<4;i++,j+=2){
            canvas.drawLine(mDatas[i].x,mDatas[i].y,mCtrls[j].x,mCtrls[j].y,mPaint);
            canvas.drawLine(mDatas[i].x,mDatas[i].y,mCtrls[j+1].x,mCtrls[j+1].y,mPaint);
        }
        canvas.drawLine(mDatas[0].x,mDatas[0].y,mCtrls[0].x,mCtrls[0].y,mPaint);
        canvas.drawLine(mDatas[0].x,mDatas[0].y,mCtrls[7].x,mCtrls[7].y,mPaint);

        /*for (int i=0; i<8; i+=2){
            canvas.drawPoint(mData[i],mData[i+1], mPaint);
        }

        for (int i=0; i<16; i+=2){
            canvas.drawPoint(mCtrl[i], mCtrl[i+1], mPaint);
        }

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        for (int i=2, j=2; i<8; i+=2, j+=4){
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j],mCtrl[j+1],mPaint);
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j+2],mCtrl[j+3],mPaint);
        }
        canvas.drawLine(mData[0],mData[1],mCtrl[0],mCtrl[1],mPaint);
        canvas.drawLine(mData[0],mData[1],mCtrl[14],mCtrl[15],mPaint);*/
    }
}
