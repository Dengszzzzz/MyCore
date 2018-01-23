package com.dengzh.sample.module.gitHubView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.GalleryAdapter;
import com.dengzh.sample.adapter.LocationBean;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.view.NineTableView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/1/17.
 * 高仿微信可拖拽返回PhotoView
 * 原作者：
 * https://github.com/githubwing/DragPhotoView
 */

public class DragPhotoViewActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nineTableView)
    NineTableView nineTableView;

    private List<String> mList = new ArrayList<>();
    private List<LocationBean> mLocationList = new ArrayList<>();
    private GalleryAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_drag_photo2;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initData() {
        //模拟数据
        mList.add("http://pic35.photophoto.cn/20150520/0022005541514545_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150816/0022005578085488_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150815/0022005572291543_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150815/0022005538663320_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150815/0022005532549182_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150814/0022005575243818_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150816/0022005584797295_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150816/0022005510125012_b.jpg");
        mList.add("http://pic36.photophoto.cn/20150815/0022005527753374_b.jpg");

        initView1();
        initView2();
    }

    private void initView1(){
        //1.recyclerView
        adapter = new GalleryAdapter(mList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv) {
                    startPhotoActivity1(DragPhotoViewActivity.this, (ImageView) view, position);
                }
            }

        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    public void startPhotoActivity1(Context context, ImageView imageView, int position) {
        Intent intent = new Intent(context, DragPhotoActivity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());

        intent.putStringArrayListExtra("datas", (ArrayList<String>) mList);
        intent.putExtra("position", position);  //当前列表位置
        context.startActivity(intent);
        overridePendingTransition(0, 0);  //进入动画，退出动画
    }


    private void initView2(){
        //2.NineTableView，先设置数据，再获取定位集合
        nineTableView.setData(mList);
        nineTableView.setOnItemChildClick(new NineTableView.OnItemChildClickListener() {
            @Override
            public void onIvClick(int position) {
                startPhotoActivity2(DragPhotoViewActivity.this,position);
            }
        });

    }

    public void startPhotoActivity2(Context context, int position) {
        Intent intent = new Intent(context, DragPhotoActivity2.class);

        mLocationList.clear();
        mLocationList.addAll(nineTableView.getLoactionList());

        intent.putExtra("locationList", (Serializable) mLocationList);  //定位列表
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("locationList", (Serializable) mLocationList);  */
        intent.putStringArrayListExtra("datas", (ArrayList<String>) mList);  //数据列表
        intent.putExtra("position", position);  //当前列表位置
        context.startActivity(intent);
        overridePendingTransition(0, 0);  //进入动画，退出动画
    }
}
