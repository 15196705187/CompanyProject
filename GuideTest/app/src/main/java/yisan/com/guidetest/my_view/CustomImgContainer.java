package yisan.com.guidetest.my_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by asus on 2018/3/11.
 */

public class CustomImgContainer extends ViewGroup{

    public CustomImgContainer(Context context) {
        super(context);
    }

    public CustomImgContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        /**
         * 计算出所以children
         */
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        /**
         * 记录如果是wrap_content作为宽高
         */
        int width=0;
        int height=0;
        int cCount=getChildCount();
        int cWidth=0;
        int cHeight=0;
        MarginLayoutParams cParams=null;

        /**
         * 用于计算左边两个children的高度
         */
        int lHeight=0;

        /**
         * 用于计算右边两个children高度，最终高度取大的那一个
         */
        int rHeight=0;

        /**
         * 用于计算上面两个children的宽度
         */
        int tWidth=0;

        /**
         * 用于计算下面两个children宽度
         */
        int bWidth=0;

        /**
         * 根据children计算出的宽高度，以及设置margin容器计算的宽高度，主要用于容器wrap_content时候
         */
        for (int i = 0; i < cCount; i++) {
            View view=getChildAt(i);
            cWidth=view.getMeasuredWidth();
            cHeight=view.getMeasuredHeight();
            cParams= (MarginLayoutParams) view.getLayoutParams();
            if (i==0||i==1){
                tWidth=cWidth+cParams.leftMargin+cParams.rightMargin;
            }
            if (i==2||i==3){
                bWidth=cWidth+cParams.rightMargin+cParams.leftMargin;
            }
            if (i==0||i==2){
                lHeight+=cHeight+cParams.topMargin+cParams.bottomMargin;
            }
            if (i==1||i==3){
                rHeight+=cHeight+cParams.topMargin+cParams.bottomMargin;
            }
            width=Math.max(tWidth,bWidth);
            height=Math.max(lHeight,rHeight);

            /**
             * 如果wrap_content 设置为我们计算的值
             * 否则直接返回父类容器的值
             */
            setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width,heightMode==MeasureSpec.EXACTLY?
            heightSize:height);
        }

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int cCount=getChildCount();
        int cWidth=0;
        int cHeight=0;
        MarginLayoutParams cParams=null;
        /**
         * 遍历所有子布局，根据其宽高以及margin进行布局
         */
        for (int j = 0; j < cCount; j++) {
            View childView=getChildAt(j);
            cWidth=childView.getMeasuredWidth();
            cHeight=childView.getMeasuredHeight();
            cParams= (MarginLayoutParams) childView.getLayoutParams();
            int cr=0,cl=0,ct=0,cb=0;
            switch (j){
                case 0:
                    cl=cParams.leftMargin;
                    ct=cParams.topMargin;
                    break;
                case 1:
                    cl=getWidth()-cWidth-cParams.leftMargin-cParams.rightMargin;
                    ct=cParams.topMargin;
                    break;
                case 2:
                    cl=cParams.leftMargin;
                    ct=getHeight()-cHeight-cParams.bottomMargin;
                    break;
                case 3:
                    cl=getWidth()-cWidth-cParams.leftMargin-cParams.rightMargin;
                    ct=getHeight()-cHeight-cParams.bottomMargin;
                    break;
            }
            cr=cWidth+cl;
            cb=cHeight+ct;
            childView.layout(cl,ct,cr,cb);
        }
    }
}
