package com.dengzh.sample.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.LocationBean;
import com.dengzh.sample.utils.glideUtils.GlideUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by dengzh on 2018/1/18.
 * 九宫格图
 */

public class NineTableView extends LinearLayout {

    private View view;
    private Context mContext;

    private LinearLayout lineLl1, lineLl2, lineLl3;
    private ImageView iv11, iv12, iv13;
    private ImageView iv21, iv22, iv23;
    private ImageView iv31, iv32, iv33;

    private ImageView[] imageViews;

    private List<String> mList = new ArrayList<>();

    public NineTableView(Context context) {
        this(context, null);
    }

    public NineTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_nine_table, this);
        lineLl1 = view.findViewById(R.id.lineLl1);
        lineLl2 = view.findViewById(R.id.lineLl2);
        lineLl3 = view.findViewById(R.id.lineLl3);

        imageViews = new ImageView[9];
        imageViews[0] = view.findViewById(R.id.iv11);
        imageViews[1] = view.findViewById(R.id.iv12);
        imageViews[2] = view.findViewById(R.id.iv13);
        imageViews[3] = view.findViewById(R.id.iv21);
        imageViews[4] = view.findViewById(R.id.iv22);
        imageViews[5] = view.findViewById(R.id.iv23);
        imageViews[6] = view.findViewById(R.id.iv31);
        imageViews[7] = view.findViewById(R.id.iv32);
        imageViews[8] = view.findViewById(R.id.iv33);

        /*iv11 = view.findViewById(R.id.iv11);
        iv12 = view.findViewById(R.id.iv12);
        iv13 = view.findViewById(R.id.iv13);
        iv21 = view.findViewById(R.id.iv21);
        iv22 = view.findViewById(R.id.iv22);
        iv23 = view.findViewById(R.id.iv23);
        iv31 = view.findViewById(R.id.iv31);
        iv32 = view.findViewById(R.id.iv32);
        iv33 = view.findViewById(R.id.iv33);*/

    }

    /**
     * 设置数据
     *
     * @param mDatas
     */
    public void setData(List<String> mDatas) {
        mList.clear();
        mList.addAll(mDatas);
        if (mList.size() > 0) {  //显示第一行
            lineLl1.setVisibility(VISIBLE);
            if (mList.size() > 3) { //显示第二行
                lineLl2.setVisibility(VISIBLE);
                if (mList.size() > 6) {  //显示第三行
                    lineLl3.setVisibility(VISIBLE);
                }
            }
            for (int i = 0; i < mList.size() && i < 9; i++) {
                GlideUtils.loadImg(mContext, imageViews[i], mList.get(i));
                final int finalI = i;
                imageViews[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener!=null){
                            listener.onIvClick(finalI);
                        }
                    }
                });
            }
        }
    }

    /**
     * 获得imageview的定位数据
     *
     * @return
     */
    public List getLoactionList() {
        List<LocationBean> mLocationList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            int location[] = new int[2];
            ((ImageView)imageViews[i]).getLocationOnScreen(location);
            LocationBean bean = new LocationBean(location[0], location[1], imageViews[i].getHeight(), imageViews[i].getWidth());
            mLocationList.add(bean);
        }
        return mLocationList;
    }

    //imageview点击事件
    private OnItemChildClickListener listener;
    public interface OnItemChildClickListener {
        void onIvClick(int position);
    }
    public void setOnItemChildClick(OnItemChildClickListener listener){
        this.listener = listener;
    }

}
