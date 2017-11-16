package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dengzh.sample.bean.PieDataBean;

import java.util.List;

/**
 * Created by dengzh on 2017/10/13 0013.
 * 饼状图
 * 基本数据：名字 数据值 百分比 对应的角度 颜色。
 *
 */

public class PieChartView extends View {

    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private float mStartAngle = 0;   // 饼状图初始绘制角度
    private List<PieDataBean> mData;
    private Paint mPaint = new Paint();  //画笔
    private int mWidth,mHeight;   //宽高高度

    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;   //view最终的宽高
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mData==null||mData.size()==0)
            return;

        float currentStartAngle = mStartAngle;    // 当前起始角度
        canvas.translate(mWidth/2,mHeight/2);      // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth,mHeight)/2*0.8);  //饼状图半径
        RectF rectF = new RectF(-r,-r,r,r);       //饼状图绘制区域

        //画圆弧
        for(PieDataBean bean:mData){
            mPaint.setColor(bean.getColor());
            canvas.drawArc(rectF,currentStartAngle,bean.getAngle(),true,mPaint);
            currentStartAngle += bean.getAngle();
        }
    }

    /**
     * 设置数据源
     * @param mDatas
     */
    public void setData(List<PieDataBean> mDatas){
        mData = mDatas;
        initData();
        invalidate();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        if(mData==null||mData.size()==0)
            return;

        float sumValue = 0;
        for(int i =0;i<mData.size();i++){
            PieDataBean bean = mData.get(i);
            sumValue += bean.getValue();  //计算数值和
            bean.setColor(mColors[i%mColors.length]); //分配颜色
        }

        for(PieDataBean bean:mData){
            float percentage = bean.getValue()/sumValue;  //所占百分比
            float angle = percentage * 360;     //所占角度
            bean.setPercentage(percentage);
            bean.setAngle(angle);
        }
    }
}
