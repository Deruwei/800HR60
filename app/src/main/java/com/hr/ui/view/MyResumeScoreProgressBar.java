package com.hr.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hr.ui.R;

/**
 * Created by wdr on 2017/11/23.
 */

public class MyResumeScoreProgressBar extends View{
    /*
  * 第一圈颜色
  */
    int firstColor;
    /*
     * 第二圈颜色
     */
    int secondColor;
    /*
     * 圆的宽度
     */
    int circleWidth;
    /*
     * 速率
     */
    int speed;
    /*
     * 画笔
     */
    Paint mPaint;
    /*
     * 进度
     */
    int mProgress;
    /*
     * 是否切换标志
     */
    boolean isNext;
    private int textSize;

    public MyResumeScoreProgressBar(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for(int i=0; i<n; i++){
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView_firstColor:
                    firstColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomView_secondColor:
                    secondColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomView_circleWidth:
                    circleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView_speed:
                    speed = typedArray.getInt(attr, 20);
                    break;
                case R.styleable.CustomView_textSize:
                    textSize=typedArray.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;

            }
        }
        typedArray.recycle();
        mPaint = new Paint();
    }
    public  void setProgram(final int program){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress<=(int)(((float)360/(float)100)*program)) {
                    mProgress++;
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public MyResumeScoreProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyResumeScoreProgressBar(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*int centre = getWidth() / 2; // 获取圆心的x坐标*/
        int centerX = getWidth()/2;//获取中心点X坐标
        int certerY = getHeight()/2;//获取中心点Y坐标
        int radius = centerX - circleWidth / 2;// 半径
        mPaint.setStrokeWidth(5); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius); // 用于定义的圆弧的形状和大小的界限
        if (isNext=false) {// 第一颜色的圈完整，第二颜色跑
            mPaint.setColor(firstColor); // 设置圆环的颜色
            canvas.drawCircle(centerX, centerX, radius, mPaint); // 画出圆环
            mPaint.setColor(secondColor); // 设置圆环的颜色
            canvas.drawArc(oval, 0, mProgress, false, mPaint); // 根据进度画圆弧
        } else {
            mPaint.setColor(secondColor); // 设置圆环的颜色
            canvas.drawCircle(centerX, centerX, radius, mPaint); // 画出圆环
            mPaint.setColor(firstColor); // 设置圆环的颜色
            canvas.drawArc(oval, 0, mProgress, false, mPaint); // 根据进度画圆弧
        }
        //画出百分比字符串
        mPaint.setStrokeWidth(0);
        mPaint.setColor(firstColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int)(((float)mProgress / (float)360) * 100);//计算百分比
        float textWidth = mPaint.measureText(percent +"%");//测量字体宽度，需要居中显示
        float textHeight=mPaint.getFontMetrics().descent- mPaint.getFontMetrics().ascent;//测量字体的宽度
        canvas.drawText(percent+"%", centerX-textWidth/2, certerY+textSize/2 , mPaint);
    }
}
