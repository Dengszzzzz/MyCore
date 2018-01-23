package com.dengzh.sample.module.gitHubView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/1/23.
 */

public class MyTransitionActivityA extends BaseActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> mList = new ArrayList<>();
    private static final String CONTENT = "目前，返回的拟南芥样品一部分已做固定处理，拟南芥果荚将带回实验室继续培养。综合材料实验返回的两批次样品将在实验室进行解剖分析研究，第三批次的6个样品将留轨进行装置热特性测量实验，以期揭示在地面重力环境下难以获知的材料物理和化学过程的规律，获得优质材料的空间制备技术和生产工艺，指导地面材料加工工艺的改进与发展。神舟十一号飞船返回后，天宫二号空间实验室转入独立飞行阶段，空间应用系统将继续按计划开展有效载荷在轨测试以及科学实验与探测，进行科学设备的参数精调，开展地球观测设备的定标和同步观测，同时深入分析研究科学实验与探测数据，开展地球观测数据的应用推广，争取获得更大科学成果，取得更大应用效益。";


    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("转场界面A");
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

        for(int i = 0 ;i<50;i++){
            mList.add(CONTENT);
        }
        adapter = new Adapter(mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(view, mList.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    class Adapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public Adapter(@Nullable List<String> data) {
            super(R.layout.item_transtion_a,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.itemTv,item);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivity(View view, String content) {
        View statusBar = findViewById(android.R.id.statusBarBackground);
        View navigationBar = findViewById(android.R.id.navigationBarBackground);

        List<Pair<View, String>> pairs = new ArrayList<>();
        pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
        pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
        pairs.add(Pair.create(view, view.getTransitionName()));

        Intent intent = new Intent(this, MyTransitionActivityB.class);
        intent.putExtra("msg", content);

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, pairs.toArray(new Pair[pairs.size()]));
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

}
