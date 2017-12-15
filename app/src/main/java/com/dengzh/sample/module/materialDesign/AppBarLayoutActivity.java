package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/12/12.
 * 1.AppBarLayout 和 CoordinatorLayout 配合使用
 * 2.CoordinatorLayout 可以定义一个depencyView，让其他子View随着它的变化而变化
 * 3.AppBarLayout 配合 NestedScrollView 可以实现各种app bar跟着滑动的效果
 * 4.FloatingActionButton
 * 5.Snackbar用于展示一条简短的消息给用户
 */

public class AppBarLayoutActivity extends BaseActivity {

    @BindView(R.id.mToolBar)
    Toolbar mToolBar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.factionBt)
    FloatingActionButton factionBt;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.userNameTil)
    TextInputLayout userNameTil;
    @BindView(R.id.pwdNameTil)
    TextInputLayout pwdNameTil;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.ac_app_bar;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setVisibility(View.GONE);
        mToolBar.setTitle("AppBarLayout");
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     //   webView.setVisibility(View.VISIBLE);
      //  webView.loadUrl("http://www.jianshu.com/p/d159f0176576");

        //testFloatingActionButton();
        //testTextInputLayout();
        testTabLayout();
    }

    /**
     * FloatingActionButton 和 Snackbar测试
     */
    private void testFloatingActionButton() {
        factionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到Snackbar对象
                final Snackbar snackbar = Snackbar.make(coordinatorLayout, "我是Snackbar...", Snackbar.LENGTH_LONG);//设置Snackbar背景
                snackbar.getView().setBackgroundResource(R.color.colorPrimary);
                snackbar.show();//显示带Action的Snackbar
                snackbar.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭Snackbar
                        snackbar.dismiss();
                    }
                });
            }
        });
        factionBt.setVisibility(View.VISIBLE);
    }

    /**
     * TextInputLayout 测试
     */
    private void testTextInputLayout() {
        userNameTil.setVisibility(View.VISIBLE);
        pwdNameTil.setVisibility(View.VISIBLE);
        //1.得到EditText对象
        EditText userEt = userNameTil.getEditText();
        EditText pwdEt = pwdNameTil.getEditText();

        //EditText添加文本变化监听
        userEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.d(TAG, "beforeTextChanged执行了....s = " + s + "---start = " + start + "---count = " + count + "---after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.d(TAG, "onTextChanged执行了....s = " + s + "---start = " + start + "---count = " + count + "---before = " + before);
                if (s.length() > 7) {
                    userNameTil.setErrorEnabled(true);//设置是否打开错误提示
                    userNameTil.setError("用户名长度不能超过8个");//设置错误提示的信息
                } else {
                    userNameTil.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.d(TAG, "afterTextChanged执行了....s = " + s);
            }
        });

        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    pwdNameTil.setErrorEnabled(true);
                    pwdNameTil.setError("密码长度不能小于6个");
                } else {
                    pwdNameTil.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * TabLayout 测试
     * 一般 TabLayout+ViewPager
     */
    private void testTabLayout() {
        //添加8个Tab标签，并设置第一个Tab标签为选中状态.
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
        //设置Tab的模式为可滑动，当tab标签超过屏幕宽度时，可以滑动.
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initData() {

    }

}
