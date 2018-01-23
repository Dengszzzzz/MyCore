package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.bean.ClazzBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.customView.CustomViewActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.premission.CallActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.shop.module.home.HomeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/11/4.
 * Rx和Mvp
 */

public class RxAndMvpActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<ClazzBean> mList = new ArrayList<>();


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

        mList.add(new ClazzBean("mvp测试",RegisterActivity.class));
        mList.add(new ClazzBean("retrofit测试",GitHubActivity.class));
        mList.add(new ClazzBean("rxBus测试",RxMsg1Activity.class));
        mList.add(new ClazzBean("rxPermission测试",CallActivity.class));
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
