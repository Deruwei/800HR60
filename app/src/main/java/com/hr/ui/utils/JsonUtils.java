package com.hr.ui.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.ui.bean.ResumeBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdr on 2017/12/12.
 */

public class JsonUtils {
    private static JsonUtils instance;
    /**
     * 基本信息
     */
    private ResumeBean.ResumeInfoBean.BaseInfoBean resumeBaseInfo;
    /**
     * 简历头信息
     */
    private ResumeBean.ResumeInfoBean.TitleInfoBean resumeTitle;
    /**
     * 求职意向
     */
    private ResumeBean.ResumeInfoBean.OrderInfoBean resumeOrder;
    /**
     * 自我评价
     */
    private ResumeBean.ResumeInfoBean.AssessInfoBean resumeAssessInfo;
    /**
     * 工作经验
     */
    private List<ResumeBean.ResumeInfoBean.ExperienceListBean> resumeExperiences;
    /**
     * 教育背景
     */
    private List<ResumeBean.ResumeInfoBean.EducationListBean> resumeEducations;
    /**
     * 培训经历
     */
    private List<ResumeBean.ResumeInfoBean.PlantListBean> resumePlants;
    /**
     * 语言能力
     */
    private List<ResumeBean.ResumeInfoBean.LanguageListBean> resumeLanguageLevels;
    /**
     * 专业技能
     */
    private List<ResumeBean.ResumeInfoBean.SkillListBean> resumeSkills;
    /**
     * 项目经验
     */
    public static JsonUtils getInstance(){
        if(instance==null){
            instance=new JsonUtils();
        }
        return instance;
    }
    private List<ResumeBean.ResumeInfoBean.ProjectListBean> resumeProjects;
    public  ResumeBean fixJson(String json){
        JSONObject jsonObject= null;
        int error_code=0;
        try {
            jsonObject = new JSONObject(json);
            JSONObject jsonObject1=jsonObject.getJSONObject("resume_info");
           error_code=jsonObject.getInt("error_code");
            resumeBaseInfo =new Gson().fromJson(jsonObject1.getString("base_info"),ResumeBean.ResumeInfoBean.BaseInfoBean.class);
            resumeTitle = new Gson().fromJson(jsonObject1.getString("title_info"),ResumeBean.ResumeInfoBean.TitleInfoBean.class);
            resumeOrder = new Gson().fromJson(jsonObject1.getString("order_info"),ResumeBean.ResumeInfoBean.OrderInfoBean.class);
            Type type1 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.AssessInfoBean>>() {}.getType();
            Type type2 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.ExperienceListBean>>() {}.getType();
            Type type3 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.EducationListBean>>() {}.getType();
            Type type4 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.PlantListBean>>() {}.getType();
            Type type5 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.LanguageListBean>>() {}.getType();
            Type type6 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.SkillListBean>>() {}.getType();
            Type type7 = new TypeToken<ArrayList<ResumeBean.ResumeInfoBean.ProjectListBean>>() {}.getType();
            //Log.i("个人简介",jsonObject1.getString("assess_info"));
            if("[]".equals(jsonObject1.getString("assess_info"))||jsonObject1.getString("assess_info")==null) {
            }else{
                resumeAssessInfo = new Gson().fromJson(jsonObject1.getString("assess_info"), ResumeBean.ResumeInfoBean.AssessInfoBean.class);
            }
            resumeExperiences = new Gson().fromJson(jsonObject1.getString("experience_list"),type2);
            resumeEducations = new Gson().fromJson(jsonObject1.getString("education_list"),type3);
            resumePlants = new Gson().fromJson(jsonObject1.getString("plant_list"),type4);
            resumeLanguageLevels = new Gson().fromJson(jsonObject1.getString("language_list"),type5);
            resumeSkills = new Gson().fromJson(jsonObject1.getString("skill_list"),type6);
            resumeProjects = new Gson().fromJson(jsonObject1.getString("project_list"),type7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResumeBean resumeBean=new ResumeBean();
        resumeBean.setError_code(error_code);
        ResumeBean.ResumeInfoBean resumeInfoBean=new ResumeBean.ResumeInfoBean();
        resumeInfoBean.setProject_list(resumeProjects);
        resumeInfoBean.setBase_info(resumeBaseInfo);
        resumeInfoBean.setSkill_list(resumeSkills);
        resumeInfoBean.setLanguage_list(resumeLanguageLevels);
        resumeInfoBean.setPlant_list(resumePlants);
        resumeInfoBean.setAssess_info(resumeAssessInfo);
        resumeInfoBean.setEducation_list(resumeEducations);
        resumeInfoBean.setOrder_info(resumeOrder);
        resumeInfoBean.setTitle_info(resumeTitle);
        resumeInfoBean.setExperience_list(resumeExperiences);
        resumeBean.setResume_info(resumeInfoBean);
        return  resumeBean;
    }
}
