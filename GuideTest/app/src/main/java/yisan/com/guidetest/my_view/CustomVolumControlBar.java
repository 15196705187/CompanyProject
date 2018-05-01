package yisan.com.guidetest.my_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/19.
 */

public class CustomVolumControlBar extends View {
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
    private int mCurrentCount = 3;

    /**
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙
     */
    private int mSplitSize;
    /**
     * 个数
     */
    private int mCount;

    private Rect mRect;

    public CustomVolumControlBar(Context context) {
        super(context);
    }

    public CustomVolumControlBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar,0,0);
        int n=a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomVolumControlBar_firstColor:
                    mFirstColor=a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomVolumControlBar_secondColor:
                    mSecondColor=a.getColor(attr,Color.CYAN);
                    break;
                case R.styleable.CustomVolumControlBar_circleWidth:
                    mCircleWidth=a.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumControlBar_bg:
                    mImage= BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.CustomVolumControlBar_dotCount:
                    mCount=a.getInt(attr,20);
                    break;
                case R.styleable.CustomVolumControlBar_splitSize:
                    mSplitSize=a.getInt(attr,20);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }

    public CustomVolumControlBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//定义线段断点形状为圆头
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        int center=getWidth()/2;//获取圆心x坐标
        int radius=center-mCircleWidth/2;//半径
        /**
         * 画块块
         */
        drawOval(canvas,center,radius);
        /**
         * 计算内切正方形位置
         */
        int relRadius=radius=mCircleWidth/2;
        /**
         * 内切正方形距顶部距离
         */
        mRect.top= (int) (relRadius-Math.sqrt(2)*1f/2*relRadius)+mCircleWidth;

        /**
         * 内切正方形左边距
         */
        mRect.left= (int) (relRadius-Math.sqrt(2)*1f/2*relRadius)+mCircleWidth;

        mRect.right= (int) (mRect.left+Math.sqrt(2)*relRadius);

        mRect.bottom= (int) (mRect.left+Math.sqrt(2)*relRadius);

        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (mImage.getWidth()<Math.sqrt(2)*relRadius){
            mRect.left= (int) (mRect.left+Math.sqrt(2)*relRadius*1f/2-mImage.getWidth()*1f/2);
            mRect.top= (int) (mRect.top+Math.sqrt(2)*relRadius*1f/2-mImage.getHeight()*1f/2);
            mRect.bottom=mRect.top+mImage.getHeight();
            mRect.right=mRect.left+mImage.getWidth();
        }
    }

    /**
     * 根据参数画出每个小块
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius){
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize=(360*1f-mCount*mSplitSize)/mCount;
        /**
         * 用于定义圆弧的形状和大小的界线
         */
        RectF oval=new RectF(centre-radius,centre-radius,centre+radius,centre+radius);
        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval,i*(mSplitSize+itemSize),itemSize,false,mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            /**
             * 根据进度画圆弧
             */
            canvas.drawArc(oval,i*(itemSize+mSplitSize),itemSize,false,mPaint);
        }
    }

    /**
     * 当前数量加1
     */
    public void up(){
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量减1
     */
    public void down(){
        mCurrentCount--;
        postInvalidate();
    }

    int xDown,xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp= (int) event.getY();
                if (xUp>xDown){
                    down();
                }else {
                    up();
                }
        }
        return true;
    }
}
