package com.dengzh.sample.view.flexboxlayout;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dengzh on 2018/1/1.
 */

public abstract class TagAdapter<T> {

    private List<T> mDatas;

    public TagAdapter(List<T> datas) {
        if(datas == null)
            datas = new ArrayList<>(0);
        this.mDatas = datas;
    }

    public TagAdapter(T[] datas) {
        mDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    protected abstract View getView(ViewGroup parent,int position,T t);

    /**
     * 传入position，如果返回true，则默认选中
     * @param position
     * @return
     */
    protected boolean select(int position){
        return false;
    }

    //未选择->选中
    protected void onSelect(ViewGroup parent,View view,int position){}
    //选中->未选中
    protected void onUnSelect(ViewGroup parent,View view,int position){}

    protected T getItem(int position){
        return mDatas.get(position);
    }

    protected int getCount(){
        return mDatas.size();
    }

    protected boolean enabled(int position){
        return true;
    }

    //接口回调更新，应该是让界面调用adapter.notifyDataSetChanged()去更新数据集
    public void notifyDataSetChanged() {
        if (mOnAdapterDataChanged != null) {
            mOnAdapterDataChanged.onChange();
        }
    }

    private OnAdapterDataChanged mOnAdapterDataChanged;

    void setOnAdapterDataChanged(OnAdapterDataChanged onAdapterDataChanged){
        mOnAdapterDataChanged = onAdapterDataChanged;
    }

    interface OnAdapterDataChanged{
        void onChange();
    }



}
