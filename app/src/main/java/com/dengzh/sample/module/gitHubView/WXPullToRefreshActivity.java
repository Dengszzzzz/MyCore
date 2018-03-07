package com.dengzh.sample.module.gitHubView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ContentAdapter;
import com.dengzh.sample.adapter.WXPullAdapter;
import com.dengzh.sample.bean.WXPullBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.pullextend.ExtendListHeader;
import com.dengzh.sample.view.pullextend.PullExtendLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/1/23.
 */

public class WXPullToRefreshActivity extends BaseActivity {


    @BindView(R.id.extend_header)
    ExtendListHeader mPullNewHeader;/*
    @BindView(R.id.pull_extend)
    PullExtendLayout mPullExtendLayout;*/
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    //头部仿小程序
    private RecyclerView headerRv;
    private WXPullAdapter adapter;
    private List<WXPullBean> mList = new ArrayList<>();

    //主体内容
    private List<WXPullBean> mContentList = new ArrayList<>();
    private ContentAdapter contentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_wx_pulltorefresh;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        mList.add(new WXPullBean("我的掘金主页", 0, ""));
        mList.add(new WXPullBean("GitHub地址", 0, ""));
        mList.add(new WXPullBean("百度", 0, ""));
        //adapter
        adapter = new WXPullAdapter(mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast(mList.get(position).getName());
            }
        });
        //recyclerView
        headerRv = mPullNewHeader.getRecyclerView();
        headerRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        headerRv.setAdapter(adapter);

        //判断是否允许下拉上拉
        /*if (adapter.getItemCount() > 0) {
            mPullExtendLayout.setPullLoadEnabled(true);
        } else {
            mPullExtendLayout.closeExtendHeadAndFooter();
            mPullExtendLayout.setPullLoadEnabled(false);
        }*/

        initContent();
    }

    private void initContent(){
        for(int i = 0 ;i<20;i++){
            mContentList.add(new WXPullBean("内容"+i, 0, ""));
        }
        contentAdapter = new ContentAdapter(mContentList);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("点击了" + mContentList.get(position).getName());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentAdapter);
    }



}
