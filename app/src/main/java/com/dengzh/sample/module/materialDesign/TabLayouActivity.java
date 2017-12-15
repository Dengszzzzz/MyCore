package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2017/12/14.
 * TabLayout+ViewPager示例
 *
 1,addTab(TabLayout.Tab tab, int position, boolean setSelected) 增加选项卡到 layout中,这是一个重载方法.第一个参数是Tab对象,通过Tablayout.newTab()创建;第二个参数是插入Tab的位置;最后一个参数是当前Tab是否为选中状态.
 2,newTab() 新建个 tab.
 3,setTabMode(),设置 Mode,有两种值：TabLayout.MODE_SCROLLABLE和TabLayout.MODE_FIXED分别表示当tab的内容超过屏幕宽度是否支持横向水平滑动，第一种支持滑动，第二种不支持，默认不支持水平滑动.如果你添加的Tab很少的话,你应该把Tab的mode设置成MODE_FIXED,否则可能会出现Tab标签不会占满屏幕的宽度.
 4,setOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) 为每个 tab 增加选择监听器.
 5,setScrollPosition(int position, float positionOffset, boolean updateSelectedText) 设置Tab滚动到的位置.
 6,setTabGravity(int gravity) 设置 Gravity.
 7,setTabTextColors(ColorStateList textColor) 设置 tab 中文本的颜色.
 8,setTabTextColors(int normalColor, int selectedColor) 设置 tab 中文本的颜色 默认 选中的颜色.
 9,setTabsFromPagerAdapter(PagerAdapter adapter) 将TabLayout与ViewPager关联,当PageAdapter更新时,TabLayout会自动更新.(注意:该方法已过时)
 10,setupWithViewPager(ViewPager viewPager) 设置Tablayout和ViewPager实现联动效果.
 11,getTabAt(int index) 得到选项卡.
 12,getTabCount() 得到选项卡的总个数.
 13,getTabGravity() 得到 tab 的 Gravity.
 14,getTabMode() 得到 tab 的模式.
 15,getTabTextColors() 得到 tab 中文本的颜色.
 */

public class TabLayouActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> mTabList;
    private List<Fragment> mTabFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_tab_layout;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("TabLayout");
    }

    @Override
    protected void initData() {

        mTabList = initTabList();
        initTabLayout(mTabList);
        mTabFragments = initFragments(mTabList);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTabFragments.size();
            }

            //注意:需要重写此方法,从标签集合中获取到title,否则标签上的title则不会显示.
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabList.get(position);
            }
        });

        //将TabLayout与ViewPager关联起来(注意:该行代码需在ViewPager设置Adapter之后调用)
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化Tab标签数据
     *
     * @return
     */
    private List<String> initTabList() {
        List<String> tabList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tabList.add("TAB " + i);
        }
        return tabList;
    }


    /**
     * 初始化TabLayout
     *
     * @param list
     */
    private void initTabLayout(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i == 0)
                tabLayout.addTab(tabLayout.newTab().setText(list.get(i)), true);
            else
                tabLayout.addTab(tabLayout.newTab().setText(list.get(i)));
        }
        //设置TabLayout的模式为可滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**
     * 初始化Tab标签对应的Fragment
     *
     * @param list 标签集合数据
     * @return
     */
    public List<Fragment> initFragments(List<String> list) {
        List<Fragment> mTabFragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Fragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Content", list.get(i));
            tabFragment.setArguments(bundle);
            mTabFragments.add(tabFragment);
        }
        return mTabFragments;
    }

}
