package com.dengzh.shop.base;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.dengzh.shop.utils.LogUtil;

/**
 * Created by dengzh on 2017/10/17 0017.
 * 在主module的applicaition中配置
 */

public class ShopModule {

    private Context mContext;
    public static ShopModule mShopModule;

    private ShopModule(){}

    public static ShopModule getInstance(){
        if(mShopModule == null){
            synchronized (ShopModule.class){
                if(mShopModule == null)
                    mShopModule = new ShopModule();
            }
        }
        return mShopModule;
    }

    public void init(Context context,String curVersion,Boolean isDebug){
        AppConfig.mContext = context;
        AppConfig.curVersion = curVersion;
        mContext = context;
        initScreenInfo();
        LogUtil.isDebug = isDebug;
    }

    /**
     * 初始化屏幕信息
     */
    private void initScreenInfo() {
        //屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        AppConfig.screenWidth = dm.widthPixels;
        AppConfig.screenHeight = dm.heightPixels;
    }
}
