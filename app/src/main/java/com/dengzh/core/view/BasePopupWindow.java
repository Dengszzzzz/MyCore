package com.dengzh.core.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.os.Build;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dengzh.sample.R;

/**
 * Created by dengzh on 2017/11/3.
 * LayoutInflate理解
 * 1.activity 中方法 getLayoutInflater() 等价于其他地方调用  LayoutInflater.from(context)；
 * 2.以下三种方法调用理解
 * Inflate(resId , null ) 只创建temp ,返回temp
 * Inflate(resId , parent, false )创建temp，然后执行temp.setLayoutParams(params);返回temp
 * Inflate(resId , parent, true ) 创建temp，然后执行root.addView(temp, params);最后返回root
 * 举个栗子：
 * 1）在listView中调用第三个种，会报错，此时返回的view是listView而不是itemView
 * 2）由于BasePopupWindow不打算绑定rootView，所以无法使用第二种方法，width和height要手动设置了
 */

public class BasePopupWindow {
    //不设置成abstract 是考虑到工具类可能直接使用  BasePopupWindow pop = new BasePopWindow();
    protected Context mContext;
    protected PopupWindow popupWindow;
    private SparseArray<View> mViews;
    protected View contentView;
    private boolean cancelOnTouchOutside;
    private int width ;
    private int height ;


    /**
     * 不手动设定宽高，则默认使用wrap_content
     * @param mContext
     * @param layoutResId
     * @param cancelOnTouchOutside
     */
    public BasePopupWindow(Context mContext, int layoutResId,boolean cancelOnTouchOutside){
        this(mContext,layoutResId, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
    }

    public BasePopupWindow(Context mContext, int layoutResId,int width,int height,boolean cancelOnTouchOutside) {
        this.mContext = mContext;
        this.cancelOnTouchOutside = cancelOnTouchOutside;
        this.width = width;
        this.height = height;
        contentView = LayoutInflater.from(mContext).inflate(layoutResId,null,false);
        mViews = new SparseArray<>();
        initWindow();
    }

    private void initWindow(){
        //1.创建PopupWindow对象，指定宽度和高度
        popupWindow = new PopupWindow(contentView,width,height);
        popupWindow.setFocusable(true);  //2.设置可获取焦点
        popupWindow.setOutsideTouchable(cancelOnTouchOutside); //设置可以触摸弹出框以外区域
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);// 实例化一个ColorDrawable颜色为半透明
        popupWindow.setBackgroundDrawable(dw); //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作

        //设置动画
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
    }

    /**
     * 相对于某个控件显示
     * 实际上 在某个view下方弹出
     * 系统为7.0时，showAsDropDown()不起效果 需要做如下处理
     * @param anchor
     * @param xoff
     * @param yoff
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(anchor, xoff, yoff);
        }else{
            // 获取控件的位置，安卓系统>7.0
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, location[1] + anchor.getHeight());
        }

    }

    /**
     * 相对于整个窗口 显示
     * @param parent  并不是指把popView放到parent里，这个parent的作用是调用其
     *                getWindowToken()方法获取窗口的Token，例如DialogAndPopActivity中 parent只是给了个button
     * @param gravity  Gravity.LEFT|Gravity.BOTTOM 这些
     * @param x
     * @param y
     */
    public void showAtLocation(View parent, int gravity, int x, int y) {
        popupWindow.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 通过id寻找控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = contentView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public BasePopupWindow setText(int viewId,int resId){
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    public BasePopupWindow setText(int viewId,String textContent){
        TextView textView = getView(viewId);
        textView.setText(textContent);
        return this;
    }

}
