package com.dengzh.shop.module.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.dengzh.shop.R;
import com.dengzh.shop.R2;
import com.dengzh.shop.module.BaseActivity;
import com.dengzh.shop.module.me.MeFragment;
import com.dengzh.shop.module.shoppingCart.ShoppingCartFragment;
import com.dengzh.shop.view.FragmentTabHost;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/10/18 0018.
 * 首页，包含4个fragment
 */

public class HomeActivity extends BaseActivity {
    @BindView(R2.id.contentPanel)
    FrameLayout contentPanel;
    @BindView(R2.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R2.id.tabhost)
    FragmentTabHost tabhost;

    //配置显示信息
    private final Class fragmentArray[] = {ShopHomeFragment.class,ClassifyFragment.class
            ,ShoppingCartFragment.class, MeFragment.class};
    private int mImageViewArray[] = {R.drawable.shop_tab_home, R.drawable.shop_tab_classify
            , R.drawable.shop_tab_cart,R.drawable.shop_tab_me};
    private int mTextviewArray[] = {R.string.str_home,R.string.str_classify
            ,R.string.str_shopping_cart,R.string.str_me};


    //定义一个布局
    private LayoutInflater layoutInflater;


    @Override
    protected int getLayoutId() {
        return R.layout.shop_ac_home;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //TabHost配置
        tabhost.setup(this,getSupportFragmentManager(),R.id.contentPanel);
        int count = fragmentArray.length;
        for(int i =0;i<count;i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec  tabSpec = tabhost.newTabSpec(getString(mTextviewArray[i])).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragmentArray[i], null);
        }
    }

    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.shop_item_home_tab, null);
        ImageView tabIv = view.findViewById(R.id.tabIv);
        tabIv.setImageResource(mImageViewArray[index]);

        TextView textView = view.findViewById(R.id.tabTv);
        textView.setText(getString(mTextviewArray[index]));

        return view;
    }

    @Override
    protected void initData() {

    }

}
