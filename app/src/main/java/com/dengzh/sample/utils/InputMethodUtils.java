package com.dengzh.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by dengzh on 2018/2/25.
 * 软键盘Utils
 * 1.InputMethodManager
 * 2.清单文件的  windowSoftInputMode 的 adjust和state
 * 3.布局文件滚动布局和固定布局
 * 4.EditText的imeOptions,更改确认键
 */

public class InputMethodUtils {

    /**
     * 显示键盘
     * @param view
     */
    public static void showSoftInput(EditText view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏键盘
     * @param ac
     */
    public static void hideSoftInput(Activity ac) {
        InputMethodManager imm = (InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(ac.getCurrentFocus().getWindowToken(),0);  //此方法可以传activity的任意view的viewToken
        }
    }

    /**
     * 切换软键盘
     * 使用toggleSoftInput()显示软键盘时，并不要求当前界面布局中有一个已经获取焦点的EditText，但软键盘中的按键输入都是无效的
     * 显示软键盘时，要求当前布局必须已经加载完成
     * 使用toggleSoftInput(0, 0)应当是最方便的无条件隐藏软键盘的方法
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * 显示键盘
     */
    public static void showInputMethod(EditText view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.requestFocusFromTouch();
        view.setSelection(view.getText().toString().length());
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }



}
