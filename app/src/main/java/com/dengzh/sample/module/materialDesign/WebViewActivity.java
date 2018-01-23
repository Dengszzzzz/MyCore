package com.dengzh.sample.module.materialDesign;

import android.os.Bundle;
import android.webkit.WebView;

import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by dengzh on 2018/1/11.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_web_view;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("WebView");
    }

    @Override
    protected void initData() {
        String data = "<p>This is some text in a very short paragraph</p><img src=\"http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg\" />";
        webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

}
