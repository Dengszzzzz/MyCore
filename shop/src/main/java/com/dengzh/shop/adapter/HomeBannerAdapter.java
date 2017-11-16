package com.dengzh.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dengzh.shop.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by dengzh on 2017/9/29.
 * 首页广告条适配器
 */
public class HomeBannerAdapter extends StaticPagerAdapter {
    private Context context;
	public HomeBannerAdapter(Context context){
		this.context = context;
	}
	@Override
	/**
	 * 获得页面的总数
	 */
	public int getCount() {
        return 2;
	}


	@Override
	public View getView(ViewGroup container, int position) {
		ImageView view = new ImageView(container.getContext());
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		view.setImageResource(R.mipmap.shop_goods_banner);
		return view;
	}


}
