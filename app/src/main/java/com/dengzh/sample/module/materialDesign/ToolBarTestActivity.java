package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;

/**
 * Created by dengzh on 2017/12/11.
 * toollbar使用
 * 1.在style文件设置样式，要NotActionBar的
 * 2.在v21-style可以设置底部导航栏颜色
 * 3.可以setSupportActionBar(mToolBar),也可以直接用toolBar做操作
 */

public class ToolBarTestActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        //对toolBar做修改
        toolbar.setNavigationIcon(R.mipmap.ic_return_white); //设置返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //返回点击事件
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("点击返回");
                finish();
            }
        });
        toolbar.setLogo(R.mipmap.ic_launcher); //设置logo
        toolbar.setTitle("toolBar");     //主标题，靠左 如果要居中，可在ToolBar布局下定义一个居中的textView
        toolbar.setSubtitle("二级标题");  //二级标题
        toolbar.inflateMenu(R.menu.menu_toolbar); //menu菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        ToastUtil.showToast("编辑");
                        break;
                    case R.id.action_share:
                        ToastUtil.showToast("分享");
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected void initData() {

    }


}
