package com.dengzh.sample.module.bluetooth;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.module.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/22.
 * 蓝牙开发
 */

public class BluetoothDemoActivity extends BaseActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("蓝牙开发");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        String[] strs = {"蓝牙开发测试","蓝牙服务端","蓝牙客户端"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(BluetoothActivity.class);
                        break;
                    case 1:

                        break;
                }
            }
        });
    }
}
