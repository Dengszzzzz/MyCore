package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.bean.ClazzBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.customView.CustomViewActivity;
import com.dengzh.sample.module.dialog.DialogAndPopActivity;
import com.dengzh.sample.module.umeng.UmengActivity;
import com.dengzh.shop.module.home.HomeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/12.
 * 1.  5.0新控件  recyclerView、CardView
 * 2.  6.0新控件  FloatingActionButton，TextInputLayout，Snackbar，TabLayout,NavigationView,CoordinatorLayout,AppBarLayout,CollapsingToolbarLayout
 *
 * 本包主要集中6.0控件试用
 */

public class MaterDesignActivity extends BaseActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ItemAdapter adapter;
    private List<ClazzBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("MaterDesign");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        mList.add(new ClazzBean("toolBar",ToolBarTestActivity.class));
        mList.add(new ClazzBean("AppBarLayout和CoordinatorLayout",AppBarLayoutActivity.class));
        mList.add(new ClazzBean("TabLayout",TabLayouActivity.class));
        mList.add(new ClazzBean("NavigationView",NavigationViewActivity.class));
        mList.add(new ClazzBean("Collapsing",CollapsingToolbarLayoutActivity.class));
        mList.add(new ClazzBean("Constraint",ConstraintActivity.class));
        mList.add(new ClazzBean("FlexboxLayout",FlexboxActivity.class));
        mList.add(new ClazzBean("WebView",WebViewActivity.class));

        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(mList.get(position).getClazz());
            }
        });
    }
}
