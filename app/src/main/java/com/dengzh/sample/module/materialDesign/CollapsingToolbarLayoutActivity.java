package com.dengzh.sample.module.materialDesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity2;
import com.dengzh.sample.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/12/14.
 */

public class CollapsingToolbarLayoutActivity extends BaseActivity2 {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_collapsing_toolbar_layout)
    CoordinatorLayout activityCollapsingToolbarLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_collapsing;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

        StatusBarUtil.setTransparent(this);
        toolbar.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);     //设置展开时Title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);    //设置收缩时Title的颜色

        tvContent.setText("在如今日新月异的今天，高科技不断涌现，信息量爆炸的时代，我们庆幸生活在这样一个时代，同样我们不得不接受科技给我们带来的一系列改变。在生活中，工作中有着一系列的产品，它们有着自己的使命，每一种计算机语言产生都是为了更好的解决人类发展中的历程。就像人类一样有出生，孩童，少年，青年，壮年，老年等不同的时期。今天我们来聊一下Android（安卓）的出生与发展。\n" +
                "   Android 的出生：Android系统一开始并不是由谷歌研发出来了，Android系统原来的公司名字就叫做Android。AndyRubin创立了这个操作系统公司Android。Android 4千万美元卖给Google。谷歌公司在2005收购了这个仅成立22月的高科技企业Android。Android系统也开始由谷歌接手研发，Android系统的负责人以及Android公司的CEO安迪·鲁宾成为谷歌公司的工程部副总裁，继续负责Android项目的研发工作。\n" +
                "   在2007年11月5日这天，谷歌公司正式向外界展示了这款名为Android的操作系统，并且在这天谷歌宣布建立一个全球性的联盟组织，该组织由34家 手机制造商、软件开发商、电信运营商以及芯片制造商共同组成。这一联盟将支持谷歌发布的手机操作系统以及应用软件，将共同开发Android系统的开放源代码。\n" +
                "创始成员\n" +
                "埃森德（Ascender）、Audience Corp（听众）、Aplix、Broadcom、中国移动（CMCC）、eBay、Esmertec、谷歌（Google）、宏达电子（HTC)、英特尔(Intel)、KDDI、LG、LivingImage、Marvell、摩托罗拉(Motorola)、NMS、NTT DoCoMo、Nuance、英伟达（Nvidia）、Packet Video、高通、SiRF、SkyPop、SONiVOX、Sprint Nextel、Synaptics、意大利电信、西班牙电信、德州仪器(TexasInstruments)、三星（Samsung)、T-Mobile、TAT、Telefónica和Wind River。\n" +
                "新加入成员\n" +
                "沃达丰（Vodafone ）、日本软件银行（Softbank）、Borqs、Omron Software、Teleca、AKMSemiconductor、ARM、AtherosCommunications 、EMP 、华硕电脑（ASUS）、日本电气股份有限公司（NEC）、泛泰（Pantech）、台湾国际航电股份有限公司（Garmin）、夏普（Sharp）、索尼爱立信（Sony Ericsson）、爱立信（Ericsson）、东芝（TOSHIBA）、中国联通（China Unicom）、SVOX、宏碁（acer)、MIPS科技公司、中国电信(ChinaTelecom)、Sasken Communication Technologies 、联发科技(MediaTek)。 \n" +
                "看完Android系统的发展简介，下面就让我们一起看看具体的Android系统版本的升级更新以及代表机型的机型有哪些。\n" +
                "1.Android 1.0 代表机型T-MobileG1\n" +
                "          在2008年，在GoogleI/O大会上，谷歌提出了AndroidHAL架构图，在同年8月18号，Android获得了美国联邦通信委员会（FCC）的批准，在2008年9月，谷歌正式发布了Android1.0系统，这也是Android系统最早的版本。\n" +
                "          在2008年，在智能手机领域还是诺基亚的天下，Symbian系统在智能手机市场中占有绝对优势，在这种前提下，谷歌发布的Android 1.0系统并没有被外界看好，甚至言论称最多一年谷歌就会放弃Android系统。");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Snackbar snackbar = Snackbar.make(activityCollapsingToolbarLayout,"已收藏...",Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
