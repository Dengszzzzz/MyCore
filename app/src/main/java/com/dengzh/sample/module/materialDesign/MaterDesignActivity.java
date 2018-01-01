package com.dengzh.sample.module.materialDesign;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.utils.ToastUtil;

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
        String[] strs = {"toolBar","AppBarLayout和CoordinatorLayout","TabLayout","NavigationView","Collapsing","Constraint","FlexboxLayout"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(ToolBarTestActivity.class);
                        break;
                    case 1:
                        startActivity(AppBarLayoutActivity.class);
                        break;
                    case 2:
                        startActivity(TabLayouActivity.class);
                        break;
                    case 3:
                        startActivity(NavigationViewActivity.class);
                        break;
                    case 4:
                        startActivity(CollapsingToolbarLayoutActivity.class);
                        break;
                    case 5:
                        startActivity(ConstraintActivity.class);
                        break;
                    case 6:
                        startActivity(FlexboxActivity.class);
                        break;
                }
            }
        });
    }
}
