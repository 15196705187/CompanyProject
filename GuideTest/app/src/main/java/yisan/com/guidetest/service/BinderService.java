package yisan.com.guidetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by asus on 2018/4/16.
 */

public class BinderService extends Service {
    private LocalBinder binder=new LocalBinder();
    private Thread thread;
    private long count=0;
    public class LocalBinder extends Binder{
        /**
         * 声明一个方法getService(提供给客户端调用)
         * @return
         */
        public BinderService getService(){
            return BinderService.this;
        }
    }

    /**
     * 把binder返回给客户端
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        thread.start();
    }

    /**
     * 公共方法
     * @return
     */
    public long getCount(){
        return count;
    }

    /**
     * 解除绑定时调用
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
