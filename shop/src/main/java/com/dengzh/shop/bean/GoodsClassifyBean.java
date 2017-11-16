package com.dengzh.shop.bean;

/**
 * Created by dengzh on 2017/9/29.
 * 商品类别
 */

public class GoodsClassifyBean {

    private String name;
    private String imgUrl;
    private String url;  //应该是一个type才对
    private int resId;   //图片资源id

    public GoodsClassifyBean() {
    }

    public GoodsClassifyBean(String name, String imgUrl, String url,int resId) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.url = url;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
