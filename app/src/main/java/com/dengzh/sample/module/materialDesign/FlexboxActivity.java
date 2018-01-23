package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.bean.Book;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.dengzh.sample.view.flexboxlayout.TagAdapter;
import com.dengzh.sample.view.flexboxlayout.TagFlowLayout;
import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/1/1.
 */

public class FlexboxActivity extends BaseActivity {

    private String TAG = "FlexboxActivity";

    @BindView(R.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;


    LayoutInflater mInflater;
    @BindView(R.id.flexboxLayout)
    FlexboxLayout flexboxLayout;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected int getLayoutId() {
        return R.layout.ac_flex_box;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("FlexboxLayout");
        initTagFlowLayoutView();
        initFlexboxLayoutView();
    }

    @Override
    protected void initData() {

    }

    /**
     * 封装试用
     */
    private void initTagFlowLayoutView() {
        mInflater = LayoutInflater.from(this);
        tagFlowLayout.setCheckable(false);
        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            protected View getView(ViewGroup parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_view_text, parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            protected void onSelect(ViewGroup parent, View view, int position) {
                view.setBackgroundResource(R.drawable.shape_spec_select);
                ((TextView) view).setTextColor(ContextCompat.getColor(FlexboxActivity.this, R.color.cyan));
            }

            @Override
            protected void onUnSelect(ViewGroup parent, View view, int position) {
                view.setBackgroundResource(R.drawable.shape_spec_normal);
                ((TextView) view).setTextColor(ContextCompat.getColor(FlexboxActivity.this, R.color.springgreen));
            }

        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(ViewGroup parent, View view, int position) {
                ToastUtil.showToast("点击了" + mVals[position]);
            }
        });
    }

    /**
     * 原生试用
     */
    private void initFlexboxLayoutView() {
        String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};
        for (int i = 0; i < tags.length; i++) {
            Book model = new Book();
            model.setId(i);
            model.setName(tags[i]);
            flexboxLayout.addView(createNewFlexItemTextView(model));
        }
    }

    /**
     * 动态创建TextView
     *
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final Book book) {

        TextView textView = (TextView) mInflater.inflate(R.layout.item_view_text2, flexboxLayout, false);
        textView.setText(book.getName());
        textView.setTag(book.getId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (book.isSelect()) { //已选->未选
                    book.setSelect(false);
                    view.setSelected(false);
                } else { //未选 -> 已选
                    book.setSelect(true);
                    view.setSelected(true);
                }
                ToastUtil.showToast(book.getName());
            }
        });
        /*int padding = Util.dip2px(this, 4);
        int paddingLeftAndRight = Util.dip2px(this, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = Util.dip2px(this, 6);
        int marginTop = Util.dip2px(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);*/
        return textView;
    }

    @OnClick(R.id.allSelectBt1)
    public void onViewClicked() {
        StringBuilder builder = new StringBuilder();
        for(int position:tagFlowLayout.getSelectedItems()){
            builder.append(mVals[position]+"&");
        }
        String result = builder.toString();
        if(builder.toString().contains("&")){  //删除最后一个&符号
            result = result.substring(0,result.length()-1);
        }
        ToastUtil.showToast(result);
    }
}
