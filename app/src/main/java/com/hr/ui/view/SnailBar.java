package com.hr.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.hr.ui.R;

/**
 * Created by wdr on 2017/11/20.
 */
public class SnailBar extends View {
    public SnailBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
       /* initView(context);*/
    }

    public SnailBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
      /*  initView(context);*/
    }

    public SnailBar(Context context) {
        super(context);
        mPaint = new Paint();
        /*initView(context);*/
    }

    /** 进度条最大值 */
    private float maxCount;
    /** 进度条当前值 */
    private float currentCount;
    private float nowCount;
    /** 画笔 */
    private Paint mPaint;
    private int mWidth, mHeight;

    /**
     * 进度条斜线的宽度
     */
    private int GRAYWIDTH = 20;

    private void initView(Context context) {
        setMaxCount(100);
        setCurrentCount(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);

        int round = mHeight * 3 / 10;
        System.out.println("max=" + maxCount + "  current=" + currentCount);

        mPaint.setColor(Color.rgb(192, 192, 192));// 设置画笔的颜色
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);// 画出圆角矩形背景
        mPaint.setColor(Color.rgb(244, 244, 244));
        int myWidth = 1;//外边框的宽度
        RectF rectBlackBg = new RectF(myWidth, myWidth, mWidth - myWidth,
                mHeight - myWidth);

        canvas.drawRoundRect(rectBlackBg, round * 7 / 9, round * 7 / 9, mPaint);// 画出浅白色背景

        float section = currentCount / maxCount;//当前值的所占总值的百分比
        RectF rectProgressBg = new RectF(myWidth, myWidth, (mWidth - myWidth)
                * section, mHeight - myWidth);
        //210,105,30
        mPaint.setColor(Color.rgb(255, 165, 0));

        canvas.drawRoundRect(rectProgressBg, round * 7 / 9, round * 7 / 9,
                mPaint);

        RectF rectProgressBg2 = new RectF(myWidth, myWidth, (mWidth - myWidth)
                * section, mHeight - myWidth);
        mPaint.setColor(Color.rgb(210, 105, 30));
        // mPaint.setColor(Color.BLACK);
        float myX = rectProgressBg2.left + myWidth;//
        int length = (Math.round(((mWidth - myWidth) * section - myWidth) / (GRAYWIDTH)));
        if (length > 1) {

            for (int i = 1; i < length - 1; i+=2) {
                Path path = new Path();
                path.moveTo(myX + i * (GRAYWIDTH), rectProgressBg2.bottom);
                path.lineTo(myX + GRAYWIDTH + i * (GRAYWIDTH) , rectProgressBg2.bottom);
                path.lineTo(myX + GRAYWIDTH + (i + 1) * (GRAYWIDTH), rectProgressBg2.top);
                path.lineTo(myX + (i + 1) * (GRAYWIDTH) , rectProgressBg2.top);
                path.close();
                if ((myX + GRAYWIDTH + (i + 1) * (GRAYWIDTH))> ((mWidth - myWidth) * section - myWidth)) {
                    break;
                }
                canvas.drawPath(path, mPaint);
            }
        }

    }

    /**
     * dip转换为px
     *
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount1
     */
    public void setCurrentCount(final float currentCount1) {
        this.currentCount = currentCount1 > maxCount ? maxCount : 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (nowCount<=currentCount1) {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentCount=nowCount;
                    postInvalidate();
                    nowCount+=5;
                }
            }
        }).start();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY
                || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = dipToPx(100);
        }
        if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);// 设置控件的宽高
    }


}
