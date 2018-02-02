package com.hr.ui.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wdr on 2017/9/6.
 */

public class MyStartAndEndTimeCustomDatePicker {
    /**
     * 定义结果回调接口
     */
    public interface ResultHandler {
        void handle(String startTime,String endTime);
    }

    private ResultHandler handler;
    private Context context;
    private boolean canAccess = false;

    private Dialog datePickerDialog;
    private DatePickerView startYear_pv,startMonth_pv,endYear_pv,endMonth_pv;
    private String textValue;
    private ArrayList<String> year, month,eYear,eMonth;
    private int startYear=1960, startMonth=1,endYear, endMonth=12;
    private int  selectStartYear,selectStartMonth,selectEndYear,selectEndMonth;
    private TextView tv_cancle, tv_select;
    private SharedPreferencesUtils sUtils;
    private String birthYear;
    private String startTime,endTime,selectEndYears;
    private int NONOWTYPE=1;
    private int type;

    public MyStartAndEndTimeCustomDatePicker(Context context, ResultHandler resultHandler,int type) {
            canAccess = true;
            this.context = context;
            this.handler = resultHandler;
            this.type=type;
            sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
            birthYear = sUtils.getStringValue(Constants.BIRTHYEAR, "2018");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");//格式为 2013年9月3日 14:44
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String currentDate = formatter.format(curDate);
            endYear = Integer.valueOf(currentDate);
            if(type==NONOWTYPE){
                startYear = Integer.valueOf(birthYear) + 1;
            }else {
                startYear = Integer.valueOf(birthYear) + 18;
            }
            initDialog();
            initView();
    }

    private void initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = new Dialog(context, R.style.mytime_dialog);
            datePickerDialog.setCancelable(false);
            datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePickerDialog.setContentView(R.layout.layout_selectstartandendtime);
            Window window = datePickerDialog.getWindow();
            datePickerDialog.setCanceledOnTouchOutside(true);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        startYear_pv = (DatePickerView) datePickerDialog.findViewById(R.id.dpv_startYear);
        startMonth_pv = (DatePickerView) datePickerDialog.findViewById(R.id.dpv_startMonth);
        endYear_pv = (DatePickerView) datePickerDialog.findViewById(R.id.dpv_endYear);
        endMonth_pv= (DatePickerView) datePickerDialog.findViewById(R.id.dpv_endMonth);
        tv_select = (TextView) datePickerDialog.findViewById(R.id.tv_submit);
        tv_cancle=(TextView)datePickerDialog.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(textValue)||textValue==null){
                        Format f = new DecimalFormat("00");
                        if("至今".equals(selectEndYears)){
                            startTime=f.format(selectStartYear) +"-"+ f.format(selectStartMonth);
                            endTime=selectEndYears;
                        }else {
                            if (selectEndYear < selectStartYear || (selectEndYear == selectStartYear && selectStartMonth > selectEndMonth)) {
                                ToastUitl.showShort("结束日期应大于开始日期");
                                return;
                            }
                            startTime=f.format(selectStartYear) +"-"+ f.format(selectStartMonth);
                            endTime=f.format(selectEndYear)+"-"+f.format(selectEndMonth);
                        }
                        handler.handle(startTime,endTime);
                }else {
                    handler.handle("","");

                }
                datePickerDialog.dismiss();
            }
        });
    }

    private void initTimer() {
        initArrayList();
        if(type!=NONOWTYPE) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
                eYear.add(String.valueOf(i));
            }
            eYear.add("至今");
        }else{
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startYear; i <= 2037; i++) {
                eYear.add(String.valueOf(i));
            }
        }
        for (int i = startMonth; i <= endMonth; i++) {
            month.add(String.valueOf(i));
            eMonth.add(String.valueOf(i));
        }
        loadComponent();
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (eYear == null) eYear = new ArrayList<>();
        if (eMonth == null) eMonth = new ArrayList<>();
        year.clear();
        month.clear();
        eYear.clear();
        eMonth.clear();
    }

    private void loadComponent() {
        startYear_pv.setData(year);
        startMonth_pv.setData(month);
        endYear_pv.setData(eYear);
        endMonth_pv.setData(eMonth);
        startYear_pv.setSelected(0);
        startMonth_pv.setSelected(0);
        endYear_pv.setSelected(0);
        endMonth_pv.setSelected(0);
        executeScroll();
    }

    private void addListener() {
        startYear_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectStartYear=Integer.valueOf(text);
            }
        });

        startMonth_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
              selectStartMonth=Integer.valueOf(text);
            }
        });

        endYear_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                if(!"至今".equals(text)) {
                    selectEndYears="";
                    endMonth_pv.setVisibility(View.VISIBLE);
                    selectEndYear = Integer.valueOf(text);
                }else{
                    selectEndYears="至今";
                    endMonth_pv.setVisibility(View.GONE);
                }
            }
        });
        endMonth_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

                selectEndMonth=Integer.valueOf(text);
            }
        });
    }

    private void monthChange() {
        month.clear();
        for (int i = startMonth; i <= endMonth; i++) {
            month.add(i+"");
        }
        startMonth_pv.setData(month);
        startMonth_pv.setSelected(selectStartMonth+"");

        executeAnimator(startMonth_pv);

    }
    private void monthEndChange() {
        eMonth.clear();
        for (int i = startMonth; i <= endMonth; i++) {
            eMonth.add(i+"");
        }
        endMonth_pv.setData(eMonth);
        endMonth_pv.setSelected(selectEndMonth+"");

        executeAnimator(endMonth_pv);

    }



    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }

    private void executeScroll() {
        startMonth_pv.setCanScroll(month.size() > 1);
        startYear_pv.setCanScroll(year.size() > 1);
        endYear_pv.setCanScroll(eYear.size() > 1);
        endMonth_pv.setCanScroll(eMonth.size() > 1);
    }


    public void show(String startTime,String endTime) {
        if (canAccess) {
            if (!"".equals(startTime)&&!"".equals(endTime)&&startTime!=null&&endTime!=null) {
                selectStartYear=Integer.valueOf(startTime.substring(0,startTime.indexOf("-")));
                selectStartMonth=Integer.valueOf(startTime.substring(startTime.indexOf("-")+1));
                if(!"至今".equals(endTime)) {
                    selectEndYear = Integer.valueOf(endTime.substring(0, endTime.indexOf("-")));
                    selectEndMonth = Integer.valueOf(endTime.substring(endTime.lastIndexOf("-") + 1));
                }else{
                    if(type==1){
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");//格式为 2013年9月3日 14:44
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        endTime= formatter.format(curDate);
                        selectEndYear = Integer.valueOf(endTime.substring(0, endTime.indexOf("-")));
                        selectEndMonth = Integer.valueOf(endTime.substring(endTime.lastIndexOf("-") + 1));
                    }else {
                        selectEndYears = endTime;
                    }
                }
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");//格式为 2013年9月3日 14:44
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String time= formatter.format(curDate);
                selectStartYear=Integer.valueOf(time.substring(0,time.indexOf("-")));
                selectEndYear=Integer.valueOf(time.substring(0,time.indexOf("-")));
                selectStartMonth=Integer.valueOf(time.substring(time.indexOf("-")+1,time.lastIndexOf("")));
                selectEndMonth=Integer.valueOf(time.substring(time.indexOf("-")+1,time.lastIndexOf("")));
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日 HH:mm");//格式为 2013年9月3日 14:44
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String  currentDate = formatter.format(curDate);
            // Log.i("当前选择的",selectDay+"---"+selectMonth+"---"+selectYear);
            canAccess = true;
            initTimer();
            addListener();
            setSelectedTime();
            datePickerDialog.show();
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setIsLoop(boolean isLoop) {
        if (canAccess) {
            this.startYear_pv.setIsLoop(isLoop);
            this.startMonth_pv.setIsLoop(isLoop);
            this.endMonth_pv.setIsLoop(isLoop);
            this.endYear_pv.setIsLoop(isLoop);
        }
    }

    /**
     * 设置日期控件默认选中的时间
     */
    public void setSelectedTime() {
        if (canAccess) {
                startYear_pv.setSelected(selectStartYear + "");
                startMonth_pv.setSelected(selectStartMonth + "");
                if("至今".equals(selectEndYears)){
                    endYear_pv.setSelected(selectEndYears);
                    endMonth_pv.setVisibility(View.GONE);
                }else {
                    endYear_pv.setSelected(selectEndYear + "");
                    endMonth_pv.setSelected(selectEndMonth + "");
                }
                //Log.i("当前选择的2",selectStartYear+"---"+selectStartMonth+"---"+selectEndYear+"----"+selectEndMonth);
                executeAnimator(startMonth_pv);
                executeAnimator(endMonth_pv);
                executeScroll();
        }
    }

    /**
     * 验证字符串是否是一个合法的日期格式
     */
    private boolean isValidDate(String date, String template) {
        boolean convertSuccess = true;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
