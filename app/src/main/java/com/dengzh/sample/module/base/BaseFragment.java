package com.dengzh.sample.module.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengzh.core.mvp.BasePresenter;
import com.dengzh.core.mvp.IBaseModel;
import com.dengzh.core.mvp.IBaseView;
import com.dengzh.sample.utils.TUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dengzh on 2017/9/8 0008.
 */

public abstract class BaseFragment <T extends BasePresenter, E extends IBaseModel> extends RxFragment {

    public T mPresenter;
    public E mModel;

    protected Activity mActivity;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container,false);
        unbinder = ButterKnife.bind(this, view);
        //绑定MVP
        mPresenter= TUtil.getT(this,0);
        mModel= TUtil.getT(this,1);
        if(this instanceof IBaseView){
            mPresenter.attachVM((IBaseView) this,mModel);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //执行在onCreateView之后，在onStart之前
        initUI();
        initData();
    }

    protected abstract int getLayoutId();
    protected abstract void initUI();
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

}
