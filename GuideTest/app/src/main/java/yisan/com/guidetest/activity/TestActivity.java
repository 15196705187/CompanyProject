package yisan.com.guidetest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import yisan.com.guidetest.R;
import yisan.com.guidetest.interfs.OnRefreshListener;
import yisan.com.guidetest.manager.NetWorkBroadcast;
import yisan.com.guidetest.my_view.RefreshListView;

/**
 * Created by asus on 2018/4/18.
 */

public class TestActivity extends AppCompatActivity implements OnRefreshListener{
    RefreshListView refresh;
    private List<String> textList;
    private MyAdapter adapter;
    NetWorkBroadcast netWorkBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_test);
        refresh=findViewById(R.id.test_refresh);
        textList = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            textList.add("这是一条ListView的数据" + i);
        }
        adapter = new MyAdapter();
        refresh.setAdapter(adapter);
        refresh.setOnRefreshListener(this);
        netWorkBroadcast=new NetWorkBroadcast();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkBroadcast,filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkBroadcast);
    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                for (int i = 0; i < 2; i++) {
                    textList.add(0, "这是下拉刷新出来的数据" + i);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                refresh.hideHeaderView();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(5000);

                textList.add("这是加载更多出来的数据1");
                textList.add("这是加载更多出来的数据2");
                textList.add("这是加载更多出来的数据3");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();

                // 控制脚布局隐藏
                refresh.hideFooterView();
            }
        }.execute(new Void[]{});
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(TestActivity.this);
            textView.setText(textList.get(i));
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18.0f);
            return textView;
        }
    }
}
