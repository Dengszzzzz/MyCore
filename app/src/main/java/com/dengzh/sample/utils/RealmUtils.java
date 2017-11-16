package com.dengzh.sample.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dengzh on 2017/11/4.
 */

public class RealmUtils {

    private static RealmUtils instance;
    private Context mContext;

    private RealmUtils(){};

    public static RealmUtils getInstance(){
        if(instance == null){
            synchronized (RealmUtils.class){
                if(instance==null)
                    instance = new RealmUtils();
            }
        }
        return instance;
    }

    public void init(Context context){
        mContext = context;
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myCore.realm")  //文件名
                .schemaVersion(0)      //版本号
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
