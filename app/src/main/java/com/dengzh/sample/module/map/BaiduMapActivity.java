package com.dengzh.sample.module.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/1/1.
 * 仿 饿了吗 地址选择
 */

public class BaiduMapActivity extends BaseActivity implements SensorEventListener {

    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.locationIv)
    ImageView locationIv;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocClient;
    private MyLocationListener myLocationListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;  //标注图，如果为null，则是默认的蓝色箭头
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;  //纬度
    private double mCurrentLon = 0.0;  //经度
    private float mCurrentAccracy;     //精度
    private MyLocationData locData;
    private SensorManager mSensorManager;

    private Double lastX = 0.0;


    boolean isFirstLoc = true; // 是否首次定位
    private GeoCoder geoCoder;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_baidu_map;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("百度地图");
        //传感器
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        //百度地图
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置地图类型,有 普通地图、卫星图、空白地图
        mBaiduMap.setTrafficEnabled(false);              //开启实时路况
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        button1.setText("普通");

        geoCoder = GeoCoder.newInstance();
        initLocation(); //定位
        initListener();  //地图监听


    }

    /**
     * 定位
     */
    private void initLocation() {
        //1.开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //2.定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);       //打开GPS
        option.setCoorType("bd09ll");  // 设置坐标类型
        option.setScanSpan(1000);      // 设置查询范围,默认500

        option.setIsNeedAddress(true);  //需要地址信息，默认false
        option.setIsNeedLocationDescribe(true);  //可选，是否需要位置描述信息，默认为不需要，即参数为false
        option.setIsNeedLocationPoiList(true);  //可选，是否需要周边POI信息，默认为不需要，即参数为false

        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private void initListener(){
        //地图的滑动监听过程
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param mapStatus 地图状态改变开始时的地图状态
             */
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            /** 因某种操作导致地图状态开始改变。
             * @param mapStatus 地图状态改变开始时的地图状态
             * @param reason 表示地图状态改变的原因，取值有：
             * 1：用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
             * 2：SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
             * 3：开发者调用,导致的地图状态改变
             */
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int reason) {

            }

            /**
             * 地图状态变化中
             * @param mapStatus 当前地图状态
             */
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            /**
             * 地图状态改变结束
             * @param mapStatus 地图状态改变结束后的地图状态
             */
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LogUtil.e(TAG,"target:" + mapStatus.target+"");
                //地理编码
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target));
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            ToastUtil.showToast("抱歉，未找到结果");
                            return;
                        }
                        String address = reverseGeoCodeResult.getAddress();
                        LogUtil.e(TAG,"地理编码：" + address);
                    }
                });

            }
        });
    }

    /************************** 传感器 ******************************/
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /************************** 传感器 结束******************************/

    @OnClick(R.id.button1)
    public void onViewClicked() {
        switch (mCurrentMode) {
            case NORMAL:
                button1.setText("跟随");
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.overlook(0);  //俯视程度
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                break;
            case COMPASS:
                button1.setText("普通");
                mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                MapStatus.Builder builder1 = new MapStatus.Builder();
                builder1.overlook(0);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                break;
            case FOLLOWING:
                button1.setText("罗盘");
                mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
                break;
            default:
                break;
        }
    }

    /**
     * 定位SDK监听函数
     * BDLocation  可获取经纬度，地址，
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude(); //纬度
            mCurrentLon = location.getLongitude(); //经度
            mCurrentAccracy = location.getRadius(); //精度
            ToastUtil.showToast(mCurrentLat+"");
            LogUtil.e(TAG, "定位信息：精度-" + mCurrentAccracy + "  经度-" + mCurrentLon + "  纬度-" + mCurrentLat);
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    .direction(mCurrentDirection)  // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon)
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
                MapStatus builder = new MapStatus.Builder()
                        .target(ll)
                        .zoom(18.0f)  //放大18倍
                        .build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(builder);
                mBaiduMap.animateMapStatus(u);
            }

            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            LogUtil.e(TAG, "addr:" + addr);
            LogUtil.e(TAG, "country:" + country);
            LogUtil.e(TAG, "province:" + province);
            LogUtil.e(TAG, "city:" + city);
            LogUtil.e(TAG, "district:" + district);
            LogUtil.e(TAG, "street:" + street);

            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            LogUtil.e(TAG, "locationDescribe:" + locationDescribe);

            List<Poi> poiList = location.getPoiList();
            for (Poi poi : poiList) {
                LogUtil.e(TAG, "poi:" + poi.getName());
            }
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //退出时销毁定位
        mLocClient.stop();
        //关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
