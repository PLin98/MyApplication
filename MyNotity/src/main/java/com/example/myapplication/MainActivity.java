package com.example.myapplication;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        TextView tv_show= (TextView) findViewById(R.id.tv_show);
        Intent intent=getIntent();
        String msg=intent.getStringExtra("msg");
        int nid=intent.getIntExtra("nid",0);
        tv_show.append("\n"+msg);

        NotificationManager manager= (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        manager.cancel(nid);


    }
}
