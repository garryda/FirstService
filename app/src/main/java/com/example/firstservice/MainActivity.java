package com.example.firstservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyIntentService.DownloadBinder downloadBinder;

    //每个活动如果想和服务绑定的话，必定会在活动的内部声明一个连接类
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        //当和服务绑定后，服务会返回一个IBinder对象参数给这个方法
        public void onServiceConnected(ComponentName name, IBinder service) {
            //如果需要动态监听服务的话，这边应该是设立了一个无限循环，不断的调用服务的方法来更新最新数据
            downloadBinder=(MyIntentService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        Button startIntentService = (Button) findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent=new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent =new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent=new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.start_intent_service:
                Log.d("MainActivity", "Thread id is " + Thread.currentThread(). getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                bindService(intentService,connection,BIND_AUTO_CREATE);//绑定服务
                break;
            default:
                break;
        }
    }
}
