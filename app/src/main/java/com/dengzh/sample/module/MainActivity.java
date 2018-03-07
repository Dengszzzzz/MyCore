package com.dengzh.sample.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.bean.ClazzBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.bluetooth.BluetoothDemoActivity;
import com.dengzh.sample.module.customView.CustomViewActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.gitHubView.GithubViewActivity;
import com.dengzh.sample.module.map.BaiduMapActivity;
import com.dengzh.sample.module.materialDesign.MaterDesignActivity;
import com.dengzh.sample.module.qrCode.QrCodeActivity;
import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.module.retrofit.RxAndMvpActivity;
import com.dengzh.sample.module.sqlite.SqliteTestActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.sample.module.utilsDemo.UtilsActivity;
import com.dengzh.sample.module.video.VideoActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.shop.module.home.HomeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


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
        mList.add(new ClazzBean("依赖包商城",HomeActivity.class));
        mList.add(new ClazzBean("友盟测试",UmengActivity.class));
        mList.add(new ClazzBean("自定义view",CustomViewActivity.class));
        mList.add(new ClazzBean("Dialog和PopWindow",DialogAndPopActivity.class));
        mList.add(new ClazzBean("rxjava ,retrofit 和 mvp",RxAndMvpActivity.class));
        mList.add(new ClazzBean("Realm和GreenDao",SqliteTestActivity.class));
        mList.add(new ClazzBean("5.0以上新控件",MaterDesignActivity.class));
        mList.add(new ClazzBean("二维码",QrCodeActivity.class));
        mList.add(new ClazzBean("蓝牙开发测试(未完成)",BluetoothDemoActivity.class));
        mList.add(new ClazzBean("地图(未完成)",BaiduMapActivity.class));
        mList.add(new ClazzBean("Github优秀View",GithubViewActivity.class));
        mList.add(new ClazzBean("音视频", VideoActivity.class));
        mList.add(new ClazzBean("工具类",UtilsActivity.class));

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
