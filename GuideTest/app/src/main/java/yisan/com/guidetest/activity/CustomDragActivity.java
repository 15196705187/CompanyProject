package yisan.com.guidetest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/26.
 */

public class CustomDragActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{
    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drag);
        tv1=findViewById(R.id.custom1);
        tv2=findViewById(R.id.custom2);
        tv3=findViewById(R.id.custom3);
//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
//        tv1.setOnTouchListener(this);
//        tv2.setOnTouchListener(this);
//        tv3.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
