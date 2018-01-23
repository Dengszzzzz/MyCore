package com.dengzh.sample.bean;

/**
 * Created by dengzh on 2018/1/23.
 */

public class GoodsTypeBean {

    public String name;
    public int iconRes;

    public GoodsTypeBean(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
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
