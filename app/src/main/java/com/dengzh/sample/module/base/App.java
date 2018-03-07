package com.dengzh.sample.module.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dengzh.core.net.BaseHttpConfig;
import com.dengzh.core.utils.LogUtils;
import com.dengzh.sample.R;
import com.dengzh.sample.net.HttpConfig;
import com.dengzh.sample.utils.APPUtils;
import com.dengzh.sample.utils.CrashHandler;
import com.dengzh.sample.utils.CustomMigrationUtils;
import com.dengzh.shop.base.ShopModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class App extends Application{

    public static Context ctx;  //上下文
    public static String curVersion;  //当前版本号

    //屏幕参数
    public static int screenWidth;
    public static int screenHeight;

    //环境相关
    public static final int CONFIG_TYPE = 0; // 0: 开发环境 1: 测试环境 2：运用环境
    public static final int CONFIG_DEVELOP = 0;        // 开发环境
    public static final int CONFIG_TEST = 1;        // 测试环境
    public static final int CONFIG_RELEASE = 2;        // 运用环境

    //SmartRefreshLayout 统一下拉刷新上拉加载样式
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        initConfig();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    /**
     * 初始化配置
     */
    private void initConfig(){
        //友盟
        initUMengConfig();
        //Realm数据库
        initRealmConfig();
        //core网络
        initBaseHttpConfig();
        //本地打印奔溃信息
        CrashHandler.getInstance().init(getApplicationContext());
        //初始化百度地图
        initBaiduMapConfig();

        //屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        //版本号
        curVersion = APPUtils.getVersion(this, false);
        //环境
        switch (CONFIG_TYPE) {
            case CONFIG_DEVELOP:
                LogUtils.isDebug = true;
                break;
            case CONFIG_TEST:
                LogUtils.isDebug = true;
                break;
            case CONFIG_RELEASE:
                LogUtils.isDebug = false;
                break;
        }

        //商城模块
        ShopModule.getInstance().init(this,"1",true);
    }

    /**
     * 初始化友盟配置
     */
    private void initUMengConfig(){
        /** 统计 **/
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);  //普通统计场景类型
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"fb8155f539a29002aabdfdecb482a352");  //初始化
        UMConfigure.setLogEnabled(false);  //默认false
        UMConfigure.setEncryptEnabled(false);  //设置日志加密
        MobclickAgent.setCatchUncaughtExceptions(true);  //捕获程序崩溃日志

        /** 推送 **/
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //当资源包名和应用程序包名不一致时，调用设置资源包名的接口
        mPushAgent.setResourcePackageName("com.dengzh.sample");
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
               // LogUtil.e("deviceToken",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        /** 分享 **/
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx51a45e71aad7e074", "be28aa9eed69bba0bcc4997970234d8e");
        PlatformConfig.setQQZone("1106391087", "GLfS8M1AnS5YiwwR");


    }

    /**
     * 初始化Realm
     */
    private void initRealmConfig(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myCore.realm")  //文件名
                .schemaVersion(1)      //版本号
                .migration(new CustomMigrationUtils())  //更新数据库的类
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * 初始化core网络配置
     */
    private void initBaseHttpConfig(){
        new BaseHttpConfig.Builder().
                setBaseUrl(HttpConfig.BASE_URL)
                .setTimeoutConnection(HttpConfig.TIMEOUT_CONNECTION)
                .setTimeoutRead(HttpConfig.TIMEOUT_READ)
                .setCachePath(HttpConfig.cachePath)
                .build();
    }

    /**
     * 初始化百度地图配置
     */
    private void initBaiduMapConfig(){
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
