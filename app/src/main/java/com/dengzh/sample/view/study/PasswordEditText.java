package com.dengzh.sample.view.study;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.dengzh.sample.R;

/**
 * Created by dengzh on 2017/10/17 0017.
 * 密码编辑框
 */

public class PasswordEditText extends AppCompatEditText implements View.OnTouchListener{

    private Drawable mShowDrawable;
    private Drawable mHideDrawable;
    private boolean isShow;

    public PasswordEditText(Context context) {
        this(context,null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mHideDrawable = getResources().getDrawable(R.mipmap.hide_pwd);
        mShowDrawable = getResources().getDrawable(R.mipmap.show_pwd_icon);
        //设置icon大小
        mHideDrawable.setBounds(0,0,mHideDrawable.getIntrinsicWidth(),mHideDrawable.getIntrinsicHeight());
        mShowDrawable.setBounds(0,0,mShowDrawable.getIntrinsicWidth(),mShowDrawable.getIntrinsicHeight());
        setContentStatus(false);
        //设置点击事件
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            int x = (int) event.getX();
            int y = (int) event.getY();
            Rect rect = getCompoundDrawables()[2].getBounds();
            int height = rect.height();
            int distance = (getHeight() - height)/2;
            boolean isInnerWidth = x>(getWidth()-getTotalPaddingRight())
                    && x<(getWidth() - getPaddingRight());
            boolean isInnerHeight = y>distance && y<(distance + height);
            if(isInnerWidth && isInnerHeight){
                isShow = !isShow;
                setContentStatus(isShow);
                return true;
            }
        }
        return false;
    }

    private void setContentStatus(boolean isShow){
        if(isShow){
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        Drawable mEyeDrawable = isShow? mShowDrawable:mHideDrawable;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],mEyeDrawable
                ,getCompoundDrawables()[3]);
    }

}
