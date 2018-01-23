package com.hr.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.ResumeDataUtils;
import com.hr.ui.ui.login.activity.UserLoginActivity;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.ui.login.presenter.RegisterPresenter;
import com.hr.ui.ui.main.activity.EducationActivity;
import com.hr.ui.ui.main.activity.JobOrderActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.activity.MultipleResumeActivity;
import com.hr.ui.ui.main.activity.PersonalInformationActivity;
import com.hr.ui.ui.main.activity.RobotActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.ui.main.activity.WorkExpActivity;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdr on 2017/12/12.
 */

public class ToolUtils {
    private static ToolUtils instance;
    public static ToolUtils getInstance(){
        if(instance==null){
            instance=new ToolUtils();
        }
        return instance;
    }
    public  void judgeResumeMultipleOrOne(Activity activity, MultipleResumeBean multipleResumeBean, int userId, int[] imageIds, LoginPresenter mPresenter){
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        sUtils.setStringValue(Constants.USERPHONE,multipleResumeBean.getBase_info().get(0).getYdphone());
        //System.out.println(""+multipleResumeBean.getBase_info().get(0).getYdphone()+"---"+multipleResumeBean.getBase_info().get(0).getTelephone());
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            RobotActivity.startAction(activity,userId);
            sUtils.setIntValue(Constants.RESUME_STOPTYPE, 0);
            sUtils.setIntValue(Constants.RESUME_STARTTYPE,0);
        }else{
            mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
        }
    }
    public void judgeResumeIsComplete(ResumeBean resumeBean, Activity activity, ArrayList<String> titles){
        titles=new ArrayList<>();
        SharedPreferencesUtils sharedPreferencesUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        if(!"".equals(resumeBean.getResume_info().getBase_info().get(0).getPic_filekey())&&resumeBean.getResume_info().getBase_info().get(0).getPic_filekey()!=null){
            sharedPreferencesUtils.setStringValue(Constants.PERSONIMAGE,resumeBean.getResume_info().getBase_info().get(0).getPic_filekey());
        }else{
            sharedPreferencesUtils.setStringValue(Constants.PERSONIMAGE,"");
        }
        if(!"".equals(resumeBean.getResume_info().getTitle_info().get(0).getResume_type())&&resumeBean.getResume_info().getTitle_info().get(0).getResume_type()!=null) {
            sharedPreferencesUtils.setStringValue(Constants.RESUME_TYPE, resumeBean.getResume_info().getTitle_info().get(0).getResume_type());
        }
        if(resumeBean.getResume_info().getBase_info().get(0).getName()==null||"".equals(resumeBean.getResume_info().getBase_info().get(0).getName())){
            titles.add("基本信息");
        }
        System.out.println(resumeBean.toString());
        if(resumeBean.getResume_info().getEducation_list()==null||"".equals(resumeBean.getResume_info().getEducation_list())||"[]".equals(resumeBean.getResume_info().getEducation_list())||resumeBean.getResume_info().getEducation_list().size()==0){
            titles.add("教育背景");
        }
        if(resumeBean.getResume_info().getExperience_list()==null||"".equals(resumeBean.getResume_info().getExperience_list())||"[]".equals(resumeBean.getResume_info().getExperience_list())||resumeBean.getResume_info().getExperience_list().size()==0){
            if("2".equals(sharedPreferencesUtils.getStringValue(Constants.RESUME_TYPE,"1"))) {
                if(Integer.parseInt(resumeBean.getResume_info().getTitle_info().get(0).getFill_scale())<60) {
                    titles.add("实习经历");
                }
            }else {
                titles.add("工作经验");
            }
        }
        if(resumeBean.getResume_info().getOrder_info()==null||resumeBean.getResume_info().getOrder_info().size()==0||"[]".equals(resumeBean.getResume_info().getOrder_info())||"".equals(resumeBean.getResume_info().getOrder_info())){
            titles.add("求职意向");
        }else{
           if(resumeBean.getResume_info().getOrder_info().get(0).getIndustry()!=null&&!"".equals(resumeBean.getResume_info().getOrder_info().get(0).getIndustry())){
               sharedPreferencesUtils.setStringValue(Constants.INDUSTRY_ID,resumeBean.getResume_info().getOrder_info().get(0).getIndustry());
           }
        }
        if(titles!=null&&!"".equals(titles)&&titles.size()!=0) {
            if ("基本信息".equals(titles.get(0))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STARTTYPE,1);
            }
            if ("教育背景".equals(titles.get(0))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STARTTYPE,2);
            }
            if ("工作经验".equals(titles.get(0))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STARTTYPE,3);
            }
            if ("实习经历".equals(titles.get(0))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STARTTYPE,3);
            }
            if ("求职意向".equals(titles.get(0))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STARTTYPE,4);
            }
            if ("基本信息".equals(titles.get(titles.size()-1))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STOPTYPE,1);
            }
            if ("教育背景".equals(titles.get(titles.size()-1))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STOPTYPE,2);
            }
            if ("工作经验".equals(titles.get(titles.size()-1))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STOPTYPE,3);
            }
            if ("实习经历".equals(titles.get(titles.size()-1))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STOPTYPE,3);
            }
            if ("求职意向".equals(titles.get(titles.size()-1))){
                sharedPreferencesUtils.setIntValue(Constants.RESUME_STOPTYPE,4);
            }
        }
        if(titles!=null&&!"".equals(titles)&&titles.size()!=0) {
            RobotActivity.startAction(activity, titles);
        }else{
           MainActivity.startAction(activity,Integer.parseInt(resumeBean.getResume_info().getTitle_info().get(0).getUser_id()));
            AppManager.getAppManager().finishAllActivity();
           // PersonalInformationActivity.startAction(activity);
            //WorkExpActivity.startAction(activity);
            //JobOrderActivity.startAction(activity);
            //EducationActivity.startAction(activity);
        }
    }
    public  void judgeResumeMultipleOrOne2(Activity activity, MultipleResumeBean multipleResumeBean, int userId, int[] imageIds, RegisterPresenter mPresenter){
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            RobotActivity.startAction(activity,userId);
            SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
            sUtils.setIntValue(Constants.RESUME_STOPTYPE, 0);
            sUtils.setIntValue(Constants.RESUME_STARTTYPE,0);
        }else{
                mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
        }
    }
    public  void judgeResumeMultipleOrOne3(Activity activity, MultipleResumeBean multipleResumeBean, int userId, int[] imageIds, SplashPresenter mPresenter){
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
            sUtils.setIntValue(Constants.RESUME_STOPTYPE, 0);
            sUtils.setIntValue(Constants.RESUME_STARTTYPE,0);
            RobotActivity.startAction(activity,userId);
        }else{
            mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
        }
    }
}
