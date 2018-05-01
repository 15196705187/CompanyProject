package yisan.com.guidetest.my_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import yisan.com.guidetest.R;

/**
 * Created by asus on 2018/3/7.
 */

public class CustomImageView extends View {
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
     * 图片
     */
    private Bitmap mImage;
    /**
     * 图片大小
     */
    private int mImageScale;
    /**
     * 画布
     */
    Rect rect,mTextBound;
    /**
     * 画笔
     */
    Paint mPaint;
    /**
     * 宽、高
     */
    int mWidth,mHeight;
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView,0,0);
        int n=a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_image:
                    mImage= BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale=a.getInt(attr,0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitleText=a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTitleTextColor=a.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTitleTextSize=a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        rect=new Rect();
        mTextBound=new Rect();
        mPaint=new Paint();
        mPaint.setTextSize(mTitleTextSize);
        //计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mTextBound);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode=MeasureSpec.getMode(widthMeasureSpec);
        int specSize=MeasureSpec.getSize(widthMeasureSpec);
        if (specMode==MeasureSpec.EXACTLY){
            mWidth=specSize;
        }else {
            //由图片决定
            int desireByImg=getPaddingLeft()+getPaddingRight()+mImage.getWidth();
            mWidth=Math.min(desireByImg,specSize);
        }
        //设置高度
        specMode=MeasureSpec.getMode(heightMeasureSpec);
        specSize=MeasureSpec.getSize(heightMeasureSpec);
        if (specMode==MeasureSpec.EXACTLY){
            mHeight=specSize;
        }else {
            int desire=getPaddingBottom()+getPaddingTop()+mImage.getHeight()+mTextBound.height();
            if (specMode==MeasureSpec.AT_MOST){
                mHeight=Math.min(desire,specSize);
            }
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        /**
         * 边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        rect.left=getPaddingLeft();
        rect.top=getPaddingTop();
        rect.right=mWidth-getPaddingRight();
        rect.bottom=mHeight-getPaddingBottom();
        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽高小于字体需要的宽高，将字体设为xxx***
         */
        if (mTextBound.width()>mWidth){
            TextPaint textPaint=new TextPaint(mPaint);
            String msg= TextUtils.ellipsize(mTitleText,textPaint,mWidth-getPaddingRight()-getPaddingLeft(),TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHeight-getPaddingBottom(),mPaint);
        }else {
            /**
             * 正常情况下，将字体居中显示
             */
            canvas.drawText(mTitleText,mWidth/2-mTextBound.width()*1.0f/2,mHeight-getPaddingBottom(),mPaint);
        }
        /**
         * 取消使用掉的块
         */
        rect.bottom-=mTextBound.height();
        if (mImageScale==1){
            canvas.drawBitmap(mImage,null,rect,mPaint);
        }else {
            rect.left=mWidth/2-mImage.getWidth()/2;
            rect.right=mWidth/2+mImage.getWidth()/2;
            rect.top=(mHeight-mTextBound.height())/2-mImage.getHeight()/2;
            rect.bottom=(mHeight-mTextBound.height())/2+mImage.getHeight()/2;
            canvas.drawBitmap(mImage,null,rect,mPaint);
        }
    }
}
