package com.dengzh.sample.module.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;

import java.util.UUID;

/**
 * Created by dengzh on 2017/12/22.
 */

public class AcceptThread extends Thread{

    private BluetoothServerSocket mServerSocket;
    private BluetoothAdapter mBluetoothAdapter;
    private final String MY_UUID = "MY_UUID";

    public AcceptThread(BluetoothAdapter mBluetoothAdapter,String NAME) {
        //BluetoothServerSocket tmp = null;
        // MY_UUID is the app's UUID string, also used by the client code   目前只能到配对成功这一步，无法连接上，应该是UUID不一样的问题
      //  tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME",);

    }
}
