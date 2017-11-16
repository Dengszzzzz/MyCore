package com.dengzh.sample.view.study;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.utils.APPUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by dengzh on 2017/10/15 0015.
 * 树叶进度条View
 */

public class LeafLoadingView extends View {

    private static final String TAG = "LeafLoadingView";
    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    // 叶子飘动一个周期所花的时间
    private static final long LEAF_FLOAT_TIME = 3000;
    // 叶子旋转一周需要的时间
    private static final long LEAF_ROTATE_TIME = 2000;
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;

    private Resources mResources;
    private Bitmap mLeafBitmap;   //树叶
    private int  mLeafWidth,mLeafHeight;  //树叶的宽高
    private Bitmap mOuterBitmap;    //框
    private int  mOuterWidth,mOuterHeight;  //框的宽高
    private Rect mOuterSrcRect, mOuterDestRect;
    private int mTotalWidth, mTotalHeight;  //总的宽高

    private Paint mBitmapPaint, mWhitePaint, mOrangePaint;
    private RectF mWhiteRectF, mOrangeRectF, mArcRectF;

    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 8;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;
    private int mLeftMargin, mRightMargin;

    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 当前进度
    private int mProgress;
    //进度条宽度
    private int mProgressWidth;
    // 当前所在的绘制的进度条的位置
    private int mCurrentProgressPosition;
    // 弧形的半径
    private int mArcRadius;
    // arc的右上角的x坐标，也是矩形x坐标的起始点
    private int mArcRightLocation;
    // 用于产生叶子信息
    private LeafFactory mLeafFactory;
    // 产生出的叶子信息
    private List<Leaf> mLeafInfos;
    // 叶子飘动一个周期所花的时间
    private long mLeafFloatTime = LEAF_FLOAT_TIME;
    // 叶子旋转一周需要的时间
    private long mLeafRotateTime = LEAF_ROTATE_TIME;
    // 中等振幅大小
    private int mMiddleAmplitude = MIDDLE_AMPLITUDE;
    // 振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;
    // 用于控制随机增加的时间不抱团
    private int mAddTime;

    public LeafLoadingView(Context context) {
        this(context,null);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        mLeftMargin = APPUtils.dipTopx(context, LEFT_MARGIN);
        mRightMargin = APPUtils.dipTopx(context, RIGHT_MARGIN);

        initBitmap();
        initPaint();

        mLeafFactory = new LeafFactory();
        mLeafInfos = mLeafFactory.generateLeafs();
    }

    private void initBitmap(){
        mLeafBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.leaf);
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mOuterBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.leaf_kuang);
        mOuterWidth = mOuterBitmap.getWidth();
        mOuterHeight = mOuterBitmap.getHeight();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);  //设置是否抖动
        mBitmapPaint.setFilterBitmap(true); //对位图进行滤波处理，如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawProgressAndLeafs(canvas);
        canvas.drawBitmap(mOuterBitmap,mOuterSrcRect,mOuterDestRect,mBitmapPaint);
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin; //进度条宽度
        mArcRadius = (mTotalHeight - 2*mLeftMargin ) / 2;  //圆半径
        mArcRightLocation = mLeftMargin + mArcRadius ;  //圆心

        //mTotalWidth，mTotalHeight是真正的宽高，可能在xml文件设定了值，
        // 则onDraw()中的canvas.drawBitmap(...)就是将src做一系列转化到des
        mOuterSrcRect = new Rect(0,0,mOuterWidth,mOuterHeight);
        mOuterDestRect = new Rect(0,0,mTotalWidth,mTotalHeight);

        mWhiteRectF = new RectF(mLeftMargin + mCurrentProgressPosition,mLeftMargin,
                mTotalWidth - mRightMargin,mTotalHeight - mLeftMargin);  //白色区域

        mOrangeRectF = new RectF(mArcRadius,0,mCurrentProgressPosition,mTotalHeight);  //矩形橙色区域

        mArcRectF = new RectF(mLeftMargin,mLeftMargin,mLeftMargin + mArcRadius*2, mTotalHeight - mLeftMargin);   //弧

    }

    private void drawProgressAndLeafs(Canvas canvas){
        if (mProgress >= TOTAL_PROGRESS) {
            mProgress = 0;
        }
        // mProgressWidth为进度条的宽度，根据当前进度算出进度条的位置
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        // 即当前位置在半圆内
        if (mCurrentProgressPosition < mArcRadius) {
            Log.i(TAG, "mProgress = " + mProgress + "---mCurrentProgressPosition = "
                    + mCurrentProgressPosition
                    + "--mArcProgressWidth" + mArcRadius);

            // 1.绘制白色弧,从90度开始绘制，扇过180度
            canvas.drawArc(mArcRectF, 90, 180, false, mWhitePaint);
            // 2.绘制白色矩形
            mWhiteRectF.left = mArcRightLocation;
            canvas.drawRect(mWhiteRectF, mWhitePaint);

            // 绘制叶子
            drawLeafs(canvas);

            // 3.绘制棕色弧
            // 单边角度 Math.acos()  －反余弦函数；
            //Math.toDegrees() － 弧度转化为角度，Math.toRadians 角度转化为弧度
            int angle = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentProgressPosition)
                    / (float) mArcRadius));
            // 起始的位置
            int startAngle = 180 - angle;
            // 扫过的角度
            int sweepAngle = 2 * angle;
            Log.i(TAG, "startAngle = " + startAngle);
            canvas.drawArc(mArcRectF, startAngle, sweepAngle, false, mOrangePaint);
        }else{
            Log.i(TAG, "mProgress = " + mProgress + "---transfer-----mCurrentProgressPosition = "
                    + mCurrentProgressPosition
                    + "--mArcProgressWidth" + mArcRadius);
            // 1.绘制白色矩形  (此处没必要画白色弧)
            // 2.绘制橙色弧
            // 3.绘制橙色矩形
            //1.绘制白色矩形
            mWhiteRectF.left = mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectF, mWhitePaint);

            //绘制叶子
            drawLeafs(canvas);
            // 2.绘制Orange ARC
            canvas.drawArc(mArcRectF, 90, 180, false, mOrangePaint);
            // 3.绘制orange RECT
            mOrangeRectF.left = mArcRightLocation;
            mOrangeRectF.right = mCurrentProgressPosition;
            canvas.drawRect(mOrangeRectF,mOrangePaint);
        }
    }

    /**
     * 绘制叶子
     * @param canvas
     */
    private void drawLeafs(Canvas canvas){
        mLeafRotateTime = mLeafRotateTime <= 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        long currentTime = System.currentTimeMillis();
        for(int i = 0;i<mLeafInfos.size();i++){
            Leaf leaf = mLeafInfos.get(i);
            if(currentTime > leaf.startTime && leaf.startTime!=0){
                // 绘制叶子－－根据叶子的类型和当前时间得出叶子的（x，y）
                getLeafLocation(leaf, currentTime);

                canvas.save();
                // 通过Matrix控制叶子旋转和移动
                Matrix matrix = new Matrix();
                float transX = mLeftMargin + leaf.x;
                float transY = mLeftMargin + leaf.y;
                Log.i(TAG, "left.x = " + leaf.x + "--leaf.y=" + leaf.y);
                matrix.postTranslate(transX, transY);
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                float rotateFraction = ((currentTime - leaf.startTime) % mLeafRotateTime)
                        / (float) mLeafRotateTime;
                int angle = (int) (rotateFraction * 360);
                // 根据叶子旋转方向确定叶子旋转角度
                int rotate = leaf.rotateDirection == 0 ? angle + leaf.rotateAngle : -angle
                        + leaf.rotateAngle;
                matrix.postRotate(rotate,transX + mLeafWidth/2,transY + mLeafHeight/2);
                canvas.drawBitmap(mLeafBitmap, matrix, mBitmapPaint);
                canvas.restore();
            }
        }
    }

    /**
     * 根据叶子的类型和当前时间得出叶子的（x，y）
     * @param leaf
     * @param currentTime
     */
    private void getLeafLocation(Leaf leaf, long currentTime) {
        long intervalTime = currentTime - leaf.startTime;
        mLeafFloatTime = mLeafFloatTime <=0 ?LEAF_FLOAT_TIME:mLeafRotateTime;
        if(intervalTime < 0){
            return;
        }else if(intervalTime > mLeafFloatTime){  //超过叶子飘动的时间
            leaf.startTime = System.currentTimeMillis() + new Random().nextInt((int) mLeafFloatTime);
        }

        float fraction = (float) intervalTime / mLeafFloatTime; //不懂
        leaf.x = (int) (mProgressWidth - mProgressWidth * fraction);
        leaf.y = getLocationY(leaf);
    }

    // 通过叶子信息获取当前叶子的Y值
    private int getLocationY(Leaf leaf) {
        // 曲线函数 y = A(wx+Q)+h 其中w影响周期，A影响振幅 ，周期T＝ 2 * Math.PI/w;
        float w = (float) ( 2 * Math.PI / mProgressWidth);
        float a = mMiddleAmplitude;
        switch (leaf.type){
            case LITTLE:
                // 小振幅 ＝ 中等振幅 － 振幅差
                a = mMiddleAmplitude - mAmplitudeDisparity;
                break;
            case MIDDLE:
                a = mMiddleAmplitude;
                break;
            case BIG:
                // 小振幅 ＝ 中等振幅 + 振幅差
                a = mMiddleAmplitude + mAmplitudeDisparity;
                break;
        }
        Log.i(TAG, "---a = " + a + "---w = " + w + "--leaf.x = " + leaf.x);
        return (int) (a * Math.sin(w * leaf.x)) + mArcRadius * 2 / 3;
    }

    /**
     * 叶子对象，用来记录叶子主要数据
     *
     * @author Ajian_Studio
     */
    private class Leaf {

        // 在绘制部分的位置
        float x, y;
        // 控制叶子飘动的幅度
        StartType type;
        // 旋转角度
        int rotateAngle;
        // 旋转方向--0代表顺时针，1代表逆时针
        int rotateDirection;
        // 起始时间(ms)
        long startTime;
    }

    private enum StartType {
        LITTLE, MIDDLE, BIG
    }

    private class LeafFactory {
        private static final int MAX_LEAFS = 8;
        Random random = new Random();

        // 生成一个叶子信息
        public Leaf generateLeaf() {
            Leaf leaf = new Leaf();
            int randomType = random.nextInt(3);
            // 随时类型－ 随机振幅
            switch (randomType) {
                case 0:
                    leaf.type = StartType.MIDDLE;
                    break;
                case 1:
                    leaf.type = StartType.LITTLE;
                    break;
                case 2:
                    leaf.type = StartType.BIG;
                    break;
            }
            // 随机起始的旋转角度
            leaf.rotateAngle = random.nextInt(360);
            // 随机旋转方向（顺时针或逆时针）
            leaf.rotateDirection = random.nextInt(2);
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            mLeafFloatTime = mLeafFloatTime <= 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
            mAddTime += random.nextInt((int) (mLeafFloatTime * 2));
            leaf.startTime = System.currentTimeMillis() + mAddTime;
            return leaf;
        }

        // 根据最大叶子数产生叶子信息
        public List<Leaf> generateLeafs() {
            return generateLeafs(MAX_LEAFS);
        }

        // 根据传入的叶子数量产生叶子信息
        public List<Leaf> generateLeafs(int leafSize) {
            List<Leaf> leafs = new LinkedList<Leaf>();
            for (int i = 0; i < leafSize; i++) {
                leafs.add(generateLeaf());
            }
            return leafs;
        }
    }


    /**************************** 向外暴露的方法 *************************************/

    /**
     * 设置中等振幅
     *
     * @param amplitude
     */
    public void setMiddleAmplitude(int amplitude) {
        this.mMiddleAmplitude = amplitude;
    }

    /**
     * 设置振幅差
     *
     * @param disparity
     */
    public void setMplitudeDisparity(int disparity) {
        this.mAmplitudeDisparity = disparity;
    }


    /**
     * 获取中等振幅
     *
     */
    public int getMiddleAmplitude() {
        return mMiddleAmplitude;
    }

    /**
     * 获取振幅差
     *
     */
    public int getMplitudeDisparity() {
        return mAmplitudeDisparity;
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    /**
     * 设置叶子飘完一个周期所花的时间
     *
     * @param time
     */
    public void setLeafFloatTime(long time) {
        this.mLeafFloatTime = time;
    }

    /**
     * 设置叶子旋转一周所花的时间
     *
     * @param time
     */
    public void setLeafRotateTime(long time) {
        this.mLeafRotateTime = time;
    }

    /**
     * 获取叶子飘完一个周期所花的时间
     */
    public long getLeafFloatTime() {
        mLeafFloatTime = mLeafFloatTime == 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
        return mLeafFloatTime;
    }

    /**
     * 获取叶子旋转一周所花的时间
     */
    public long getLeafRotateTime() {
        mLeafRotateTime = mLeafRotateTime == 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        return mLeafRotateTime;
    }

}
