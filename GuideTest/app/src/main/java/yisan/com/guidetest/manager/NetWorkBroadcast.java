package yisan.com.guidetest.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by asus on 2018/5/1.
 */

public class NetWorkBroadcast extends BroadcastReceiver {
    public static int NETWORK_NOW;
    public static int NETWORK_NONE=-1;
    public static int NETWORK_PHONE=0;
    public static int NETWORKE_WIFE=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isAvailable()){
            switch (networkInfo.getType()){
                case ConnectivityManager.TYPE_WIFI:
                    NETWORK_NOW=NETWORKE_WIFE;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    NETWORK_NOW=NETWORK_PHONE;
                    break;
            }
        }else {
            NETWORK_NOW=NETWORK_NONE;
        }
        Log.i("networksss","3333333333333");
    }
}
