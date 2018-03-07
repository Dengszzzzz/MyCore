package com.dengzh.sample.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.dengzh.core.utils.LogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dengzh on 2018/2/25.
 * 解决 软键盘遮盖没有遮盖焦点，但是遮盖了需要显示的控件
 * SoftKeyBroadManager mManager =new SoftKeyBroadManager ("根布局");
 //添加软键盘的监听，然后和上面一样的操作即可.
 mSoftKeyBroadManager.addSoftKeyboardStateListener();
 //注意销毁时，得移除监听
 mSoftKeyBroadManager.removeSoftKeyboardStateListener();
 */

public class SoftKeyBroadManager  implements ViewTreeObserver.OnGlobalLayoutListener{


    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int keyboardHeightInPx);

        void onSoftKeyboardClosed();
    }

    private final List<SoftKeyboardStateListener> listeners = new LinkedList<SoftKeyboardStateListener>();
    private final View activityRootView;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened;

    public SoftKeyBroadManager(View activityRootView) {
        this(activityRootView,false);
    }

    public SoftKeyBroadManager(View activityRootView, boolean isSoftKeyboardOpened) {
        this.activityRootView = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        //r will be populated with the coordinates of your view that area still visible.
        activityRootView.getWindowVisibleDisplayFrame(r);

        int rootViewHeight =  activityRootView.getRootView().getHeight();

        final int heightDiff = rootViewHeight - (r.bottom - r.top);
        LogUtils.e("SoftKeyboardStateHelper", "heightDiff:" + heightDiff);
        if (!isSoftKeyboardOpened && heightDiff > 500) { // if more than 100 pixels， its probably a keyboard...
            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(heightDiff);
            //if (isSoftKeyboardOpened && heightDiff < 100)
        } else if (isSoftKeyboardOpened && heightDiff < 500) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    /**
     * Default value is zero (0)
     *
     * @return last saved keyboard height in px
     */
    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx;

        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }
}
