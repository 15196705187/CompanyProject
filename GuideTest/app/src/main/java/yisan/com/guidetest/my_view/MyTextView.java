package yisan.com.guidetest.my_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/28.
 */

public class MyTextView extends View {
    private String myText;//文字
    private int myTextColor,bgColor;//颜色控制
    private int myTextSize;//文字大小
    private int bodlerWidth;//边框宽度

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyTextView,0,0);
        int n=array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr=array.getIndex(n);
            switch (attr){
                case R.styleable.MyTextView_bgColor:
                    bgColor=array.getColor(attr,Color.GREEN);
                    break;
                case R.styleable.MyTextView_bolderWidth:
                    bodlerWidth=array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyTextView_myTextColor:
                    myTextColor=array.getColor(attr,Color.RED);
                    break;
                case R.styleable.MyTextView_myTextSize:
                    myTextSize=array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyTextView_myText:
                    myText=array.getString(attr);
                    break;
            }
            array.recycle();
            mPaint=new Paint();
            mBound=new Rect();
            mPaint.setTextSize(myTextSize);
            Log.i("66666666",myText+"");
            mPaint.getTextBounds(myText,0,myText.length(),mBound);
        }
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            mPaint.setTextSize(myTextSize);
            mPaint.getTextBounds(myText,0,myText.length(),mBound);
            float textWidth=mBound.width();
            int desire= (int) (getPaddingLeft()+textWidth+getPaddingRight());
            width=desire;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else {
            mPaint.setTextSize(myTextSize);
            mPaint.getTextBounds(myText,0,myText.length(),mBound);
            float textHeigth=mBound.height();
            int desire= (int) (getPaddingTop()+textHeigth+getPaddingBottom());
            height=desire;
        }
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(myTextColor);
        canvas.drawText(myText,getWidth()/2-mBound.width()*1.0f/2,getHeight()/2-mBound.height()/2,mPaint);
        Paint paint=new Paint();
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(bodlerWidth);
        paint.setAntiAlias(true);
        Path path = new Path();
//        RectF oval = new RectF(50, 50, 150, 150);
//        path.addRoundRect(oval,5,5, Path.Direction.CW);
//        RectF oval2 = new RectF(50, 200, 250, 300);
//        path.addOval(oval2, Path.Direction.CCW);
//        RectF oval3 = new RectF(50, 350, 150, 450);
//        path.addRect(oval3, Path.Direction.CCW);
//        path.addCircle(100, 550, 50, Path.Direction.CCW);
        path.moveTo(120,120);
        path.lineTo(220,120);
        path.lineTo(220,140);
        path.lineTo(228,150);
        path.lineTo(220,160);
        path.lineTo(220,180);
        path.lineTo(120,180);
        path.lineTo(120,120);
        path.close();
//        path.setFillType(Path.FillType.EVEN_ODD);
        //连接的外边缘以圆弧的方式相交
        paint.setStrokeJoin(Paint.Join.ROUND);


        canvas.drawPath(path, paint);
    }
}
