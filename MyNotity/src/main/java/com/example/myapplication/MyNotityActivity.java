package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Rin on 2019/4/18.
 */

public class MyNotityActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private static int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取NotificationManager实例
        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        MyListener listener=new MyListener();
        Button btn_send= (Button) findViewById(R.id.btn_send);
        Button btn_send2= (Button) findViewById(R.id.btn_send2);
        btn_send.setOnClickListener(listener);
        btn_send2.setOnClickListener(listener);

    }
    public class MyListener implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_send:
                    sendNotification(++n);
                    break;
                case R.id.btn_send2:
                    sendNotificationWithAction(2);
                    break;
            }
        }
    }

    //静态通知
    private void sendNotification(int nid){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setPriority(Notification.PRIORITY_DEFAULT)//通知的优先级
                .setSmallIcon(R.mipmap.ic_launcher)//小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))//设置大图标
                .setContentTitle("标题：hello")//标题
                .setContentText("内容：It's bad day!");//内容

        //生成Notification对象
        Notification notification=builder.build();

        //发送通知：id=1（通知的唯一标志）
        notificationManager.notify(nid,notification);
    }

    //可交互的通知，设置PendingIntend
    private void sendNotificationWithAction(int nid){
        //获取设置PendingIntend
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("msg","From:带Action的Notify");

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("有新资讯")//通知提醒文本
                .setContentTitle("带Action的Note")
                .setContentText("点击会打开Activity")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(nid,builder.build());
    }
}