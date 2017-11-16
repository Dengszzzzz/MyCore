package com.dengzh.shop.module.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.shop.R;
import com.dengzh.shop.R2;
import com.dengzh.shop.adapter.GoodsAdapter;
import com.dengzh.shop.adapter.HomeBannerAdapter;
import com.dengzh.shop.adapter.HomeClassifyAdapter;
import com.dengzh.shop.bean.GoodsBean;
import com.dengzh.shop.bean.GoodsClassifyBean;
import com.dengzh.shop.bean.ShopBannerBean;
import com.dengzh.shop.bean.ShopHomeBean;
import com.dengzh.shop.bean.ShopRecommendBean;
import com.dengzh.shop.module.BaseActivity;
import com.dengzh.shop.utils.LogUtil;
import com.dengzh.shop.utils.ToastUtil;
import com.dengzh.shop.view.recyclerView.FullyGridLayoutManager;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/9/29.
 * 商城首页
 * recyclerView 分头部 和 商品
 */

public class ShopHomeActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private RollPagerView viewPager;
    private RecyclerView classifyRv;
    private ImageView recommendIv;
    private RecyclerView recommendRv;


    //数据
    private ShopHomeBean shopHomeBean;
    private List<ShopBannerBean> bannerList = new ArrayList<>();
    private List<GoodsClassifyBean> classifyList = new ArrayList<>();
    private ShopRecommendBean recommendBean;
    private List<GoodsBean> likeList = new ArrayList<>();
    //适配器
    private GoodsAdapter adapter;  //猜你喜欢
    private HomeBannerAdapter homeBannerAdapter;  //广告
    private HomeClassifyAdapter classifyAdapter;  //分类
    private GoodsAdapter reCommendAdapter;  //推荐

    @Override
    protected int getLayoutId() {
        return R.layout.shop_ac_shop_home;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new GoodsAdapter(likeList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("点击跳转，点击位置" + position);
            }
        });
    }

    @Override
    protected void initData() {

        //测试数据
        shopHomeBean = new ShopHomeBean();
        //广告
        bannerList.add(new ShopBannerBean("imgurl1","url1"));
        bannerList.add(new ShopBannerBean("imgurl2","url2"));
        shopHomeBean.setBannerList(bannerList);
        //分类
        classifyList.add(new GoodsClassifyBean("红茶","img1","url1",R.mipmap.shop_hongcha));
        classifyList.add(new GoodsClassifyBean("绿茶","img2","url2",R.mipmap.shop_lvcha));
        classifyList.add(new GoodsClassifyBean("黑茶","img3","url3",R.mipmap.shop_heicha));
        classifyList.add(new GoodsClassifyBean("乌龙茶","img4","url4",R.mipmap.shop_wulongcha));
        classifyList.add(new GoodsClassifyBean("花草茶","img5","url5",R.mipmap.shop_huachacha));
        classifyList.add(new GoodsClassifyBean("茶具","img6","url6",R.mipmap.shop_chaju));
        shopHomeBean.setGoodsTypeList(classifyList);
        //推荐商品
        recommendBean = new ShopRecommendBean();
        recommendBean.setResId(R.mipmap.shop_goods_banner);
        List<GoodsBean> recommendGoodsList = new ArrayList<>();
        for(int i = 0 ;i<6;i++){
            GoodsBean bean = new GoodsBean(i,"茶叶"+i,"image"+i,"desc"+i,i*8.5);
            bean.setResId(R.mipmap.shop_goods_banner);
            recommendGoodsList.add(bean);
        }
        recommendBean.setGoodsList(recommendGoodsList);
        shopHomeBean.setRecommendBean(recommendBean);
        //猜你喜欢
        addData();
        shopHomeBean.setGoodsList(likeList);


        addHeadView();
        initRefreshLayout();
    }

    /**
     * 模仿网络请求 更多数据
     */
    private int id;
    private int totalPage = 5000;
    private int curPage = 0;
    private void addData(){
        for (int i = 0; i < 20; i++) {
            GoodsBean bean = new GoodsBean(id++, "商品" + id, "", "描述" + id, id * 8);
            bean.setResId(R.mipmap.shop_goods_banner);
            likeList.add(bean);
        }
        curPage++;
        LogUtil.e("loadmore","loadmore--------------------------------" + id);
    }


    /**
     * 添加头部
     */
    private void addHeadView() {
        View headrView = LayoutInflater.from(this).inflate(R.layout.shop_view_shop_home_header, null);
        viewPager = headrView.findViewById(R.id.viewPager);
        classifyRv = headrView.findViewById(R.id.classifyRv);
        recommendIv = headrView.findViewById(R.id.recommendIv);
        recommendRv = headrView.findViewById(R.id.recommendRv);
        headrView.findViewById(R.id.npTv).setOnClickListener(this);
        headrView.findViewById(R.id.ndTv).setOnClickListener(this);
        headrView.findViewById(R.id.npAndndTv).setOnClickListener(this);
        headrView.findViewById(R.id.moreTv).setOnClickListener(this);
        headrView.findViewById(R.id.recommendIv).setOnClickListener(this);


        adapter.addHeaderView(headrView);
        //广告栏
        viewPager.setAnimationDurtion(500);
        homeBannerAdapter = new HomeBannerAdapter(this);
        viewPager.setAdapter(homeBannerAdapter);
        viewPager.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#40ffffff")));
        //分类
        classifyRv.setHasFixedSize(true);
        classifyRv.setLayoutManager(new FullyGridLayoutManager(this,3));
        classifyAdapter = new HomeClassifyAdapter(classifyList);
        classifyRv.setAdapter(classifyAdapter);
        classifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("点击跳转，点击位置" + position);
            }
        });
        //商品推荐
        recommendIv.setBackgroundResource(recommendBean.getResId());
        recommendRv.setHasFixedSize(true);
        recommendRv.setLayoutManager(new FullyGridLayoutManager(this,2));
        reCommendAdapter = new GoodsAdapter(recommendBean.getGoodsList());
        recommendRv.setAdapter(reCommendAdapter);
        reCommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("点击跳转，点击位置" + position);
                //startActivity(GoodsDetailActivity.class);
            }
        });
    }



    private void initRefreshLayout() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                addData();
                adapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(true);  //加载完成
                if(curPage >= totalPage){ //到达总页数
                    refreshlayout.setLoadmoreFinished(true);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.npTv){

        }else if(i == R.id.ndTv){

        }else if(i == R.id.npAndndTv){

        }else if(i == R.id.moreTv){

        }else if(i == R.id.recommendIv){

        }
    }
}
