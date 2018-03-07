package com.dengzh.sample.bean;

import java.io.Serializable;

/**
 * Created by dengzh on 2018/2/26.
 */

public class WXPullBean implements Serializable{

    public String name;
    public int iconRes;
    public String url;

    public WXPullBean(String name, int iconRes,String url) {
        this.name = name;
        this.iconRes = iconRes;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
