package yisan.com.guidetest.my_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/5.
 */

public class CustomTitleView extends View {
    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */

    private Rect mBound;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        super(context);
    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,0,0);
        int n=a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText=a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor=a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize=a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        mPaint=new Paint();
        mBound=new Rect();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if (widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth=mBound.width();
            int desire= (int) (getPaddingLeft()+textWidth+getPaddingRight());
            width=desire;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeigth=mBound.height();
            int desire= (int) (getPaddingTop()+textHeigth+getPaddingBottom());
            height=desire;
        }
        setMeasuredDimension(width,height);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitleText=radomText();
                invalidate();
            }
        });
    }

    private String radomText() {
        Random random=new Random();
        Set<Integer> set=new HashSet<>();
        while (set.size()<4){
            int randomInt=random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer buffer=new StringBuffer();
        for (int i:set
             ) {
            buffer.append(""+i);
        }
        return buffer.toString();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setColor(Color.WHITE);
//        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(mTitleTextColor);
        Log.i("123456",mBound.width()+"**"+getWidth());
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.width()/2,mPaint);
    }
}
