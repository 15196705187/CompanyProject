package yisan.com.guidetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by asus on 2018/4/16.
 */

public class SimpleService extends Service {

    /**
     * 绑定时才会调用
     * 必须重写的方法
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onStartCommand()或onBind()之前）
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次。
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
