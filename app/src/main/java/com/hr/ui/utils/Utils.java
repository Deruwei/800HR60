package com.hr.ui.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hr.ui.R;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.datautils.FromStringToArrayList;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wdr on 2018/1/12.
 */

public class Utils {

    private Uri photoUri;
    //拍照的请求码
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    //选择图片的返回码
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    //图片的路径
    private String picPath = "";
    private static ProgressDialog pd;
    private String resultStr = "";	//
    private String imgUrl = "";
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
            //Log.i("现在的数据", time + "");
        time = time.substring(time.indexOf("-") + 1);
        if(time.contains("-")) {
            String month = time.substring(0, time.indexOf("-"));
            int monthInt = Integer.valueOf(month);
            String day = time.substring(time.indexOf("-") + 1);
            int dayInt = Integer.valueOf(day);
            return monthInt + " - " + dayInt;
        }else{
            return "";
        }
    }

    /**
     * 四舍五入
     * @param d  数字
     * @param num 保存多少位小数点
     * @return
     */
    public static String formatDouble(double d,int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    public static String getDateMonthAndDay2(String time){
        //Log.i("现在的数据",time);
        time=time.substring(time.indexOf("-")+1);
        String month=time.substring(0,time.indexOf("-"));
        int monthInt=Integer.valueOf(month);
        String day=time.substring(time.indexOf("-")+1);
        int dayInt=Integer.valueOf(day);
        return monthInt+"月"+dayInt+"日";
    }
    public static String getSalary(String date){
        //Log.i("时间",date+"----");
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
           double i=Double.parseDouble(time)/1000;
            String str=formatDouble(i,1);
            if(str.contains(".0")) {
                return str.substring(0,str.indexOf(".")) + "千";
            }else {
                return str+ "千";
            }
        }else if(time.length()>4){
            double k=Double.parseDouble(time)/10000;
            String s=formatDouble(k,2);
            if(s.contains(".00")){
                s=s.substring(0,s.indexOf("."));
            }else if(s.endsWith("0")){
               s=s.substring(0,s.lastIndexOf("0"));
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
                .fitCenter()
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
                .fitCenter()
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        iv.setImageBitmap(resource);
                    }
                });
    }
    public static boolean checkMedicinePositionClass(CityBean cityBean){
        String string=cityBean.getId();
        if ((string.contains("263") || string.contains("264")
                || string.contains("265") || string.contains("266")
                || string.contains("267") || string.contains("268"))) {
          return true;
        } else {
            return false;
        }
    }
    public static String getPositionClassName(String id){
        String name="";
        List<CityBean> positionClassList= FromStringToArrayList.getInstance().getPositionClassList();
        for(int i=0;i<positionClassList.size();i++){
            if(id.equals(positionClassList.get(i).getId())){
                name=positionClassList.get(i).getName();
                break;
            }
        }
        return name;
    }
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static String getUserInfo(ThirdLoginBean thirdLoginBean){
        if(thirdLoginBean!=null){
            return "{\"name\":\""+thirdLoginBean.getName()+"\",\"birthday\":\""+thirdLoginBean.getBirthDay()+"\",\"nickname\":\""+thirdLoginBean.getName()+
            "\",\"tinyurl\":\""+thirdLoginBean.getPhoto()+"\",\"sex\":\""+thirdLoginBean.getGender()+"\"}";
        }
        return"";
    }
    public static void setListCheckFalse(List<CityBean> list){
        for(int i=0;i<list.size();i++){
            list.get(i).setCheck(false);
        }
    }
    public static boolean checkSalary(String salary){
        if(salary.contains("-")){
            return true;
        }else{
            return false;
        }
    }
    public static String getLeftSalary(String salaty){
        if(checkSalary(salaty)==true){
            return  salaty.substring(0,salaty.indexOf("-"));
        }else{
            if("不限".equals(salaty)){
                return "0";
            }else if("2000以下".equals(salaty)){
                return "0";
            }else if("50000".equals(salaty)){
                return "50000";
            }
        }
        return "";
    }
    public static String getRightSalary(String salary){
        if(checkSalary(salary)==true){
            return  salary.substring(salary.indexOf("-")+1);
        }else{
            if("不限".equals(salary)){
                return "500000";
            }else if("2000以下".equals(salary)){
                return "2000";
            }else if("50000".equals(salary)){
                return "500000";
            }
        }
        return "";
    }
    public static void setEditViewTextChangeAndFocus(final EditText et, final ImageView iv){
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    iv.setVisibility(View.GONE);
                }else{
                    if(et.getText().toString()!=null&&!"".equals(et.getText().toString())){
                        iv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    iv.setVisibility(View.VISIBLE);
                }else{
                    iv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
