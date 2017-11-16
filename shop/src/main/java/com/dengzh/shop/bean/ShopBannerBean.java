package com.dengzh.shop.bean;

import java.io.Serializable;

/**
 * Created by dengzh on 2017/9/29.
 * 商城首页广告条
 */

public class ShopBannerBean implements Serializable{

    private String imgUrl;
    private String url;

    public ShopBannerBean(String imgUrl, String url) {
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public ShopBannerBean() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
