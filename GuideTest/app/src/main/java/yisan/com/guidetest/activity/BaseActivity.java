package yisan.com.guidetest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by asus on 2018/4/7.
 */

abstract class  BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intial(savedInstanceState);
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        operator();
    }

    public abstract void intial(@Nullable Bundle savedInstanceState);
    public abstract void setData();
    public abstract void operator();
}
