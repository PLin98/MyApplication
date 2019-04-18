package com.example.myapplication.serviceActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.service.BindService2WithThread;


/**
 * Created by Rin on 2019/4/11.
 */

public class BindService2WithThreadActivity extends AppCompatActivity{
    private BindService2WithThread bindServiceWithThread;
    private boolean isBound=false;
    private static TextView tv_show;
    private BroadcastReceiver myReceiver;

    private static Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_show.setText(tv_show.getText().toString()+"\n"+msg.obj.toString());
        }
    };
    public static Handler getMyhandler(){
        return myhandler;
    }

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService2WithThread.MyBinder myBinder= (BindService2WithThread.MyBinder) service;
            bindServiceWithThread= myBinder.getService();
            //String msg=bindServiceWithThread.say();

            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
            bindServiceWithThread=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show= (TextView) findViewById(R.id.tv_show);
        Button btn_test= (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BindService2WithThreadActivity.this,BindService2WithThread.class);
                bindService(intent,conn, Service.BIND_AUTO_CREATE);//绑定服务
            }
        });
        Button btn_stop= (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    unbindService(conn);//解绑
                }
            }
        });

        //动态注册广播
        myReceiver=new myReceiver();
        IntentFilter filter=new IntentFilter(BindService2WithThread.MY_ACTION);
        registerReceiver(myReceiver,filter);

    }
    /*public static void show(int id){
        tv_show.setText("服务已启动"+id+"次");
    }*/

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }
    private class myReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(BindService2WithThread.MY_ACTION)){
                String str=intent.getStringExtra("msg");
                tv_show.append("\n"+str);
                tv_show.setTextColor(Color.RED);
            }
        }
    }
}

