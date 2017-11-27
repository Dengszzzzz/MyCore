package com.dengzh.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.customView.CustomViewActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.module.retrofit.RxAndMvpActivity;
import com.dengzh.sample.module.sqlite.SqliteTestActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.shop.module.home.HomeActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("首页");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("返回");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    protected void initData() {
        String[] strs = {"测试","依赖包商城","友盟测试","自定义view","Dialog和PopWindow","rxjava ,retrofit 和 mvp","Realm和GreenDao"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(GitHubActivity.class);
                        break;
                    case 1:
                        startActivity(HomeActivity.class);
                        break;
                    case 2:
                        startActivity(UmengActivity.class);
                        break;
                    case 3:
                        startActivity(CustomViewActivity.class);
                        break;
                    case 4:
                        startActivity(DialogAndPopActivity.class);
                        break;
                    case 5:
                        startActivity(RxAndMvpActivity.class);
                        break;
                    case 6:
                        startActivity(SqliteTestActivity.class);
                        break;
                }
            }
        });
    }

}
