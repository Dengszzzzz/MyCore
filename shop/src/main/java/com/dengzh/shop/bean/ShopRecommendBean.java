package com.dengzh.shop.bean;

import java.util.List;

/**
 * Created by dengzh on 2017/9/29.
 * 商城首页商品推荐
 */

public class ShopRecommendBean {

    private String imgUrl;  //活动图，只有一张
    private List<GoodsBean> goodsList;  //商品列表
    private int resId;  //资源id

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public List<GoodsBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsBean> goodsList) {
        this.goodsList = goodsList;
    }
}
