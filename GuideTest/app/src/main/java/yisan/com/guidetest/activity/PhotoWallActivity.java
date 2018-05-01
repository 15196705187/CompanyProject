package yisan.com.guidetest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import yisan.com.guidetest.R;
import yisan.com.guidetest.adaptor.PhotoWallAdapter;
import yisan.com.guidetest.been.Images;

/**
 * Created by asus on 2018/4/7.
 */

public class PhotoWallActivity extends BaseActivity {
        /**
         * 用于展示照片墙的GridView
         */
        private GridView mPhotoWall;

        /**
         * GridView的适配器
         */
        private PhotoWallAdapter adapter;

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // 退出程序时结束所有的下载任务
            adapter.cancelAllTasks();
        }

    @Override
    public void intial(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mPhotoWall =  findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
    }

    @Override
    public void setData() {

    }

    @Override
    public void operator() {

    }

    @Override
    public void onClick(View view) {

    }
}
