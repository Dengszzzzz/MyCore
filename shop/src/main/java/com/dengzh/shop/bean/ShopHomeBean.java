package com.dengzh.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengzh on 2017/9/29.
 * 商城首页
 */

public class ShopHomeBean implements Serializable{

    private List<ShopBannerBean> bannerList;    //广告
    private List<GoodsClassifyBean> goodsTypeList;  //商品类别
    private ShopRecommendBean recommendBean;    //推荐
    private List<GoodsBean>   goodsList;        //猜你喜欢 -- 商品列表

    public List<ShopBannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<ShopBannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<GoodsClassifyBean> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<GoodsClassifyBean> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    public ShopRecommendBean getRecommendBean() {
        return recommendBean;
    }

    public void setRecommendBean(ShopRecommendBean recommendBean) {
        this.recommendBean = recommendBean;
    }

    public List<GoodsBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsBean> goodsList) {
        this.goodsList = goodsList;
    }
}
