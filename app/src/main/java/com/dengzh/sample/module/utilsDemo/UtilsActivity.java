package com.dengzh.sample.module.utilsDemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.bean.ClazzBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.customView.BannerActivity;
import com.dengzh.sample.module.customView.BezierActivity;
import com.dengzh.sample.module.customView.CountDownActivity;
import com.dengzh.sample.module.customView.EditTextActivity;
import com.dengzh.sample.module.customView.LeafLoadingActivity;
import com.dengzh.sample.module.customView.MeasurePathActivity;
import com.dengzh.sample.module.customView.PieChartActivity;
import com.dengzh.sample.module.customView.RadarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/2/25.
 */

public class UtilsActivity extends BaseActivity{

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
        toolbar.setTitle("Utils工具Demo");
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

        mList.add(new ClazzBean("软键盘",SoftInputActivity.class));
        /*mList.add(new ClazzBean("树叶进度条",LeafLoadingActivity.class));
        mList.add(new ClazzBean("编辑框",EditTextActivity.class));
        mList.add(new ClazzBean("雷达图",RadarActivity.class));
        mList.add(new ClazzBean("贝塞尔曲线",BezierActivity.class));
        mList.add(new ClazzBean("MeasurePath学习",MeasurePathActivity.class));
        //mList.add(new ClazzBean("晋级图('未完成')",LevelViewActivity.class));
        mList.add(new ClazzBean("轮播图('未完成')",BannerActivity.class));
        mList.add(new ClazzBean("倒计时图",CountDownActivity.class));*/

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
