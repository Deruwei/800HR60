package com.hr.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hr.ui.R;

/**
 * Created by wdr on 2017/11/24.
 */

public class DialogView extends View {
    private int width;
    private int heigth;
    private int recWidth=30;
    private float textWidth;
    private float textHeight;
    private String text="我非常非常的好设计思路穆斯林岁的弟弟，我的时空隧道建设领导送礼的，老大";
    /**
     * 画笔,文本绘制范围
     */
    private Rect mBound;
    private int textColor;
    private int textSize;
    private Paint mPaintNormal;
    private Path mPathRect;
    //定义一个接口对象listerner
    private OnViewClickListener listener;
    //获得接口对象的方法。

    public void setListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    public DialogView(Context context) {
        super(context);
    }

    public DialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyDialogView);
       textSize= a.getDimensionPixelSize(R.styleable.MyDialogView_textSizeDv, 20);
        textColor = a.getColor(R.styleable.MyDialogView_textColor, Color.BLACK);

        mBound = new Rect();
        mPaintNormal=new Paint();
        mPaintNormal.setAntiAlias(true);
        mPaintNormal.setTextSize(textSize);
        mPaintNormal.setColor(Color.BLACK);
        mPaintNormal.setAlpha(100);
        //background包括color和Drawable,这里分开取值
        mPaintNormal.getTextBounds(text, 0, text.length(), mBound);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        mPathRect=new Path();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = onMeasureR(0, widthMeasureSpec);
        heigth = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width, heigth);
        Log.d("length","宽度是"+width);
        Log.d("length","高度是"+heigth);
    }
    /**
     * 计算控件宽高
     *
     * @param attr
     *            [0宽,1高]
     * @param oldMeasure
     * @author Ruffian
     */
    public int onMeasureR(int attr, int oldMeasure) {

        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                float value;
                if (attr == 0) {
                    value = mBound.width();
                    // value = mPaint.measureText(mText);
                    // 控件的宽度  + getPaddingLeft() +  getPaddingRight()
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());
                } else if (attr == 1) {
                    value = mBound.height();
                    // FontMetrics fontMetrics = mPaint.getFontMetrics();
                    // value = Math.abs((fontMetrics.descent - fontMetrics.ascent));
                    // 控件的高度  + getPaddingTop() +  getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());
                }
                break;
        }
        return newSize;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个三角形或者多边形
        mPathRect.moveTo(2,(getHeight()-2)/4*3);
        mPathRect.lineTo(2,getHeight()-2);//起点
        mPathRect.lineTo(getWidth()-2,getHeight()-2);
        mPathRect.lineTo(getWidth()-2,(getHeight()-2)/4*3);
        mPathRect.lineTo(2,(getHeight()-2)/4*3);
        mPathRect.lineTo(2,recWidth/2+2);
        mPathRect.lineTo(getWidth()-recWidth-2,recWidth/2+2);
        mPathRect.lineTo(getWidth()-recWidth/2-2,2);
        mPathRect.lineTo(getWidth()-2,recWidth/2+2);
        mPathRect.lineTo(getWidth()-2,(getHeight()-2)/4*3);
        mPathRect.close();//将最后到达的点和第一个点连接起来
        canvas.drawPath(mPathRect,mPaintNormal);//画出路径
        mPaintNormal.setColor(textColor);
        mPaintNormal.setColor(Color.WHITE);
        mPaintNormal.setStyle(Paint.Style.STROKE);
        mPaintNormal.setAntiAlias(true);
        Path pathLine=new Path();
        pathLine.moveTo(getWidth()-2,(getHeight()-2)/4*3);
        pathLine.lineTo(2,(getHeight()-2)/4*3);
        canvas.drawPath(pathLine,mPaintNormal);//画出路径
		/*
		 * 控件宽度/2 - 文字宽度/2
		 */
        int startX = getWidth() / 2;
		/*
		 * 控件高度/2 + 文字高度/2,绘制文字从文字左下角开始,因此"+"
		 */
        // float startY = getHeight() / 2 + mBound.height() / 2;
        Paint.FontMetricsInt fm = mPaintNormal.getFontMetricsInt();
        // int startY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent)
        // / 2;
        int startY=((getHeight()-2)/4*3-recWidth/2)/2+recWidth/2;
      /*  int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;*/
        Point point=new Point(startX,startY);
        // 绘制文字
    /*    canvas.drawText(text, startX, startY, mPaintNormal);*/
        TextPaint tp = new TextPaint();
        tp.setColor(textColor);
        tp.setStyle(Paint.Style.FILL_AND_STROKE);
        textCenter(text,tp,canvas,point,getWidth(),Layout.Alignment.ALIGN_CENTER,1.2f,0,false);


        int OkY=getHeight()/8*7;
        int OKX=getWidth()/2;
        Point point1=new Point(OKX,OkY);
        // 绘制文字
    /*    canvas.drawText(text, startX, startY, mPaintNormal);*/
        TextPaint tp1 = new TextPaint();
        tp1.setColor(textColor);
        tp1.setStyle(Paint.Style.FILL_AND_STROKE);
        textCenter("关闭",tp1,canvas,point1,getWidth(),Layout.Alignment.ALIGN_CENTER,1.2f,0,false);
       /* canvas.restore();*/
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (y>(getHeight()-2)/4*3) {
                    if(listener!=null) {
                        listener.onViewClick(x,y);
                    }
                }
                break;
        }
        return true;
    }
    //定义一个接口
    public interface  OnViewClickListener{
         void onViewClick(int x,int y);
    }
    private void textCenter(String string, TextPaint textPaint, Canvas canvas, Point point, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad){
        StaticLayout staticLayout = new StaticLayout(string,0,string.length(),textPaint,width-10, align,spacingmult,spacingadd,includepad);
        canvas.save();
        canvas.translate(-staticLayout.getWidth()/2+point.x,-staticLayout.getHeight()/2+point.y);
        staticLayout.draw(canvas);
        canvas.restore();
    }

}
