package com.dengzh.sample.module.customView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/20.
 */

public class BannerActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.dotLl)
    LinearLayout dotLl;
    @BindView(R.id.bannerRl)
    RelativeLayout bannerRl;

    private List<ImageView> ivList = new ArrayList<>();
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_banner;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

        //显示图片
        for(int i = 0;i<2;i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(R.mipmap.ic_launcher);
            ivList.add(imageView);
        }
        initDotViews();
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //改变dot状态

                dots.get(oldPosition).setBackgroundResource(R.drawable.shape_dot_grey);
                dots.get(position).setBackgroundResource(R.drawable.shape_dot_white);

                oldPosition = currentItem;
                currentItem = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void initData() {

    }

    private class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return ivList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(ivList.get(position));
            return ivList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(ivList.get(position));
        }
    }

    private void initDotViews(){
        //显示小点
        dots = new ArrayList<View>();
        for(int i = 0;i<ivList.size();i++){
            ImageView dotView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = 100;
            params.height = 100;
            dotView.setLayoutParams(params);
            dotView.setImageResource(R.drawable.shape_dot_grey);
            dots.add(dotView);
            dotLl.addView(dots.get(i));
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //轮播
                   // viewPager.setCurrentItem((currentItem+1)%2);
                    handler.sendEmptyMessageDelayed(0,3000);
                    break;
                case 1:

                    //停止
                    break;
            }
        }
    };

}
