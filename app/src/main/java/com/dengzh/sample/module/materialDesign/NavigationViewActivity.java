package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.StatusBarUtil;
import com.dengzh.sample.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/12/14.
 */

public class NavigationViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_navigation_view;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        //设置状态栏为全透明。
        toolbar.setVisibility(View.GONE);
        mToolbar.setTitle("NavigationView");
        //设置DrawerLayout
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
        };
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        //获取头布局内部的控件
        View header = navigationView.getHeaderView(0);
        TextView userName =  header.findViewById(R.id.tv_username);
        TextView email =  header.findViewById(R.id.tv_email);
        userName.setText("Zhihui Deng");
        email.setText("dengzhui00@gmail.com");


        //处理菜单列表种图标不显示原始颜色的问题.  当你设置完图标后,会发现无论图标的原始颜色是什么,都会全部变成灰色.
        // 此时,你可以通过app:itemIconTint="@color/..."属性为图标设置统一的颜色.当然,如果你需要图标显示自己原始的颜色,
        // 可以调用NavigationView的setItemIconTintList(ColorStateList tint)方法,参数传为null即可.
        // navigationView.setItemIconTintList(null);

        //设置菜单列表的点击事件。
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        ToastUtil.showToast("Home");
                        drawerLayout.closeDrawers();  //点击条目后，关闭侧滑才菜单
                        item.setChecked(true);        //设置点击过的item为选中状态，字体和图片的颜色会发生变化，颜色会变为和Toolbar的颜色一致。
                        mToolbar.setTitle("home");    //修改Toolbar显示的Title
                        break;
                    case R.id.drawer_classify:
                        ToastUtil.showToast("classify");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        mToolbar.setTitle("classify");
                        break;
                    case R.id.drawer_cart:
                        ToastUtil.showToast("cart");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        mToolbar.setTitle("cart");
                        break;
                    case R.id.drawer_me:
                        ToastUtil.showToast("me");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        mToolbar.setTitle("me");
                        break;
                    case R.id.drawer_shop:
                        ToastUtil.showToast("shop");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        mToolbar.setTitle("shop");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        //重写返回键，当侧滑菜单处于打开状态，点击返回键就关闭侧滑菜单。
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}
