package com.dengzh.sample.view.recyclerView;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.core.utils.LogUtils;
import com.dengzh.sample.R;

import java.util.List;

/**
 * Created by dengzh on 2018/1/17.
 */

public class TanTanCallback extends RenRenCallback{

    private static final int MAX_ROTATION = 15;
    //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，
    //判断 此次滑动方向是否是竖直的 ，水平方向上的误差(阈值，默认我给了50dp)
    int mHorizontalDeviation;

    public TanTanCallback(RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(rv, adapter, datas);
        mHorizontalDeviation = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mRv.getContext().getResources().getDisplayMetrics());
    }


    /**
     * 返回值滑动消失的距离, 这里是相对于RecycleView的宽度，0.5f表示为RecycleView的宽度的一半，取值为0~1f之间
     * @param viewHolder
     * @return
     */
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        if (isTopViewCenterInHorizontal(viewHolder.itemView)) {
            return Float.MAX_VALUE;
        }
        return super.getSwipeThreshold(viewHolder);
    }

    /**
     * 返回值滑动消失的距离，滑动小于这个值不消失，大于消失
     * @param defaultValue
     * @return
     */
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        View topView = mRv.getChildAt(mRv.getChildCount() - 1);
        if (isTopViewCenterInHorizontal(topView)) {
            return Float.MAX_VALUE;
        }
        return super.getSwipeEscapeVelocity(defaultValue);
    }

    /**
     * 返回值作为滑动的流程程度，越小越难滑动，越大越好滑动
     * @param defaultValue
     * @return
     */
    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {

        View topView = mRv.getChildAt(mRv.getChildCount() - 1);
        if (isTopViewCenterInHorizontal(topView)) {
            return Float.MAX_VALUE;
        }
        return super.getSwipeVelocityThreshold(defaultValue);
    }

    /**
     * 返回TopView此时在水平方向上是否是居中的
     *
     * @return
     */
    public boolean isTopViewCenterInHorizontal(View topView) {
        LogUtils.e("TAG", "getSwipeThreshold() called with: viewHolder.itemView.getX() = [" + topView.getX() + "]");
        LogUtils.e("TAG", "getSwipeThreshold() called with:  viewHolder.itemView.getWidth() / 2  = [" + topView.getWidth() / 2 + "]");
        LogUtils.e("TAG", "getSwipeThreshold() called with:  mRv.getX() = [" + mRv.getX() + "]");
        LogUtils.e("TAG", "getSwipeThreshold() called with:  mRv.getWidth() / 2 = [" + mRv.getWidth() / 2 + "]");
        return Math.abs(mRv.getWidth() / 2 - topView.getX() - (topView.getWidth() / 2)) < mHorizontalDeviation;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        super.onSwiped(viewHolder, direction);
        LogUtils.e("swipecard", "厉害了");
        //探探只是第一层加了rotate & alpha的操作
        //对rotate进行复位
        viewHolder.itemView.setRotation(0);

        //自己感受一下吧 Alpha
        if (viewHolder instanceof BaseViewHolder) {
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            holder.setAlpha(R.id.loveIv, 0);
            holder.setAlpha(R.id.delIv, 0);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        LogUtils.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //探探的效果
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold();
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                }
            } else {
                //探探只是第一层加了rotate & alpha的操作
                //不过他区分左右
                float xFraction = dX / getThreshold();
                //边界修正 最大为1
                if (xFraction > 1) {
                    xFraction = 1;
                } else if (xFraction < -1) {
                    xFraction = -1;
                }
                //rotate
                child.setRotation(xFraction * MAX_ROTATION);

                //自己感受一下吧 Alpha
                if (viewHolder instanceof BaseViewHolder) {
                    BaseViewHolder holder = (BaseViewHolder) viewHolder;
                    if (dX > 0) {
                        //露出左边，比心
                        holder.setAlpha(R.id.loveIv, xFraction);
                    } else if (dX<0){
                        //露出右边，滚犊子
                        holder.setAlpha(R.id.delIv, -xFraction);
                    }else {
                        holder.setAlpha(R.id.loveIv, 0);
                        holder.setAlpha(R.id.delIv, 0);
                    }
                }
            }
        }


        //可以在此判断左右滑：
        float v = mRv.getWidth() / 2 - viewHolder.itemView.getX() - (viewHolder.itemView.getWidth() / 2);
        if (v > 0) {
            isLeftSwipe = true;
        } else if (v < 0) {
            isLeftSwipe = false;
        }
    }

    //一个flag 判断左右滑
    private boolean isLeftSwipe;
}
