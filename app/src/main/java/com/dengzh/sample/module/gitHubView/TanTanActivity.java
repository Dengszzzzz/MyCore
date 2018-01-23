package com.dengzh.sample.module.gitHubView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.adapter.TantanAdapter;
import com.dengzh.sample.bean.TantanBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.recyclerView.CardConfig;
import com.dengzh.sample.view.recyclerView.OverLayCardLayoutManager;
import com.dengzh.sample.view.recyclerView.TanTanCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/1/17.
 * 仿探探订阅界面，炫动滑动 卡片层叠布局    ---- 学习
 * 原作者
 * http://blog.csdn.net/zxt0601/article/details/53730908
 * https://github.com/mcxtzhang/ZLayoutManager
 */

public class TanTanActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TantanAdapter adapter;
    private List<TantanBean> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.ac_tantan;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("仿探探订阅界面");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        CardConfig.initConfig(this);
        CardConfig.MAX_SHOW_COUNT = 4;

        int i = 1;
        mDatas.add(new TantanBean(i++, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        mDatas.add(new TantanBean(i++, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        mDatas.add(new TantanBean(i++, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        mDatas.add(new TantanBean(i++, "http://www.kejik.com/image/1460343965520.jpg", "多种type"));
        mDatas.add(new TantanBean(i++, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        mDatas.add(new TantanBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        mDatas.add(new TantanBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        mDatas.add(new TantanBean(i++, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));

        adapter = new TantanAdapter(mDatas);
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter();

        //滑动监听
        TanTanCallback callback = new TanTanCallback(recyclerView, adapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
