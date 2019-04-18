package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Rin on 2019/4/11.
 */

public class BindService2WithThread extends Service {
    public final static String MY_ACTION="edu.cwu.BindService2WithThread";
    private String TAG="===========BindServiceWithThread";
    private Thread thread;
    private final IBinder myBinder=new MyBinder();
    public class MyBinder extends Binder {
        public BindService2WithThread getService(){
            return BindService2WithThread.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        thread.start();
        return myBinder;
    }
    public boolean onUnbind(Intent intent) {
        //Log.i(TAG,"onUnbind:AudioBindService");
        return super.onUnbind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    Log.i(TAG,"Task is running"+i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /*Log.i(TAG,"Task running"+(i/5.0)*100+"%");
                    Handler handler= BindServiceWithThreadActivity.getMyhandler();
                    Message msg=handler.obtainMessage();
                    msg.obj="Task running  "+(i/5.0)*100+"%";
                    handler.sendMessage(msg);*/
                    String str="Task running"+(i/5.0)*100+"%";
                    //发送广播
                    Intent intent=new Intent(MY_ACTION);
                    intent.putExtra("msg",str);
                    sendBroadcast(intent);

                    //回调接口

                    /*if(onUpdateListener!=null)
                        onUpdateListener.onUpdate(str);*/
                }
                Log.i(TAG,"Task has done!");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(thread.isAlive()){
            thread.interrupt();
        }
    }
}
