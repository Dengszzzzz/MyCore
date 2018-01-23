package com.dengzh.sample.module.qrCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengzh.sample.R;
import com.dengzh.sample.adapter.ItemAdapter;
import com.dengzh.sample.adapter.ItemAdapter2;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;
import com.google.zxing.activity.CaptureActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzh on 2017/12/15.
 */

public class QrCodeActivity extends BaseActivity {

    private final int REQUEST_CODE = 1000;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ItemAdapter2 adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_custom_list;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("二维码");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        String[] strs = {"二维码--QrScan","二维码--BGAQRCode-Android"};
        List<String> nameList = Arrays.asList(strs);
        adapter = new ItemAdapter2(nameList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(QrCodeActivity.this,CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case 1:
                        startActivity(new Intent(QrCodeActivity.this,TestScanActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //二维码--QrScan
        if (requestCode == REQUEST_CODE && resultCode == CaptureActivity.RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            ToastUtil.showToast(scanResult);
        }
    }

}
