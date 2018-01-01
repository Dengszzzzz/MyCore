package com.dengzh.sample.view.flexboxlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.jude.rollviewpager.Util;

/**
 * Created by dengzh on 2018/1/1.
 */

public class TagView extends FrameLayout implements Checkable{

    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(Context context) {
        super(context);
    }

    public static TagView wrap(Context context,View view){
        if (view == null)
            throw new IllegalArgumentException("view can not be null .");
        view.setDuplicateParentStateEnabled(true);
        TagView tagView = new TagView(context);
        if(view.getLayoutParams() !=null){
            tagView.setLayoutParams(view.getLayoutParams());
        }else{
            //设置了边距
            MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            int defaultMargin = Util.dip2px(context, 4);
            lp.setMargins(defaultMargin,
                    defaultMargin,
                    defaultMargin,
                    defaultMargin);
            tagView.setLayoutParams(lp);
        }
        tagView.addView(view);

        return tagView;
    }

    public static View unwarp(View view){
        if(view == null){
            throw new IllegalArgumentException("view can not be null");
        }
        if(view instanceof TagView){
            return ((TagView) view).getChildAt(0);  //返回TagView的第一个子View
        }else{
            return view;
        }
    }

    /**
     *
     * @param extraSpace
     * @return
     */
    @Override
    public int[] onCreateDrawableState(int extraSpace){
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()){
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if(this.isChecked != checked){
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
