package com.dengzh.shop.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengzh.shop.R;
import com.dengzh.shop.R2;
import com.dengzh.shop.bean.GoodsInfo;
import com.dengzh.shop.bean.StoreInfo;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/9/26 0026.
 */

public class ShopcartAdapter extends BaseExpandableListAdapter{

    private boolean isEdit;
    private List<StoreInfo> groups;
    private Map<String, List<GoodsInfo>> children;
    private Context context;
    private CheckInterface checkInterface;  //选择监听
    private ModifyCountInterface modifyCountInterface;  //数量监听

    /**
     * 设置编辑状态
     * @param edit
     */
    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    /**
     * 构造函数
     *
     * @param groups   组元素列表
     * @param children 子元素列表
     * @param context
     */
    public ShopcartAdapter(List<StoreInfo> groups, Map<String, List<GoodsInfo>> children, Context context) {
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    /**
     * 选择监听
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 数量监听
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsInfo> childs = children.get(groups.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shop_item_shopcart_group, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        }else {
            gholder = (GroupViewHolder) convertView.getTag();
        }

        //组元素设置
        final StoreInfo group = (StoreInfo) getGroup(groupPosition);
        gholder.tvSourceName.setText(group.getName());
        gholder.determineChekbox.setChecked(group.isChoosed());
        gholder.determineChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                //全组选中或者全组取消
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shop_item_shopcart_product, null);
            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        }else{
            cholder = (ChildViewHolder) convertView.getTag();
        }

        //编辑状态，显示数量框
        if(isEdit){
            cholder.llEdtor.setVisibility(View.VISIBLE);
            cholder.rlNoEdtor.setVisibility(View.GONE);
        }else{
            cholder.llEdtor.setVisibility(View.GONE);
            cholder.rlNoEdtor.setVisibility(View.VISIBLE);
        }
        //当前商品
        final GoodsInfo goodsInfo = (GoodsInfo) getChild(groupPosition, childPosition);
        //是否显示分割线
        if(isLastChild&&goodsInfo!=null) {
            cholder.stub.setVisibility(View.VISIBLE);
        }else{
            cholder.stub.setVisibility(View.GONE);
        }
        //商品信息
        if (goodsInfo != null) {
            cholder.tvIntro.setText(goodsInfo.getDesc());
            cholder.tvPrice.setText("￥" + goodsInfo.getPrice() + "");
            cholder.etNum.setText(goodsInfo.getCount() + "");
            cholder.ivAdapterListPic.setImageResource(goodsInfo.getGoodsImg());
            cholder.tvColorsize.setText("颜色：" + goodsInfo.getColor() + "," + "尺码：" + goodsInfo.getSize() + "瓶/斤");
            cholder.tvBuyNum.setText("x" + goodsInfo.getCount());
            cholder.checkBox.setChecked(goodsInfo.isChoosed());
            cholder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsInfo.setChoosed(((CheckBox) v).isChecked());
                    cholder.checkBox.setChecked(((CheckBox) v).isChecked());
                    //子选中或取消
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });
            cholder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露增加接口
                }
            });
            cholder.btReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露删减接口
                }
            });
            /********************方案一：弹出软键盘修改数量，应为又不知名的bug会使然键盘强行关闭***********************/
            /****在清单文件的activity下设置键盘：
             android:windowSoftInputMode="adjustUnspecified|stateHidden|adjustPan"
             android:configChanges="orientation|keyboardHidden"****/
            cholder.etNum.addTextChangedListener(new GoodsNumWatcher(goodsInfo));//监听文本输入框的文字变化，并且刷新数据

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }



    /**
     * 组元素绑定器
     */
    static class GroupViewHolder {
        @BindView(R2.id.determine_chekbox)
        CheckBox determineChekbox;
        @BindView(R2.id.tv_source_name)
        TextView tvSourceName;
        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 子元素绑定器
     */
    static class ChildViewHolder {
        @BindView(R2.id.check_box)
        CheckBox checkBox;
        @BindView(R2.id.iv_adapter_list_pic)
        ImageView ivAdapterListPic;
        @BindView(R2.id.tv_intro)
        TextView tvIntro;
        @BindView(R2.id.tv_color_size)
        TextView tvColorSize;
        @BindView(R2.id.tv_price)
        TextView tvPrice;
        @BindView(R2.id.tv_discount_price)
        TextView tvDiscountPrice;
        @BindView(R2.id.tv_buy_num)
        TextView tvBuyNum;
        @BindView(R2.id.rl_no_edtor)
        RelativeLayout rlNoEdtor;
        @BindView(R2.id.bt_reduce)
        Button btReduce;
        @BindView(R2.id.et_num)
        EditText etNum;
        @BindView(R2.id.bt_add)
        Button btAdd;
        @BindView(R2.id.ll_change_num)
        RelativeLayout llChangeNum;
        @BindView(R2.id.tv_colorsize)
        TextView tvColorsize;
        @BindView(R2.id.tv_goods_delete)
        TextView tvGoodsDelete;
        @BindView(R2.id.ll_edtor)
        LinearLayout llEdtor;
        @BindView(R2.id.stub)
        View stub;
        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

    }

    /**
     * 购物车的数量修改编辑框的内容监听
     */
    class GoodsNumWatcher implements TextWatcher {
        GoodsInfo   goodsInfo;
        public GoodsNumWatcher(GoodsInfo goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!TextUtils.isEmpty(s.toString())){//当输入的数字不为空时，更新数字
                goodsInfo.setCount(Integer.valueOf(s.toString().trim()));
            }
        }

    }

}
