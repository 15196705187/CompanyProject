package yisan.com.guidetest.my_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/10.
 */

public class CustomProgressBar extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar,0,0);
        int n=a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor=a.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor=a.getColor(attr,Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth=a.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed=a.getInt(attr,20);
                    break;
            }
        }
        a.recycle();
        mPaint=new Paint();
        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   mProgress++;
                   if (mProgress==360){
                       mProgress=0;
                       if (!isNext){
                           isNext=true;
                       }else {
                           isNext=false;
                       }
                   }
                   Log.i("12341234",mProgress+"**");
                   postInvalidate();
                   try {
                       Thread.sleep(mSpeed);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        }).start();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre=getWidth()/2;//获取圆心x坐标
        int radius=centre-mCircleWidth/2;//半径
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        RectF oval=new RectF(centre-radius,centre-radius,centre+radius,centre+radius);
        if (!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre,centre,radius,mPaint);//第一个完整
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);//第二个覆盖
        }else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre,centre,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }
    }
}
