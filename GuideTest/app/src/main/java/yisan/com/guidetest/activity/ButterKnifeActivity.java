package yisan.com.guidetest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/4/18.
 */

public class ButterKnifeActivity  extends AppCompatActivity{
    @BindView(R.id.butter_knife_one)
    TextView one;
    @BindView(R.id.butter_knife_two)
    TextView two;
    @BindView(R.id.butter_knife_three)
    TextView three;
    @BindView(R.id.butter_knife_four)
    TextView four;
    @OnClick({R.id.butter_knife_one,R.id.butter_knife_two,R.id.butter_knife_three,R.id.butter_knife_four})
    public void onViewsClicked(View view){
        switch (view.getId()){
            case R.id.butter_knife_one:
                one.setText("1");
                Intent intent=new Intent(ButterKnifeActivity.this,TestActivity.class);
                startActivity(intent);
                break;
            case R.id.butter_knife_two:
                one.setText("2");
                break;
            case R.id.butter_knife_three:
                one.setText("3");
                break;
            case R.id.butter_knife_four:
                one.setText("4");
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);
    }

    public void setData() {

    }

    public void operator() {

    }

}
