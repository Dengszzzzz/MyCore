package com.dengzh.sample.module.map;

import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/1/1.
 */

public class BaiduMapActivity extends BaseActivity {


    @BindView(R.id.bmapView)
    MapView mMapView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_baidu_map;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("百度地图");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
