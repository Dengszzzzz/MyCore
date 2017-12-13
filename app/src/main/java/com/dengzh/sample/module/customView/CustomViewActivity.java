package com.dengzh.sample.module.customView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.module.retrofit.RxAndMvpActivity;
import com.dengzh.sample.module.sqlite.SqliteTestActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.shop.module.home.HomeActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/3.
 */

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("自定义view");
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
        String[] strs = {"饼状图","树叶进度条","编辑框","雷达图","贝塞尔曲线","MeasurePath学习","晋级图"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(PieChartActivity.class);
                        break;
                    case 1:
                        startActivity(LeafLoadingActivity.class);
                        break;
                    case 2:
                        startActivity(EditTextActivity.class);
                        break;
                    case 3:
                        startActivity(RadarActivity.class);
                        break;
                    case 4:
                        startActivity(BezierActivity.class);
                        break;
                    case 5:
                        startActivity(MeasurePathActivity.class);
                        break;
                    case 6:
                        startActivity(LevelViewActivity.class);
                        break;
                }
            }
        });
    }

}
