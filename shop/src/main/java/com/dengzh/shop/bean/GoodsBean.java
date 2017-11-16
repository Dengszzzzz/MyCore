package com.dengzh.shop.bean;

import java.io.Serializable;

/**
 * Created by dengzh on 2017/9/29.
 */

public class GoodsBean implements Serializable{

    private int id;
    private String name;
    private String imgUrl;
    private String desc;
    private double price;
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public GoodsBean(int id, String name, String imgUrl, String desc, double price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.price = price;
    }

    public GoodsBean() {
    }
}
