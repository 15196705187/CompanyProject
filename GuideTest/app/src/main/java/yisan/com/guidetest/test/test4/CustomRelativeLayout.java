package yisan.com.guidetest.test.test4;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by asus on 2018/4/23.
 */

public class CustomRelativeLayout extends ViewGroup {
    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    //自定义布局中设置的宽度和高度
    private int mHoriztonalSpacing;
    private int mVerticalSpacing;


    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CascadeViewGroup);
//        try {
//            //获取设置的宽度
//            mHoriztonalSpacing = a.getDimensionPixelSize(R.styleable.CascadeViewGroup_horizontalspacing,
//                    this.getResources().getDimensionPixelSize(R.dimen.default_horizontal_spacing));
//            //获取设置的高度
//            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.CascadeViewGroup_verticalspacing,
//                    this.getResources().getDimensionPixelSize(R.dimen.default_vertical_spacing));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            a.recycle();
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int count = this.getChildCount();
        int width = this.getPaddingLeft();
        int height = this.getPaddingTop();
        for (int i = 0; i < count; i++) {
            final View currentView = this.getChildAt(i);
            this.measureChild(currentView, widthMeasureSpec, heightMeasureSpec);
            CustomRelativeLayout.LayoutParams lp = (CustomRelativeLayout.LayoutParams) currentView.getLayoutParams();
            if(lp.mSettingPaddingLeft != 0){
                width +=lp.mSettingPaddingLeft;
            }
            if(lp.mSettingPaddingTop != 0){
                height +=lp.mSettingPaddingTop;
            }
            lp.x = width;
            lp.y = height;
            width += mHoriztonalSpacing;
            height += mVerticalSpacing;
        }
        width +=getChildAt(this.getChildCount() - 1).getMeasuredWidth() + this.getPaddingRight();
        height += getChildAt(this.getChildCount() - 1).getMeasuredHeight() + this.getPaddingBottom();
        this.setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean b, int l, int i1, int i2, int i3) {
        final int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            final View currentView = this.getChildAt(i);
            CustomRelativeLayout.LayoutParams lp = (CustomRelativeLayout.LayoutParams) currentView.getLayoutParams();
            currentView.layout(lp.x, lp.y, lp.x + currentView.getMeasuredWidth(),
                    lp.y + currentView.getMeasuredHeight());
        }


    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x;
        int y;
        int mSettingPaddingLeft=0;
        int mSettingPaddingTop=0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
//            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CascadeViewGroup_LayoutParams);
//            mSettingPaddingLeft = a.getDimensionPixelSize(R.styleable.CascadeViewGroup_LayoutParams_layout_paddingleft, 0);
//            mSettingPaddingTop = a.getDimensionPixelSize(R.styleable.CascadeViewGroup_LayoutParams_layout_paddinTop, 0);
//            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }
}
