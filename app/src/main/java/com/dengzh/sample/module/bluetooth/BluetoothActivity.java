package com.dengzh.sample.module.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dengzh.core.utils.LogUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.utils.ToastUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2017/12/22.
 * 蓝牙开发
 * 1.权限  清单文件加入
 * <uses-permission android:name="android.permission.BLUETOOTH" />
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
 * 2.重要API
 * BluetoothAdapter :这个类代表蓝牙适配器，并且是所有蓝牙交互的入口点
 * BluetoothDevice  :代表一个蓝牙设备，通它可以通过BluetoothSocket向其他的蓝牙设备发出连接请求；
 */

public class BluetoothActivity extends BaseActivity {

    private String TAG = "BluetoothActivity";
    private final int REQUEST_ENABLE_BT = 10001;

    @BindView(R.id.searchBlueBoothBt)
    Button searchBlueBoothBt;
    @BindView(R.id.hintTv)
    TextView hintTv;
    @BindView(R.id.bluetoothNameTv)
    TextView bluetoothNameTv;
    @BindView(R.id.bluetoothNameTv2)
    TextView bluetoothNameTv2;

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_blue_tooth;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("蓝牙开发");
    }

    @Override
    protected void initData() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,filter);
    }

    /**
     * 测试一下蓝牙
     */
    private void testBlueBooth() {
        //1.检测设备是否支持蓝牙
        if (mBluetoothAdapter == null) {
            //设备不支持蓝牙
            ToastUtil.showToast("设备不支持蓝牙");
        } else {
            bluetoothNameTv.setText("蓝牙名字：" + mBluetoothAdapter.getName());
            mBluetoothAdapter.setName("本地蓝牙重命名");  //本地蓝牙重命名
            bluetoothNameTv2.setText("改名字后：" + mBluetoothAdapter.getName());
        }
    }

    /**
     * 1.开启蓝牙
     */
    private void openBlueBooth(){
        //1.检测设备是否支持蓝牙
        if(mBluetoothAdapter!=null){
            //如果蓝牙被关闭
            if (!mBluetoothAdapter.isEnabled()) {
                //打开蓝牙
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }else{
                ToastUtil.showToast("蓝牙已经处于开启状态");
            }
        }
    }

    /**
     * 2.查询已经配对过的设备
     */
    private void searchBlueBooth(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device:pairedDevices){
                //遍历设备，查看已匹配设备
                ToastUtil.showToast("已配对设备"+device.getName()+" 地址:"+device.getAddress());
                LogUtil.e(TAG,"已配对设备"+device.getName()+" 地址:"+device.getAddress());
            }
        }else{
            ToastUtil.showToast("没有配对设备");
        }
    }

    /**
     * 3.搜索其他设备
     * 我们可以通过调用 startDiscovery() 来搜索新设备，这个过程是异步的，并且这个方法会立即返回boolean值说明此次搜索是否成功启动；
     搜索过程大约12秒的扫描；
     我们还需要实现一个广播接受者来接收被发现的设备的信息；
     注意：startDiscovery()对于BluetoothAdapter来说是一项沉重的任务，会消耗大量的资源，所以如果我们尝试连接搜索到的新设备之前需要调用cancelDiscovery()来取消这一过程；
     同样,如果你已经连接上一个设备,然后执行startDiscovery()将会显著减少可用的带宽连接,所以不应该在连接上设备的同时startDiscovery()。
     */
    private void searchBlueBooth2(){
        mBluetoothAdapter.startDiscovery();

    }

    /**
     * 4.设置本机蓝牙可被其他设备检查到
     * 如果想让我们的设备可以被其他设备检测到，需要执行以下代码；
     可被检测时间通过intent设置BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION属性，如果不设置默认120秒的可被检测时间；
     可被检测时间下限为0（0意味着始终可以被检测到），上限为3600秒，如果低于0或者高于3600，那么系统默认设置它为120秒；
     如果蓝牙没有打开而直接设置蓝牙的可检测性，那么蓝牙会被自动打开。


     */
    private void setBlueBoothCanFound(){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
        startActivity(discoverableIntent);
    }

    //自定义广播接收者，监听搜索到的设备
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //当发现设备
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtil.e(TAG,"发现的设备"+device.getName()+" 地址:"+device.getAddress());
                ToastUtil.showToast("发现的设备："+device.getName()+" 地址:"+device.getAddress());
            }else if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)){
                //新的扫描模式
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,-100);
                LogUtil.e(TAG, "onReceive: 新的扫描模式"+scanMode);
                switch (scanMode){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE: //设备既能被其他设备检测到，又能被其他设备连接
                        ToastUtil.showToast("SCAN_MODE_CONNECTABLE_DISCOVERABLE");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:  //设备不可被检测到，但是还是可以被之前发现过这个蓝牙的设备连接到
                        ToastUtil.showToast("SCAN_MODE_CONNECTABLE");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:    //设备既不能被其他设备检测到，又不能被其他设备连接
                        ToastUtil.showToast("SCAN_MODE_NONE");
                        break;
                }
                //老的扫描模式
                int preScanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE,-100);
                LogUtil.e(TAG, "onReceive: 老的扫描模式"+preScanMode);
            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            ToastUtil.showToast("已经打开了蓝牙");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @OnClick({R.id.searchBlueBoothBt, R.id.openBlueBoothBt,R.id.searchBlueBoothBt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBlueBoothBt:
                searchBlueBooth();
                break;
            case R.id.openBlueBoothBt:
                openBlueBooth();
                break;
            case R.id.searchBlueBoothBt2:  //查询操作要开启蓝牙后操作
                searchBlueBooth2();
                break;
        }
    }
}
