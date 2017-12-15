package com.hr.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hr.ui.app.AppManager;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.db.ResumeDataUtils;
import com.hr.ui.ui.login.activity.UserLoginActivity;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.ui.login.presenter.RegisterPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.activity.MultipleResumeActivity;
import com.hr.ui.ui.main.activity.RobotActivity;

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
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            RobotActivity.startAction(activity,userId);
        }else{
            if(multipleResumeBean.getResume_list().size()==1){
                mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
            }else{
                ResumeDataUtils.deleteAll();
                List<MultipleResumeBean.ResumeListBean> resumeListBeanList=multipleResumeBean.getResume_list();
                //Log.i("niham,s,s,",resumeListBeanList.toString());
                for(int j=0;j<resumeListBeanList.size();j++){
                    ResumeData resumeData=new ResumeData();
                    resumeData.setResumeId(resumeListBeanList.get(j).getResume_id());
                    resumeData.setTitle(resumeListBeanList.get(j).getTitle());
                    resumeData.setComplete(resumeListBeanList.get(j).getFill_scale());
                    resumeData.setImageId(imageIds[j]);
                    ResumeDataUtils.insertResumeData(resumeData);
                }
                MultipleResumeActivity.startAction(activity,userId);
            }
        }
    }
    public void judgeResumeIsComplete(ResumeBean resumeBean, Activity activity, ArrayList<String> titles){
        titles=new ArrayList<>();
        if(resumeBean.getResume_info().getBase_info()==null||"".equals(resumeBean.getResume_info().getBase_info())){
            titles.add("基本信息");
        }
        if(resumeBean.getResume_info().getOrder_info()==null||"".equals(resumeBean.getResume_info().getOrder_info())){
            titles.add("求职意向");
        }
        if(resumeBean.getResume_info().getEducation_list()==null||"".equals(resumeBean.getResume_info().getEducation_list())||resumeBean.getResume_info().getEducation_list().size()==0){
            titles.add("教育背景");
        }
        if(resumeBean.getResume_info().getExperience_list()==null||"".equals(resumeBean.getResume_info().getEducation_list())||resumeBean.getResume_info().getExperience_list().size()==0){
            titles.add("工作经验");
        }
        if(titles!=null&&!"".equals(titles)&&titles.size()!=0) {
            RobotActivity.startAction(activity, titles);
        }else{
            MainActivity.startAction(activity,Integer.parseInt(resumeBean.getResume_info().getAssess_info().getUser_id()));
            AppManager.getAppManager().finishAllActivity();
        }
    }
    public  void judgeResumeMultipleOrOne2(Activity activity, MultipleResumeBean multipleResumeBean, int userId, int[] imageIds, RegisterPresenter mPresenter){
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            RobotActivity.startAction(activity,userId);
        }else{
            if(multipleResumeBean.getResume_list().size()==1){
                mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
            }else{
                ResumeDataUtils.deleteAll();
                List<MultipleResumeBean.ResumeListBean> resumeListBeanList=multipleResumeBean.getResume_list();
                //Log.i("niham,s,s,",resumeListBeanList.toString());
                for(int j=0;j<resumeListBeanList.size();j++){
                    ResumeData resumeData=new ResumeData();
                    resumeData.setResumeId(resumeListBeanList.get(j).getResume_id());
                    resumeData.setTitle(resumeListBeanList.get(j).getTitle());
                    resumeData.setComplete(resumeListBeanList.get(j).getFill_scale());
                    resumeData.setImageId(imageIds[j]);
                    ResumeDataUtils.insertResumeData(resumeData);
                }
                MultipleResumeActivity.startAction(activity,userId);
            }
        }
    }
}