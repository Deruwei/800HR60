package com.hr.ui.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.hr.ui.R;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.CheckBean;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SaveFile;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.VerificationCodeEditText;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public static String getIMSI(Context context){
       return "";
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

    public static Drawable getMaskDrawable(Context context, int maskId) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(maskId);
        } else {
            drawable = context.getResources().getDrawable(maskId);
        }

        if (drawable == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }

        return drawable;
    }
    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //Log.i("版本",resultData);
        return resultData;
    }

    public static boolean checkPermissionIsAllRequest(String[] permissions,List<String> mPermissionList,Context mContext){
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         判断是否为空
         **/
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
           return true;
        }else{
            return false;
        }
    }

    public static boolean checkVersion(String version,String version1)
    {
        //Log.i("现在的时候",version+"----"+version1);
        int  first= Integer.parseInt(version.substring(0,version.indexOf(".")));
        int second= Integer.parseInt(version.substring(version.indexOf(".")+1,version.lastIndexOf(".")));
        int third= Integer.parseInt(version.substring(version.lastIndexOf(".")+1));
        int first1= Integer.parseInt(version1.substring(0,version1.indexOf(".")));
        int second1= Integer.parseInt(version1.substring(version1.indexOf(".")+1).substring(0,version1.indexOf(".")));
        int third1=0;
        if(version1.substring(version1.indexOf(".")+1).substring(version1.indexOf(".")+1).contains(".")) {
             third1 = Integer.parseInt(version1.substring(version1.indexOf(".") + 1).substring(version1.indexOf(".") + 1).substring(0, version1.indexOf(".")));
        }else{
            third1 = Integer.parseInt(version1.substring(version1.lastIndexOf(".")+1));
        }
        if(first==first1&&second==second1&&third==third1){
            return false;
        }else{
            if(first>first1) {
                return true;
            }else if(first==first1){
                if (second > second1) {
                        return true;
                }else if(second==second1){
                    if(third>third1) {
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    //判断集合是否为空
    public static boolean checkListIsNotEmpty(List<?> list){
        if(list!=null&&!"".equals(list)&&list.size()!=0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断设备 是否使用代理上网
     *
     * */
    public static boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);

    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        try {

            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 返回选择城市中左侧的数据
     * @param list 原始的list数组
     * @return
     */
    public static List<CityBean> findProvinceCityList(List<CityBean> list){
        List<CityBean> provinceList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().endsWith("00")) {
                provinceList.add(list.get(i));
            }
        }
        return provinceList;
    }
    public static List<CityBean> findProvinceCityList2(List<CityBean> list){
        List<CityBean> provinceList=new ArrayList<>();
        CityBean cityBean=new CityBean();
        cityBean.setId("");
        cityBean.setName("不限");
        cityBean.setCheck(false);
        provinceList.add(cityBean);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().endsWith("00")) {
                provinceList.add(list.get(i));
            }
        }
        return provinceList;
    }
    public static void setTvState(TextView tv,boolean b){
        if(b){
            tv.setBackgroundResource(R.drawable.tv_rectange_orange);
            tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        }else{
            tv.setBackgroundResource(R.drawable.tv_rectange_gray);
            tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.color_333));
        }
    }
    /**
     * 给以选择的直辖市标记状态
     * @param selectCityList 已选择的城市集合
     * @param originCityList  城市集合
     * @return
     */
    public static List<CityBean> setSelectCityInToOriginList(List<CityBean> selectCityList,List<CityBean> originCityList){
        for(int i=0;i<4;i++){
            for(int j=0;j<selectCityList.size();j++){
                if(selectCityList.get(j).getId().equals(originCityList.get(i).getId())){
                    originCityList.get(i).setCheck(true);
                    continue;
                }
            }
        }
        return originCityList;
    }
    public static List<CityBean> setSelectCityInToOriginList2(List<CityBean> selectCityList,List<CityBean> originCityList){
        for(int i=0;i<originCityList.size();i++){
            for(int j=0;j<selectCityList.size();j++){
                if(selectCityList.get(j).getId().equals(originCityList.get(i).getId())){
                    originCityList.get(i).setCheck(true);
                    continue;
                }
            }
        }
        return originCityList;
    }
    /**
     * 设置citybean集合全部为false
     * @param list
     * @param position 该位置开始
     */
    public static void setCityListIsAllFalse(List<CityBean> list,int position){
        for(int i=position;i<list.size();i++){
            list.get(i).setCheck(false);
        }
    }

    /**
     * 选择城市右边数据
     * @param cityBean
     * @param list
     * @return
     */
    public static List<CityBean> findCityList(CityBean cityBean,List<CityBean> list){
        List<CityBean> cityList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (cityBean.getId().substring(0, 2).equals(list.get(i).getId().substring(0, 2))) {
                list.get(i).setCheck(false);
                cityList.add(list.get(i));
            }
        }
        return cityList;
     }
     public static List<CityBean> removeList(CityBean cityBean,List<CityBean> selectCityList){
        for(int i=0;i<selectCityList.size();i++){
            if(cityBean.getId().equals(selectCityList.get(i).getId())){
                selectCityList.remove(selectCityList.get(i));
            }
        }
        return selectCityList;
     }


     public static int getRandomInt(List<CheckBean> list,int sum){
        Random random=new Random();
        int n=random.nextInt(sum);
       // Log.i("现在的数据",list.toString());
        if (checkList(list)){
        }else {
            while (checkNumIsCheck(n, list)) {
                n = random.nextInt(sum);
            }
        }
        for(int i=0;i<list.size();i++){
            if(n==list.get(i).getNum()){
                list.get(i).setCheck(true);
                break;
            }
        }
        return n;
     }
     public static boolean checkNumIsCheck(int num,List<CheckBean> list){
        for(int i=0;i<list.size();i++){
            if(num==list.get(i).getNum()&&list.get(i).isCheck){
                return true;
            }
        }
        return false;
     }
     public static boolean checkList(List<CheckBean> list){
        int num=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).isCheck==true){
                num++;
            }
        }
        if(num==list.size()){
            for(int i=0;i<list.size();i++){
                list.get(i).setCheck(false);
            }
            return true;
        }else{
            return false;
        }
     }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    public static String getDateMonthAndDay(String time){
            //Log.i("现在的数据", time + "");
            time = time.substring(time.indexOf("-") + 1);
            if (time.contains("-")) {
                String month = time.substring(0, time.indexOf("-"));
                int monthInt = Integer.valueOf(month);
                String day = time.substring(time.indexOf("-") + 1,time.indexOf("-")+3);
                int dayInt = Integer.valueOf(day);
                return monthInt + " - " + dayInt;
            } else {
                return "";
            }
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static  boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }



    public static String getIndustryNUumber(int industryID){
        switch (industryID) {
            case 11:   //建筑
                return "010-82197168";

            case 12:   //金融
                return "010-82197466";

            case 14:   //医药
                return "010-82197575";

            case 26:   //服装
                return "010-82197265";

            case 29:   //化工
                return "010-82197555";
            case 22:   //制造业
                return "010-82197466";
            default:
                return "010-62123388";
        }
    }
    /**
     * 四舍五入
     * @param d  数字
     * @param num 保存多少位小数点
     * @return
     */
    public static String formatDouble(double d,int num) {
        String s="";
        if(num==2) {
            DecimalFormat df = new DecimalFormat("#.00");
           s= df.format(d);
        }else if(num==1){
            DecimalFormat df = new DecimalFormat("#.0");
            s= df.format(d);
        }
        return s;
    }
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
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
                if(str.contains("0.0")) {
                    return Integer.parseInt(str.substring(0, str.indexOf(".")))/10 + "万";
                }else{
                    return str.substring(0, str.indexOf(".")) + "千";
                }
            }else {
                return str+ "千";
            }
        }else if(time.length()>4){
            double k=Double.parseDouble(time)/10000;
            String s=formatDouble(k,2);
            if(s.contains(".00")){
                s=s.substring(0,s.indexOf("."));
            }else if(s.contains(".")&&s.endsWith("0")){
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
    public static  boolean checkMedicinePositionClass2(CityBean cityBean){
        String string=cityBean.getId();
        //Log.i("当前的Id",string+"-----");
        if (string.contains("278") || string.contains("279")){
            if(string.equals("279106")||string.equals("279107")||string.equals("279108")){
                return false;
            }else {
                return true;
            }
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
            }else if("50000以上".equals(salaty)){
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
            }else if("50000以上".equals(salary)){
                return "500000";
            }
        }
        return "";
    }
    public static String getSalaryAround(String salary){
        switch (salary){
            case "0-500000":
                return "不限";
            case "0-2000":
                return "2000以下";
            case "50000-500000":
                return "50000以上";
                default:
                    return salary;

        }
    }
    public static void setTextViewChangeIconRightChange(final TextView textView, final ImageView iv) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    iv.setImageResource(R.mipmap.right_arrow);
                } else {
                    iv.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public static void setIM(VerificationCodeEditText et,Context context){
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        InputMethodManager im= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(et,0);
    }
    public static void setIM2(EditText et,Context context){
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        InputMethodManager im= (InputMethodManager)context .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(et,0);
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
    public  static void getConnect(){
        //Log.i("你好","------");
        ToastUitl.showShort("网络出了小差");
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<BaseBean> Object = RxService.getConnect(EncryptUtils.encrypParams(ApiParameter.getConnect(HRApplication.getAppContext())));
        Subscriber mSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getError_code()==0){
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
                    sUtils.setStringValue(Constants.SESSION_KEY,baseBean.getSession_key());
                    Constants.SESSION_KEY=baseBean.getSession_key();
                    Rc4Md5Utils.secret_key = Constants.PRE_SECRET_KRY + baseBean.getSecret_key();
                    Constants.SVR_API_VER = baseBean.getSvr_api_ver();
                    getArray();
                    LoginUser();
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private static void getArray(){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getArrayInfo();
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                ArrayInfoBean arrayInfoBean=null;
                try {
                    String s=responseBody.string().toString();
                    arrayInfoBean=new Gson().fromJson(s,ArrayInfoBean.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
                if(!sUtils.getStringValue(Constants.CITY_VER,"").equals(arrayInfoBean.getCity().getVer())){
                    getArrayData("client/file/array/city.php","city.txt");
                }
                if(!sUtils.getStringValue(Constants.JOB_VER,"").equals(arrayInfoBean.getJob().getVer())){
                    getArrayData("client/file/array/job.php","job.txt");
                }
                if(!sUtils.getStringValue(Constants.LINGYU_VER,"").equals(arrayInfoBean.getLingyu().getVer())){
                    getArrayData("client/file/array/lingyu.php","lingyu.txt");
                }
                sUtils.setStringValue(Constants.CITY_VER,arrayInfoBean.getCity().getVer());
                sUtils.setStringValue(Constants.JOB_VER,arrayInfoBean.getJob().getVer());
                sUtils.setStringValue(Constants.LINGYU_VER,arrayInfoBean.getLingyu().getVer());
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private static void getArrayData(String path, final String fileName){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getLingYuArray(path);
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String s= responseBody.string().toString();
                    SaveFile.updateCJ(HRApplication.getAppContext(),fileName,s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private  static  void  LoginUser(){
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        int isAutoLogin = sUtils.getIntValue(Constants.ISAUTOLOGIN, 0);
        int autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
        LoginBean loginBean=null;
        if (autoLoginType != 5) {
            try{
                loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
            }catch(Exception e){
               e.printStackTrace();
            }
            // System.out.println("auto"+loginBean.toString());
            //isAutoLogin是否是自动登录   0不是
            if (isAutoLogin == 0) {
            } else {
                if(loginBean==null||"".equals(loginBean)){
                }else {
                    if (autoLoginType == 0) {
                        getAutoPhoneLogin(loginBean,1);
                        //mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 1, false);
                    } else if (autoLoginType == 1) {
                        getAutoPhoneLogin(loginBean,2);
                       // mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 2, false);
                    } else {
                        getAutoThirdBindingLogin(loginBean);
                       // mPresenter.getThirdBindingLogin(loginBean, false);
                    }
                }
            }
        }
    }
    private static void getAutoPhoneLogin(LoginBean loginBean,int type){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<RegisterBean> Object = RxService.getLogin(EncryptUtils.encrypParams(ApiParameter.getLogin(loginBean.getName(), loginBean.getPassword(), 1)));
        Subscriber mSubscriber = new Subscriber<RegisterBean>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(RegisterBean registerBean) {
             /*   try {
                   *//* String s= responseBody.string().toString();
                    SaveFile.updateCJ(HRApplication.getAppContext(),fileName,s);*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }

    private static void getAutoThirdBindingLogin(LoginBean loginBean){
        ApiService RxService = Api.getDefault(HostType.HR);
        ThirdLoginBean thirdLoginBean=new ThirdLoginBean();
        thirdLoginBean.setSUId(loginBean.getThirdPartSUid());
        thirdLoginBean.setUId(loginBean.getThirdPartUid());
        thirdLoginBean.setType(loginBean.getThirdPartLoginType());
        Observable<ResponseBody> Object = RxService.getThirdPartLogin(EncryptUtils.encrypParams(ApiParameter.getThirdPartLogin(thirdLoginBean)));
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                /*   try {
                 *//* String s= responseBody.string().toString();
                    SaveFile.updateCJ(HRApplication.getAppContext(),fileName,s);*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
}
