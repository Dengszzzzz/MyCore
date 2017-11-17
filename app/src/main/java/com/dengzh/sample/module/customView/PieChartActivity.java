package com.dengzh.sample.module.customView;

import android.os.Bundle;

import com.dengzh.sample.R;
import com.dengzh.sample.bean.PieDataBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.study.PieChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/10/13 0013.
 * 饼状图
 */

public class PieChartActivity extends BaseActivity {

    private List<PieDataBean> pieDataList;
    @BindView(R.id.pieChartView)
    PieChartView pieChartView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_pie_chart;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        pieDataList = new ArrayList<>();
        pieDataList.add(new PieDataBean("1",10));
        pieDataList.add(new PieDataBean("2",10));
        pieDataList.add(new PieDataBean("3",10));
        pieDataList.add(new PieDataBean("4",10));
        pieDataList.add(new PieDataBean("5",10));
        pieChartView.setData(pieDataList);
    }

}
