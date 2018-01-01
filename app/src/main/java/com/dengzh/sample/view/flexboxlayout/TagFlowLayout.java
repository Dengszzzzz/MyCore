package com.dengzh.sample.view.flexboxlayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dengzh on 2018/1/1.
 * 封装 FlexboxLayout
 */

public class TagFlowLayout extends FlexboxLayout implements TagAdapter.OnAdapterDataChanged {

    private boolean mCheckable = true;  //默认可点击
    private TagAdapter mAdapter;

    private Set<Integer> mSelectedItem = new HashSet<Integer>();
    private Set<Integer> mPreSelectedItem = new HashSet<Integer>();

    public TagFlowLayout(Context context) {
        super(context);
        init();
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFlexDirection(FlexDirection.ROW);  //设置元素排列方向
        setFlexWrap(FlexWrap.WRAP);           //设置自动换行
    }

    public void setCheckable(boolean isCheckable) {
        mCheckable = isCheckable;
    }

    public void setAdapter(TagAdapter adapter) {
        mAdapter = adapter;
        adapterChanged();
    }

    private void clearForDataChanged() {
        removeAllViews();
        mSelectedItem.clear();
    }

    private void adapterChanged() {
        if (mAdapter != null) {
            mAdapter.setOnAdapterDataChanged(this);
            dataChanged();
        }
    }

    private void dataChanged() {
        clearForDataChanged();
        TagAdapter adapter = mAdapter;
        if (adapter == null) return;
        for (int i = 0, n = adapter.getCount(); i < n; i++) {
            View view = adapter.getView(this, i, adapter.getItem(i));
            TagView tagView = null;

            if (!(view instanceof TagView) && mCheckable) {
                tagView = TagView.wrap(getContext(), view);  //把view添加入tagView中
                addView(tagView);    //把TagView添加入TagFlowLayout中
                viewClickableSet(tagView, i);  //设置tagView点击事件

                if (adapter.select(i)) {
                    mPreSelectedItem.add(i);  //已选的加到某个集合
                    tagView.setChecked(true);
                }
            } else {
                addView(view);
                viewClickableSet(view, i);

                if (adapter.select(i)) {
                    mPreSelectedItem.add(i);
                    adapter.onSelect(this, view, i);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mPreSelectedItem.size() > 0) {
            mSelectedItem.addAll(mPreSelectedItem);
            mPreSelectedItem.clear();
        }
    }

    /**
     * 设置子view的点击事件
     *
     * @param view
     * @param position
     */
    private void viewClickableSet(final View view, final int position) {
        if (!mAdapter.enabled(position)) return;
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TagView的选择事件
                if ((view instanceof TagView) && mCheckable) {
                    TagView tagView = (TagView) view;
                    boolean checked = tagView.isChecked();
                    if (checked) { //已结选中->取消选中
                        tagView.setChecked(false);
                        mSelectedItem.remove(position);
                    } else {  //未选中 -> 选中
                        tagView.setChecked(true);
                        mSelectedItem.add(position);
                    }
                }
                //Tag标签点击事件
                if (mOnTagClickListener != null) {
                    mOnTagClickListener.onTagClick(TagFlowLayout.this, TagView.unwarp(view), position);
                }

                //普通view的选择事件
                if (!mCheckable) {
                    if (mSelectedItem.contains(position)) {  //已选中 -> 未选中
                        mSelectedItem.remove(position);      //移除当前选中位置
                        mAdapter.onUnSelect(TagFlowLayout.this, view, position);  //该View设成未选中
                    } else {
                        mSelectedItem.add(position);  //未选中 -> 选中
                        mAdapter.onSelect(TagFlowLayout.this, view, position);  //该view设成已选中
                    }
                }

            }
        });
    }


    @Override
    public void onChange() {
        dataChanged();
    }

    public  Set<Integer> getSelectedItems(){
        return  mSelectedItem;
    }

    /**
     * tag点击事件接口回调
     */
    private OnTagClickListener mOnTagClickListener;

    public interface OnTagClickListener {
        void onTagClick(ViewGroup parent, View view, int position);
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }


    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_CHECKABLE = "key_checkable";
    private static final String KEY_DEFAULT = "key_default";

    /**
     * 界面被回收时保存数据
     * @return
     */
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());

        String selectPos = "";
        if (mSelectedItem.size() > 0){
            for (int key : mSelectedItem)
            {
                selectPos += key + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos);
        bundle.putBoolean(KEY_CHECKABLE, mCheckable);
        return super.onSaveInstanceState();
    }

    /**
     * 界面被重建时重新复原数据
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (mAdapter == null){
            super.onRestoreInstanceState(state);
            return;
        }
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            String mSelectPos = bundle.getString(KEY_CHOOSE_POS);
            mCheckable = bundle.getBoolean(KEY_CHECKABLE, true);
            if (!TextUtils.isEmpty(mSelectPos)){
                String[] split = mSelectPos.split("\\|");
                for (String pos : split) {
                    int index = Integer.parseInt(pos);
                    mSelectedItem.add(index);
                }
                if (mSelectedItem.size() > 0){
                    for (int i : mPreSelectedItem){
                        if (!mCheckable)
                            mAdapter.onUnSelect(this, getChildAt(i), i);
                        else
                            ((TagView) getChildAt(i)).setChecked(false);
                    }
                    mPreSelectedItem.clear();
                }else{
                    mSelectedItem.addAll(mPreSelectedItem);
                    mPreSelectedItem.clear();
                }

                for (int index : mSelectedItem){
                    if (mCheckable){
                        TagView tagView = (TagView) getChildAt(index);
                        if (tagView != null)
                            tagView.setChecked(true);
                    }else{
                        mAdapter.onSelect(this, getChildAt(index), index);
                    }
                }
            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
