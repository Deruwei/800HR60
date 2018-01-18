package com.hr.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;

/**
 * Created by wdr on 2017/12/4.
 */

public class PieChartView extends View {
    //画笔
    private Paint mPaint,mSecondPaint;
    private int currentProgram;
    private int mWidth,mHeight;
    private float startAngle=0;
    private float endAngle=0;

    public PieChartView(Context context) {
        super(context);
        mPaint=new Paint();
        mSecondPaint=new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mSecondPaint=new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mSecondPaint=new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }
    public  void SetProgram(int program){
        currentProgram=program;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth=getWidth();
        mHeight=getHeight();
        canvas.translate(mWidth/2,mHeight/2);
        float r=(float) (Math.min(mWidth,mHeight)-6)/2;
        startAngle=0;
        endAngle=(int )((currentProgram)/100.0*360.0);
        RectF rectF=new RectF(-r,-r,r,r);
        RectF rectF1=new RectF(-r+2,-r-2,r+2,r-2);
        mPaint.setColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        canvas.drawArc(rectF,startAngle,endAngle,true,mPaint);
        startAngle=endAngle;
        endAngle=360-endAngle;
        mSecondPaint.setColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        canvas.drawArc(rectF1,startAngle,endAngle,true,mSecondPaint);
    }
}
