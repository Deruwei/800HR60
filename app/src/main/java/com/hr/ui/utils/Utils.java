package com.hr.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hr.ui.R;
import com.hr.ui.ui.message.activity.WebActivity;

import java.lang.reflect.Field;
import java.sql.Time;

/**
 * Created by wdr on 2018/1/12.
 */

public class Utils {
    public static int dp2px(Context context, float dp)
    {
        return (int ) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( outMetrics);
        return outMetrics .widthPixels ;
    }
    public static String getDateMonthAndDay(String time){
        //Log.i("现在的数据",time);
        time=time.substring(time.indexOf("-")+1);
        String month=time.substring(0,time.indexOf("-"));
        String day=time.substring(time.indexOf("-")+1);
        return month+" - "+day;
    }
    public static String getSalary(String date){
      /*  Log.i("时间",date+"----");*/
        if(date!=null&&!"".equals(date) ){
            if ("面议".equals(date)) {
                return date;
            } else {
                if (date.indexOf("-") == -1) {
                    return salaryFromat(date);
                } else {
                    String salaryLeft = date.substring(0, date.indexOf("-") - 1);
                    String salaryRight = date.substring(date.indexOf("-") + 2);
                    return salaryFromat(salaryLeft) + " - " + salaryFromat(salaryRight);
                }
            }
        }else{
            return "";
        }

    }
    public  static String salaryFromat(String time){

        if(time.length()==4) {
            int salary =Integer.parseInt(time);
            int i=salary/1000;
            return i+"千";
        }else if(time.length()>4){
            String s="";
            int salary =Integer.parseInt(time);
            if(salary%1000==0){
                int i=salary/10000;
                s=i+"";
            }else {
                int i = salary / 1000;
                double j = i / 10.0;
                s=j+"";
            }
            return s+"万";
        }
        return time;
    }
    public static void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    public static String getGuidanceTitle(int position){
        if(position==0){
            return "求职宝典";
        }else if(position==1){
            return "简历指南";
        }else if(position==2){
            return "职业诊断";
        }else if(position==3){
            return "职场八卦";
        }else if(position==4){
            return "创业指南";
        }else if(position==5){
            return "面试秘籍";
        }else if(position==6){
            return "劳动模范";
        }else if(position==7){
            return "人际关系";
        }else if(position==8) {
            return "薪酬行情";
        }
        return "";
    }
    public static SpannableStringBuilder setTextLinkOpenByWebView(final Context context, String answerString) {
        if (!TextUtils.isEmpty(answerString)) {
            Spanned htmlString = Html.fromHtml(answerString);
            if (htmlString instanceof SpannableStringBuilder) {
                SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) htmlString;
                // 取得与a标签相关的Span
                Object[] objs = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);
                if (null != objs && objs.length != 0) {
                    for (Object obj : objs) {
                        int start = spannableStringBuilder.getSpanStart(obj);
                        int end = spannableStringBuilder.getSpanEnd(obj);
                        if (obj instanceof URLSpan) {
                            //先移除这个Span，再新添加一个自己实现的Span。
                            URLSpan span = (URLSpan) obj;
                            final String url = span.getURL();
                            spannableStringBuilder.removeSpan(obj);
                            spannableStringBuilder.setSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    WebActivity.startAction((Activity) context, url);
                                }
                            }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
                return spannableStringBuilder;
            }
        }
        return new SpannableStringBuilder(answerString);
    }
    public static void setImageResource(Context context, final ImageView iv, String url){
        Glide.with(context).load(url).asBitmap()
                .animate(R.anim.crop_image_fade_anim)
                .placeholder(R.mipmap.defaultcompany)
                .centerCrop()
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        iv.setImageBitmap(resource);
                    }
                });
    }
    public static void setImageResourceDefault(Context context, final ImageView iv){
        Glide.with(context).load(R.mipmap.defaultcompany).asBitmap()
                .animate(R.anim.crop_image_fade_anim)
                .placeholder(R.mipmap.defaultcompany)
                .centerCrop()
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        iv.setImageBitmap(resource);
                    }
                });
    }
}
