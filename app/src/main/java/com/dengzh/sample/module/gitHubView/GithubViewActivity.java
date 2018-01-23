package com.dengzh.sample.module.gitHubView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.bean.ClazzBean;
import com.dengzh.sample.module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/1/17.
 */

public class GithubViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<ClazzBean> mList = new ArrayList<>();
    private ItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("Github优秀View");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {

        mList.add(new ClazzBean("仿探探 炫动滑动 卡片层叠布局,学习RecyclerView的自定义LayoutManager",TanTanActivity.class));
        mList.add(new ClazzBean("仿人人 炫动滑动 卡片层叠布局",RenRenActivity.class));
        mList.add(new ClazzBean("高仿微信可拖拽返回PhotoView",DragPhotoViewActivity.class));
        mList.add(new ClazzBean("自定义转场动画",MyTransitionActivityA.class));
        mList.add(new ClazzBean("芝麻信用分 雷达图",CreditScoreViewActivity.class));
        mList.add(new ClazzBean("美团商品分类 viewPager + GridView",MeiTuanGoodsTypeActivity.class));
        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(mList.get(position).getClazz());
            }
        });
    }
}
