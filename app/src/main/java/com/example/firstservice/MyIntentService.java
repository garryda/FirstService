package com.example.firstservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */

//该类的出现是因为如果在普通服务中设置子线程的话，都必须要调用stopService()或者stopSelf()方法
    //才能使得服务关闭，否则该服务就会一直处在运行状态，但是我们很容易会忘记开启线程，或者忘记关闭
    //线程。为了可以简单的创建一个异步的，会自动停止的服务，安卓就专门提供了这个IntentService类

public class MyIntentService extends IntentService {

    private MyIntentService.DownloadBinder mBinder =new MyIntentService.DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(){
            Log.d("Myservice2","startDownload executed");
        }

        public int getProgress(){
            Log.d("Myservice2","getProgress executed");
            return 0;
        }
    }

    //在活动中就是调用该方法，获取到Binder类，来跟服务交流
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Myservice2","onbind");
        return mBinder;

    }
    public MyIntentService() {
        super("MyIntentService");  //调用父类的有参构造函数
   }


   //该方法已经是在子线程里面运行的了，也就是说该类会自动的开启一个子线程去执行这个方法
    @Override
    protected void onHandleIntent(Intent intent) {
      //打印当前线程的id
        Log.d("MyIntentService","Thread id is"+Thread.currentThread().getId());
    }

    //当服务停止之后会调用这个方法
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyIntentService","onDestroy executed");
    }

}
