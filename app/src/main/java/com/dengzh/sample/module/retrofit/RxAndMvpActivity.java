package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.premission.CallActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/11/4.
 * Rx和Mvp
 */

public class RxAndMvpActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("RxJava和MVP");
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
        String[] strs = {"mvp测试","retrofit测试","rxBus测试","rxPermission测试"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(RegisterActivity.class);
                        break;
                    case 1:
                        startActivity(GitHubActivity.class);
                        break;
                    case 2:
                        startActivity(RxMsg1Activity.class);
                        break;
                    case 3:
                        startActivity(CallActivity.class);
                        break;
                }
            }
        });
    }

}
