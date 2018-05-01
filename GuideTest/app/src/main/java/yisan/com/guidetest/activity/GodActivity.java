package yisan.com.guidetest.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import yisan.com.guidetest.R;
import yisan.com.guidetest.service.BinderService;

/**
 * Created by asus on 2018/4/16.
 */

public class GodActivity extends BaseActivity {
    TextView start,bind,unbind,data;
    ServiceConnection connection;
    BinderService service;
    Intent intent;
    @Override
    public void intial(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_god);
    }

    @Override
    public void setData() {
        start=findViewById(R.id.god_start_service);
        bind=findViewById(R.id.god_bind_service);
        unbind=findViewById(R.id.god_unbind_service);
        data=findViewById(R.id.god_get_data);
        start.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        data.setOnClickListener(this);
    }

    @Override
    public void operator() {
        //创建绑定对象
        intent=new Intent(GodActivity.this,BinderService.class);
        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                BinderService.LocalBinder binder= (BinderService.LocalBinder) iBinder;
                service=binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                service=null;
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.god_start_service:
                break;
            case R.id.god_bind_service:
                bindService(intent,connection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.god_unbind_service:
                if (service!=null){
                    service=null;
                    unbindService(connection);
                }
                break;
            case R.id.god_get_data:
                start.setText(service.getCount()+"****");
                break;
        }
    }
}
