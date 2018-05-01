package yisan.com.guidetest.test.test5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by asus on 2018/4/23.
 */

public class CustomFluidLayout extends ViewGroup {
    public CustomFluidLayout(Context context) {
        super(context);
    }

    public CustomFluidLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFluidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public CustomFluidLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取出系统的模式和大小
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        //用来保存一行的宽高
        int lineW = 0, lineH = 0;
        //测量出来的宽高
        int width = 0, height = 0;
        //获取子控件的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //当前的子view
            View childAt = getChildAt(i);
            //测量每一个子控件的宽高
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            //获取MarginLayoutParams对象
            MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
            //此时子控件的宽高
            int childW = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childH = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            //判断当前这个子view的宽是否超过限定的宽度
            if (lineW + childW > sizeW) {//换行
                //宽度取最大的
                width = Math.max(lineW, childH);
                //从新记录下一行的宽度
                lineW = childW;
                //累加高度
                height += lineH;
                //记录下一行的高度
                lineH = childH;
            } else {
                lineW += childW;
                //行高取最大值
                lineH = Math.max(lineH, childH);
            }
            if (i == childCount - 1) {//在最后一行时的判断
                // width+=childW;
                width = Math.max(width, lineW);
                height += lineH;
            }
        }
        setMeasuredDimension(modeW == MeasureSpec.EXACTLY ? sizeW : width,
                modeH == MeasureSpec.EXACTLY ? sizeH : height);
    }


    /*
      *返回当前的系统的MarginLayoutParams的值，因为我们需要用到margin
      * @param attrs
      * @return
    */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onLayout(boolean b, int j, int i1, int i2, int i3) {
//父控件的宽度(包含padding)
        int width = getWidth();
//子控件在父控件中x轴方向上能显示的最大宽度
        int maxWidth = width - getPaddingRight();
//最开始的位置的j
        int baseLeft = getPaddingLeft();
        int baseTop = getPaddingTop();
//当前的距左边和顶部的距离
        int currentLeft = baseLeft;
        int currentTop = baseTop;
//表示当前view的left、top、right、bottom的位置
        int viewL = 0, viewT = 0, viewR = 0, viewB = 0;
//子控件的个数
        int childCount = getChildCount();
//用来保存要换行的上一个子view的高度
        int lastChildViewHeight = 0;
        for (int i = 0; i < childCount; i++) {
//当前子view
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == View.GONE) {
                continue;
            }
//当前子view的MarginLayoutParams对象
            MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
//子view实际的宽高包括margin
            int childWidth = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            if (currentLeft + childWidth > maxWidth) {//换行
//此时距顶部距离累加
                currentTop += lastChildViewHeight;
                viewL = baseLeft + params.leftMargin;
                viewT = currentTop + params.topMargin;
                viewR = viewL + childAt.getMeasuredWidth();
                viewB = viewT + childAt.getMeasuredHeight();
//由于此时是新的一行，所以currentLeft要从新开始
                currentLeft = baseLeft + childWidth;
            } else {//
                viewL = currentLeft + params.leftMargin;
                viewT = currentTop + params.topMargin;
                viewR = viewL + childAt.getMeasuredWidth();
                viewB = viewT + childAt.getMeasuredHeight();
//当前距左边距离累加
                currentLeft += childWidth;
            }
            lastChildViewHeight = childHeight;
            childAt.layout(viewL, viewT, viewR, viewB);
        }
    }
}
