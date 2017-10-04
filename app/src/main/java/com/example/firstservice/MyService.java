package com.example.firstservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    //这个类是为了服务能和活动有更好的交流而在Service中
    //设立的
    private DownloadBinder mBinder =new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("Myservice","startDownload executed");
        }

        public int getProgress(){
            Log.d("Myservice","getProgress executed");
            return 0;
        }
    }

    //在活动中就是调用该方法，获取到Binder类，来跟服务交流
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Myservice","onbind");
       return mBinder;

    }

    //当服务第一次打开时，其会调用该方法，这边启动的是
    //前台服务，前台服务其实就是类似加了通知的服务，这边点击了通知的话，就会跳转到MainActivity
    //PendingIntent就是Intent的特殊版本，延迟的Intent，需要跳转时才会跳转
    public void onCreate(){
        super.onCreate();
        Log.d("MyService","onCreate executed");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    //这个方法是可以重复调用的
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    //当服务销毁的时候，其会调用
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService","onDestroy executed");
    }
}
