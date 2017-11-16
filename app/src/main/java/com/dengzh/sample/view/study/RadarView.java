package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.dengzh.sample.R;

/**
 * Created by dengzh on 2017/10/31.
 * 雷达图
 * 学习 Path
 */

public class RadarView extends View {

    private float radius;  //网格半径
    private int centerX;   //中心X
    private int centerY;   //中心Y
    private Paint mainPaint;   //雷达区画笔
    private Paint valuePaint;  //数据区画笔
    private Paint textPaint;  //文本画笔

    /** 可操作数据 **/
    private int count = 6;  //数据个数,几边形
    private int ShapeCount = 5;  //多边形数量
    private String[] titles = {"a","b","c","d","e","f"};
    private double[] data = {100,60,60,60,90,20}; //各维度分值
    private double maxValue = 100;  //数据最大值
    private Context mContext;
    private float radian = (float) (Math.PI*2/count);  //Math.PI=π ，此处计算的是每个角度对应的弧度



    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    /**
     * Paint 初始化
     */
    private void initView(){
        //网格线画笔
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(ContextCompat.getColor(mContext, R.color.lightgray));
        mainPaint.setStrokeWidth(5);
        mainPaint.setStyle(Paint.Style.STROKE);

        //数据区画笔
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        valuePaint.setStrokeWidth(5);
        valuePaint.setStyle(Paint.Style.FILL);

        //文本区画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(mContext, R.color.darkorange));
        textPaint.setTextSize(50);

    }

    /**
     * 确定View大小 -> 确定坐标轴原点
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w,h)/2 * 0.8f;  //网格半径，0.8是整个控件的大小比例
        centerX = w/2;
        centerY = h/2;
    }


    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    /**
     * 1.画蜘蛛网格
     * @param canvas
     */
    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r = radius/ShapeCount;  //r是蜘蛛丝之间的间距，5是圈数
        for(int i = 0;i<ShapeCount;i++){  //画shapeCount 圈多边形
            float curR = r*(i+1);  //当前半径

            //开始画多边形
            path.reset();     //重置
            for(int j=0;j<count;j++){
                if(j==0){
                    path.moveTo(centerX+curR,centerY);  //起点
                }else{
                    //Math.cos(x)  x是指弧度而非角度
                    float x = (float) (centerX + curR*Math.cos(radian * j));
                    float y = (float) (centerY + curR * Math.sin(radian * j));
                    path.lineTo(x,y);
                }
            }
            path.close(); //闭合路径
            canvas.drawPath(path,mainPaint);
        }
    }

    /**
     * 2.绘制直线，连接各个多边形
     * @param canvas
     */
    private void drawLines(Canvas canvas){
        Path path = new Path();
        for(int i=0;i<count;i++){
            path.reset();
            path.moveTo(centerX,centerY);
            float x = (float) (centerX + radius * Math.cos(radian*i));
            float y = (float) (centerY +radius * Math.sin(radian*i));
            path.lineTo(x,y);
            canvas.drawPath(path,mainPaint);
        }
    }


    /**
     * 3.绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics(); //字体测量
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;  //下坡 - 上坡
        for(int i=0;i<count;i++){
            float x = (float) (centerX + (radius + fontHeight/2)*Math.cos(radian*i));
            float y = (float) (centerY + (radius + fontHeight/2)*Math.sin(radian*i));
            if(radian*i>=0&&radian*i<=Math.PI/2) {//第4象限

                canvas.drawText(titles[i],x,y,textPaint);

            }else if(radian*i>=3*Math.PI/2&&radian*i<=Math.PI*2) {//第3象限

                canvas.drawText(titles[i],x,y,textPaint);

            }else if(radian*i>Math.PI/2&&radian*i<=Math.PI) {//第2象限

                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);

            }else if(radian*i>=Math.PI&&radian*i<3*Math.PI/2) {//第1象限

                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }
        }
    }

    /**
     * 4.绘制覆盖区
     * @param canvas
     */
    private void drawRegion(Canvas canvas){
        Path path = new Path();
        valuePaint.setAlpha(255);  //设置完全不透明
        for(int i=0;i<count;i++){
            double percent = data[i]/maxValue>1 ?1:data[i]/maxValue;  //百分比
            float x = (float) (centerX + radius*Math.cos(radian*i)*percent);
            float y = (float) (centerY + radius*Math.sin(radian*i)*percent);
            if(i==0){
                path.moveTo(x,y);
            }else{
                path.lineTo(x,y);
            }
            //绘制小圆点
            canvas.drawCircle(x,y,10,valuePaint);
        }
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE); //只描边
        canvas.drawPath(path,valuePaint);
        //绘制填充区域
        valuePaint.setAlpha(127);  //设置半透明
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);

    }


}
