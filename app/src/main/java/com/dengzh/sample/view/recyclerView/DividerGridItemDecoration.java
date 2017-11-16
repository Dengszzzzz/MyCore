package com.dengzh.sample.view.recyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dengzh.sample.R;


public class DividerGridItemDecoration extends RecyclerView.ItemDecoration
{

    private static final int[] ATTRS = new int[] { android.R.attr.listDivider };
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a = context.obtainStyledAttributes(R.style.listDivider,ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividerGridItemDecoration(Context context,int resId){
        final TypedArray a = context.obtainStyledAttributes(resId,ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state){
        super.onDraw(c, parent, state);
        //onDraw其实画了背景
        //drawHorizontal(c, parent);
        //drawVertical(c, parent);

    }

    /**
     * 一行几个item，可以说是列数(竖直情况下)
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent){
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i = 3; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent){
        final int childCount = parent.getChildCount();
        for (int i = 3; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
            int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            if ((pos + 1) % spanCount == 0)//最后一列
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
            int childCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            if (((GridLayoutManager)layoutManager).getOrientation()
                    == GridLayoutManager.VERTICAL) {//竖直方向的
                if(childCount % spanCount == 0 && pos > childCount - spanCount-1){
                    return true;
                }
                if(childCount % spanCount != 0 && pos > childCount - childCount % spanCount -1){
                    return true;
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);  //列数
        int childCount = parent.getAdapter().getItemCount();  //item总数
        int itemPosition = parent.getChildAdapterPosition(view);  //item的位置


        if(type == TYPE_ALL){
            //四面八方都要有间隔
            if (isLastRaw(parent, itemPosition, spanCount, childCount)){  // 如果是最后一行，画下
                outRect.bottom = mDivider.getIntrinsicHeight();
            }
            if (isLastColum(parent, itemPosition, spanCount, childCount)){ // 如果是最后一列，画右
                outRect.right = mDivider.getIntrinsicWidth();
            }
            //必画左上
            outRect.left = mDivider.getIntrinsicWidth();
            outRect.top = mDivider.getIntrinsicHeight();
        }else if(type == TYPE_MIDDLE){
            //只有item与item之间有间隔
            //画右下
            outRect.right = mDivider.getIntrinsicWidth();
            outRect.bottom = mDivider.getIntrinsicHeight();
            if (isLastRaw(parent, itemPosition, spanCount, childCount)){  // 如果是最后一行,不画下
                outRect.bottom = 0;
            }
            if (isLastColum(parent, itemPosition, spanCount, childCount)){ // 如果是最后一列，不画右
                outRect.right = 0;
            }
        }

    }


    public static int TYPE_MIDDLE = 1;
    public static  int TYPE_ALL = 2;
    private int type = TYPE_MIDDLE;
    private void setType(int type){
        this.type = type;
    }
}