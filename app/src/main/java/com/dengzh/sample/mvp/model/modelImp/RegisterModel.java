package com.dengzh.sample.mvp.model.modelImp;

import com.dengzh.sample.mvp.model.modelInter.IRegisterModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class RegisterModel implements IRegisterModel {


    @Override
    public void submitRegister(final String phone, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //延迟执行，模仿网络交互,方式一
//                Looper.prepare();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(phone.equals("13728778986")){
//                            callBack.callBack(100);
//                        }else{
//                            callBack.callBack(200);
//                        }
//                    }
//                },3*1000);
//                Looper.loop();


                //方式2
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if(phone.equals("13728778986")){
                            callBack.callBack(100);
                        }else{
                            callBack.callBack(200);
                        }
                    }
                };
                new Timer().schedule(task,1*1000);

            }
        }).start();
    }
}
