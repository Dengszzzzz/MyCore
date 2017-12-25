package com.dengzh.sample.module.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by dengzh on 2017/12/22.
 * 蓝牙服务器端
 */

public class AcceptThread extends Thread{

    private BluetoothServerSocket mServerSocket;
    // 3.用来唯一地标识应用程序的蓝牙服务，客户端使用服务端的UUID来进行通信匹配,
    // 也就是说客户端和服务端用的UUID必须相同才能连接的上；
    private final UUID MY_UUID = java.util.UUID.randomUUID();


    public AcceptThread(BluetoothAdapter mBluetoothAdapter,String NAME) {
        BluetoothServerSocket tmp = null;
        //MY_UUID is the app's UUID string, also used by the client code   目前只能到配对成功这一步，无法连接上，应该是UUID不一样的问题
        try {
            //4.参数String代表这个服务的名称，可以是任意的字符串，一般用我们应用的名称;
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("MyCore",MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mServerSocket = tmp;
    }

    public void run(){
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mServerSocket.accept();  //5.开启监听（和TCP的差不多），成功接收那么返回一个BluetoothSocket;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            // If a connection was accepted
            if(socket!=null){
                // Do work to manage the connection (in a separate thread)
                //manageConnectedSocket(socket);
                //当客户端获取到服务端提供的这个BluetoothSocket，那么服务端的BluetoothServerSocket可以（也应该）关闭了，除非服务端想要接受更多设备的连接；
                try {
                    mServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    break;
                }
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel(){
        try {
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
