package yisan.com.guidetest.my_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by asus on 2018/3/26.
 */

public class CustomDragView extends LinearLayout {
    private ViewDragHelper helper;

    public CustomDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        helper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBounds=getPaddingLeft();
                int rightBounds=getWidth()-child.getWidth()-leftBounds;
                int newLeft=Math.min(Math.max(left,leftBounds),rightBounds);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int topBounds=getPaddingTop();
                int bottomBounds=getHeight()-child.getHeight()-topBounds;
                int newTop=Math.min(Math.max(top,topBounds),bottomBounds);
                return newTop;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }
}
