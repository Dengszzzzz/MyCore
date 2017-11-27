package com.dengzh.sample.module.customView;

import android.os.Bundle;

import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.example.levelviewlibray.LevelView;
import com.example.levelviewlibray.LevelViewBaseData;
import com.example.levelviewlibray.LevelViewBaseView;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/11/23.
 * 晋级图
 */

public class LevelViewActivity extends BaseActivity {


    @BindView(R.id.level_view)
    LevelView levelView;

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_level_view;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        levelView.setLevelViewListener(new LevelView.LevelViewListener() {
            @Override
            public void onError(String error) {
                LogUtil.e("LevelViewActivity","on error is "+error);
            }

            @Override
            public void onItemClik(LevelViewBaseView childView, int i) {
                ToastUtil.showToast("点击了第{"+ i +"}项,name is "+childView.getName() +
                        "count is " + childView.getCount());
            }

            @Override
            public void onItemLongClik(LevelViewBaseView childView, int i) {
                ToastUtil.showToast("长按了第{"+i+"}项,name is "+childView.getName() +
                        "count is " + childView.getCount() +
                        "time is " + childView.getData().getIcon()+
                        "other is "+ childView.getData().getList());
            }
        });

        switch (type){
            case 0:  //空视图
                levelView.addChildView(3,true);
                break;
            case 1:
                showPartView();
                break;
        }

    }

    private void showPartView(){
        List<LevelViewBaseData> list = new ArrayList<>();

    }

    @Override
    protected void initData() {

    }


}
