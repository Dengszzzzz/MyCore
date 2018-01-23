package com.dengzh.sample.view.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dengzh on 2018/1/17.
 * 重叠卡片布局  --  学习
 * 原作者
 * http://blog.csdn.net/zxt0601/article/details/53730908
 * https://github.com/mcxtzhang/ZLayoutManager
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager{

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * LayoutManager主要工作就是对子View布局
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        //view缓存的两种方式  1、detach的view放在scrap缓存中 2.remove的view放在recycle中
        detachAndScrapAttachedViews(recycler);
        //removeAndRecycleAllViews(recycler);
        int itemCount = getItemCount();
        if(itemCount<1){
            return;
        }

        //top-3View的position,从这个开始，后面的都不显示
        int bottomPosition;
        //边界处理
        if(itemCount < CardConfig.MAX_SHOW_COUNT){
            bottomPosition = 0;
        }else{
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        //从可见的最底层View开始layout，依次层叠上去
        for(int position = bottomPosition;position < itemCount;position++){
            //返回的View可能是之前回收的垃圾View，也可能是new出来的新View
            View view = recycler.getViewForPosition(position);
            //将View加入到RecyclerView中
            addView(view);
            //对子View进行测量,对每一个具体的子View进行测量。但是需要考虑到margin等信息
            measureChildWithMargins(view,0,0);
            //getWidth()是recyclerView的宽，getDecoratedMeasuredWidth(view)是包含ItemDecorate的尺寸的子View的宽
            //widthSpace就是剩余的空间
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);  //getHeight()是指recyclerView的高
            //我们在布局时，将childView居中处理，这里也可以改为只水平居中
            layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2,
                    widthSpace/2+getDecoratedMeasuredWidth(view),
                    heightSpace/2+getDecoratedMeasuredHeight(view));

            /**
             * TopView的Scale 为1，translationY 0
             * 每一级Scale相差0.05f，translationY相差7dp左右
             *
             * 观察人人影视的UI，拖动时，topView被拖动，Scale不变，一直为1.
             * top-1View 的Scale慢慢变化至1，translation也慢慢恢复0
             * top-2View的Scale慢慢变化至 top-1View的Scale，translation 也慢慢变化只top-1View的translation
             * top-3View的Scale要变化，translation岿然不动
             */

            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = itemCount - position - 1;
            //除了顶层不需要缩小和位移
            if (level > 0 /*&& level < mShowCount - 1*/) {
                //每一层都需要X方向的缩小
                view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                //前N层，依次向下位移和Y方向的缩小
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    float transY = CardConfig.TRANS_Y_GAP * level;
                    float scaleY = 1 - CardConfig.SCALE_GAP *level;
                    view.setTranslationY(transY);
                    view.setScaleY(scaleY);
                } else {//第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                    float transY = CardConfig.TRANS_Y_GAP * (level - 1);
                    float scaleY = 1 - CardConfig.SCALE_GAP * (level - 1);
                    view.setTranslationY(transY);
                    view.setScaleY(scaleY);
                }
            }
        }
    }
}
