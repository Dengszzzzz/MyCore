package com.dengzh.shop.module.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengzh.shop.R;
import com.dengzh.shop.R2;
import com.dengzh.shop.adapter.ShopcartAdapter;
import com.dengzh.shop.bean.GoodsInfo;
import com.dengzh.shop.bean.StoreInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/9/26 0026.
 */

public class ShoppingCartActivity extends Activity implements ShopcartAdapter.CheckInterface,
        ShopcartAdapter.ModifyCountInterface{

    private ShopcartAdapter selva;
    private List<StoreInfo> groups = new ArrayList<StoreInfo>();// 组元素数据列表
    private Map<String, List<GoodsInfo>> children = new HashMap<String, List<GoodsInfo>>();// 子元素数据列表


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.title)
    TextView title;
    @BindView(R2.id.subtitle)
    TextView subtitle;
    @BindView(R2.id.top_bar)
    LinearLayout topBar;
    @BindView(R2.id.exListView)
    ExpandableListView exListView;
    @BindView(R2.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R2.id.all_chekbox)
    CheckBox allChekbox;
    @BindView(R2.id.tv_delete)
    TextView tvDelete;
    @BindView(R2.id.tv_go_to_pay)
    TextView tvGoToPay;

    @BindView(R2.id.ll_shar)
    LinearLayout llShar;
    @BindView(R2.id.ll_info)
    LinearLayout llInfo;

    @BindView(R2.id.tv_share)
    TextView tvShare;
    @BindView(R2.id.tv_save)
    TextView tvSave;
    @BindView(R2.id.ll_cart)
    LinearLayout llCart;
    @BindView(R2.id.layout_cart_empty)
    LinearLayout cart_empty;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_ac_shopping_cart);
        ButterKnife.bind(this);
        initDatas();
        initUI();
    }


    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void initDatas(){
        for (int i = 0; i < 3; i++) {
            groups.add(new StoreInfo(i + "", "天猫店铺" + (i + 1) + "号店"));
            List<GoodsInfo> products = new ArrayList<GoodsInfo>();
            for (int j = 0; j <= i; j++) {
                int[] img = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher};
                products.add(new GoodsInfo(j + "", "商品", groups.get(i)
                        .getName() + "的第" + (j + 1) + "个商品", 12.00 + new Random().nextInt(23), new Random().nextInt(5) + 1, "豪华", "1", img[i * j], 6.00 + new Random().nextInt(13)));
            }
            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }
    }


    private void initUI(){
        //ExpandableListView
        selva = new ShopcartAdapter(groups, children, this);
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        exListView.setAdapter(selva);
        for (int i = 0; i < selva.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }

    }


    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {

    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }
}
