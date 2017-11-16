package com.dengzh.core.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengzh.sample.R;

import retrofit2.http.PATCH;

/**
 * Created by dengzh on 2017/11/2.
 * Dialog基类
 */

public class BaseDialog {

    private Dialog mDialog;
    protected Context mContext;
    private SparseArray<View> mViews;
    private boolean cancelOnTouchOutside;
    protected int layoutResId;
    protected int gravity = Gravity.CENTER;
    protected boolean isUp;

    public BaseDialog(Context mContext, int layoutResId, int gravity,boolean cancelOnTouchOutside) {
        this(mContext,layoutResId,gravity,cancelOnTouchOutside,false);
    }

    /**
     * @param mContext
     * @param layoutResId 布局id
     * @param gravity     位置
     * @param cancelOnTouchOutside  是否点击外部取消
     * @param isUp        从下往上动画
     */
    public BaseDialog(Context mContext, int layoutResId, int gravity,boolean cancelOnTouchOutside,boolean isUp) {
        this.mContext = mContext;
        this.layoutResId = layoutResId;
        this.gravity = gravity;
        this.cancelOnTouchOutside = cancelOnTouchOutside;
        this.isUp = isUp;
        mViews = new SparseArray<>();
        initView();
    }


    private void initView(){
        if(!isUp){
            mDialog = new Dialog(mContext);
            mDialog.setContentView(layoutResId);
            mDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            mDialog.getWindow().setGravity(gravity);
            WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(params);
        }else{
            View view = View.inflate(mContext,layoutResId, null);
            mDialog = new Dialog(mContext,R.style.transparentFrameWindowStyle);
            mDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            Window window = mDialog.getWindow();
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // 设置显示位置
            mDialog.onWindowAttributesChanged(wl);
            // 设置点击外围解散
            mDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        }
    }

    /**
     * 取消蓝色线 4.4系统出现蓝线
     */
    private void cancelDialogTitleLineColor(){
        int divierId = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = mDialog.findViewById(divierId);
        if(divider != null){
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
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
            view = mDialog.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 切换dialog
     */
    public void toggleDialog(){
        if(mDialog!=null){
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }else{
                mDialog.show();
            }
        }
    }


    public BaseDialog setText(int viewId,int resId){
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    public BaseDialog setText(int viewId,String textContent){
        TextView textView = getView(viewId);
        textView.setText(textContent);
        return this;
    }

    public BaseDialog setOnViewClick(int viewId, View.OnClickListener listener){
        if(listener!=null){
            getView(viewId).setOnClickListener(listener);
        }
        return this;
    }

}
