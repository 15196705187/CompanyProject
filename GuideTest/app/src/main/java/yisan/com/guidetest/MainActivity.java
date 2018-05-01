package yisan.com.guidetest;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.igexin.sdk.PushManager;

import java.io.File;

import yisan.com.guidetest.my_view.CustomImageView;
import yisan.com.guidetest.my_view.CustomProgressBar;
import yisan.com.guidetest.my_view.CustomTitleView;
import yisan.com.guidetest.service.DemoIntentService;
import yisan.com.guidetest.service.DemoPushService;

public class MainActivity extends AppCompatActivity {
    CustomImageView textView;
    CustomTitleView customTitleView;
    CustomProgressBar customProgressBar;



    private int REQUEST_PERMISSION=1;
    private Class userPushService = DemoPushService.class;
    PushManager pushManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView=findViewById(R.id.main_civ);
////        customTitleView=findViewById(R.id.main_ctv);
//        customProgressBar=findViewById(R.id.main_cpb);
        push();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    public void push(){

        pushManager=PushManager.getInstance();
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(android.Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);


        // cpu 架构
        Log.d("clientId", "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));

        // 检查 so 是否存在
        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
        Log.e("clientId", "libgetuiext2.so exist = " + file.exists());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("12345677",pushManager.getClientid(MainActivity.this)+"");
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            } else {
                Log.e("clientId", "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
