package com.hr.ui.api;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hr.ui.BuildConfig;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CompanyRegisterBean;
import com.hr.ui.bean.EducationData;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.LanguageLevelData;
import com.hr.ui.bean.NoticeData;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.ProfessionSkillData;
import com.hr.ui.bean.ProjectExpData;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.bean.TrainExpData;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.AndroidUtils;
import com.hr.ui.utils.NetworkMng;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.UUID;

import cn.sharesdk.framework.Platform;

/**
 * Created by wdr on 2017/11/21.
 */

public class ApiParameter {
    public static final String TAG = ApiParameter.class.getSimpleName();

    /**
     * 连接服务器
     * @param context
     * @return
     */
    public static HashMap<String, String> getConnect(Context context) {
        SharedPreferencesUtils sUtils = new SharedPreferencesUtils(context);
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("method", "user.connect");
        requestMap.put("api_ver", Constants.API_VER);
        requestMap.put("client_ver", BuildConfig.VERSION_NAME);
        requestMap.put("os_name", Constants.OS_NAME);
        requestMap.put("os_ver", android.os.Build.VERSION.RELEASE);
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false)) {// 第一次运行
            sUtils.setBooleanValue(Constants.IS_GUIDE, true);
            requestMap.put("new_setup", "1");
            sUtils.setStringValue(Constants.DEVICE_USER_ID, getPhoneInfo(context));
        } else {
            //Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
            requestMap.put("new_setup", "0");
        }

        requestMap.put("appcode", Constants.APPCODE);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        requestMap.put("width", String.valueOf(displayMetrics.widthPixels));
        requestMap.put("height", String.valueOf(displayMetrics.heightPixels));
        String username = sUtils.getStringValue(Constants.DEVICE_USER_ID, "");
        /*  Log.i("设备唯一码",username+"---");*/
        if (username != null && !"".equals(username)) {
            requestMap.put("phonecode", username);
        }else{
            sUtils.setStringValue(Constants.DEVICE_USER_ID, getPhoneInfo(context));
        }
        requestMap.put("model", AndroidUtils.get_model());
        requestMap.put("dnfrom", Utils.getAppMetaData(HRApplication.getAppContext(), "UMENG_CHANNEL"));
        //Log.i("设备唯一码",username+"---"+Utils.getAppMetaData(HRApplication.getAppContext(),"UMENG_CHANNEL"));
        requestMap.put("network_type", new NetworkMng(context).getNetworkType());
        return requestMap;
    }

    private static String newRandomUUID() {// add by yl
        String uuidRaw = UUID.randomUUID().toString();
        return uuidRaw.replaceAll("-", "");
    }

    public static String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(HRApplication.getAppContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //Log.i("到这里了",""+tm.getDeviceId());
            Log.i("现在的数据",tm.getDeviceId()+"--------");
            return tm.getDeviceId();
        }else{
            return "";
        }
    }


    /**
     * 手机验证码
     * @param type 1表示不是第一次请求请求 0代表第一次请求手机验证码
     * @param context
     * @param phoneNum  手机号码
     * @param way 1注册，2登录，3找回密码，4修改、验证手机，5修改手机+密码
     * @return
     */
    public static HashMap<String,String> getValidCode(Context context,String phoneNum,String captcha,int type,String way,int validType){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.phonetoken");
        requestMap.put("user_phone",phoneNum);
        requestMap.put("type",way);
        requestMap.put("channel",validType+"");
        if(type==1) {
            requestMap.put("captcha", captcha);
        }
       // Log.i("现在的数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 图形验证码
     * @return
     */
    public static HashMap<String,String> getAutoCode(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.captcha");
        return requestMap;
    }

    /**
     * 用户收藏等统计量
     * @return
     */
    public static HashMap<String,String> getAllInfo(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.totals");
        return requestMap;
    }

    /**
     * 获取通知的消息
     * @param ims
     * @return
     */
    public static HashMap<String,String> getNotice(String ims){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.getnotice");
        requestMap.put("phonecode",ims);
        return requestMap;
    }
    /**
     * 绑定极光推送
     * @param registerID
     * @return
     */
    public static HashMap<String,String> bindJpush(String registerID){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.pushtoken");
        requestMap.put("pushtoken",registerID);
        return requestMap;
    }
    /**
     * 设置通知开关
     * @param noticeData   通知的数据
     * @return
     */
    public static HashMap<String,String> setNotice(NoticeData noticeData){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.setnotice");
        requestMap.put("rushjob_state",noticeData.getRushjob_state());
        requestMap.put("invite_state",noticeData.getInvite_state());
        requestMap.put("sound_state",noticeData.getSound_state());
        requestMap.put("notice_bgntime",noticeData.getNotice_bgntime());
        requestMap.put("notice_endtime",noticeData.getNotice_endtime());
        requestMap.put("phonecode",noticeData.getPhonecode());
        requestMap.put("baidu_user_id",noticeData.getBaidu_user_id());
        requestMap.put("baidu_channel_id",noticeData.getBaidu_channel_id());
        requestMap.put("push_way",noticeData.getPush_way());
        return requestMap;
    }
    /**
     * 注册参数
     * @param phoneNumber  手机号码
     * @param validCode  验证码
     * @param psw   密码
     * @return
     */
    public static HashMap<String,String> getRegister(String phoneNumber,String validCode,String psw){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.phoneregister");
        requestMap.put("user_phone",phoneNumber);
        requestMap.put("industry","10");
        requestMap.put("user_pwd",psw);
        requestMap.put("token",validCode);
        Log.i("现在的数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 手机用户登录(type:1 标识手机登录 2 表示用户名登录)
     * @param phoneNumber   用户名
     * @param psw   密码
     * @param type  登录的类型
     * @return
     */
    public static HashMap<String,String> getLogin(String phoneNumber,String psw,int type){
        HashMap<String,String> requestMap=new HashMap<>();
        if(type==1) {
            requestMap.put("method", "user.phonelogin");
            requestMap.put("user_phone", phoneNumber);
            requestMap.put("industry", "10");
            requestMap.put("user_pwd", psw);
        }else if (type==2){
            requestMap.put("method", "user.login");
            requestMap.put("user_name", phoneNumber);
            requestMap.put("industry", "10");
            requestMap.put("user_pwd", psw);
        }else if(type==3){
            requestMap.put("method", "user.tokenlogin");
            requestMap.put("user_phone", phoneNumber);
            requestMap.put("industry", "10");
            requestMap.put("token", psw);
        }
        //Log.i("当前的数据",requestMap.toString());
        return requestMap;
    }
    /**
     * 手机找回密码
     * @param phoneNumber  手机号码
     * @param psw    密码
     * @param validCode  手机验证码
     * @return
     */
    public static HashMap<String,String> resetPhonePsw(String phoneNumber,String validCode,String psw){
        HashMap<String,String> requestMap=new HashMap<>();
            requestMap.put("method", "user.phoneresetpwd");
            requestMap.put("user_phone", phoneNumber);
            requestMap.put("token", validCode);
            requestMap.put("password", psw);
        return requestMap;
    }
    /**
     * 用户找回密码
     * @param email   邮箱
     * @param userName   用户名
     * @return
     */
    public static HashMap<String,String> resetUserPsw(String email,String userName){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method", "user.resetpwd");
        requestMap.put("email", email);
        requestMap.put("user_name", userName);
        return requestMap;
    }

    /**
     * 第三方登录
     * @param thirdPartBean  第三方帐号的信息
     * @return
     */
    public static HashMap<String,String> getThirdPartLogin(ThirdLoginBean thirdPartBean){
        HashMap<String,String>  requestMap=new HashMap<>();
        requestMap.put("method","user.thirdlogin");
        requestMap.put("third_code",thirdPartBean.getType());
        requestMap.put("third_uid",thirdPartBean.getUId());
        requestMap.put("industry","10");
        if(!"".equals(thirdPartBean.getSUId())&&thirdPartBean.getSUId()!=null) {
            requestMap.put("suid", thirdPartBean.getSUId());
        }else{
            requestMap.put("suid", "");
        }
        return requestMap;
    }

    /**
     * 第三方绑定
     * @param thirdPartBean  第三方平台用户的信息
     * @param userName  用户名
     * @param psw    密码
     * @param type  app用户的类型  1：
     * @return
     */
    public static HashMap<String,String> getThirdPartBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type){
        HashMap<String,String>  requestMap=new HashMap<>();
        requestMap.put("method","user.thirdbinding");
        requestMap.put("third_code",thirdPartBean.getType());
        requestMap.put("third_uid",thirdPartBean.getUId());
        requestMap.put("industry","10");
        //Log.i("传送的数据",Utils.getUserInfo(thirdPartBean));
        requestMap.put("third_userinfo", Utils.getUserInfo(thirdPartBean));
        requestMap.put("binding",""+Constants.BINDING);
        if(type==1){
            requestMap.put("user_name",userName);
            requestMap.put("user_pwd", psw);
        }
        //Log.i(TAG,requestMap.toString());
        return requestMap;
    }

    /**
     * 获取简历列表
     * @return
     */
    public static HashMap<String,String> getResumeList(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.resumelist");
        //requestMap.put("is_complete","1");
        //requestMap.put("mb","1");
        return requestMap;
    }
    /**
     * 获取简历列表
     * @return
     */
    public static HashMap<String,String> validPhoneState(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.phonestate");
       /* requestMap.put("is_complete","1");
        requestMap.put("mb","");*/
        return requestMap;
    }

    /**
     * 企业注册
     * @param companyRegisterBean  企业注册的内容
     * @return
     */
    public static HashMap<String,String> companyRegister(CompanyRegisterBean companyRegisterBean){
        HashMap<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("method", "enterprise.register");
        requestParams.put("site_code", companyRegisterBean.getSiteCode());
        requestParams.put("user_name", companyRegisterBean.getUserName());
        requestParams.put("password", companyRegisterBean.getPassword());
        requestParams.put("passwordre",companyRegisterBean.getPasswordRe());
        requestParams.put("enterprise_name", companyRegisterBean.getEnterpriseName());
        requestParams.put("linkman", companyRegisterBean.getLinkMan());
        requestParams.put("how_to_know", companyRegisterBean.getHowToknow());
        requestParams.put("phone", companyRegisterBean.getPhone());
        requestParams.put("email", companyRegisterBean.getEmail());
        requestParams.put("is_login", "0");
        //Log.i("传送的数据",requestParams.toString());
      return requestParams;

    }
    /**
     * 获取具体简历的信息
     * @param resumeId 简历的id
     * @return
     */
    public static HashMap<String,String> getResumeDate(String resumeId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.get");
        requestMap.put("resume_id",resumeId);
        requestMap.put("resume_language","zh");
        //Log.i("简历信息",requestMap.toString());
        return requestMap;
    }

    /**
     * 创建一份新的简历
     * @return
     */
    public static HashMap<String,String> createNewResume(){
        HashMap<String,String> requestMap=new HashMap<>();
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        String elegantResumeId=sUtils.getStringValue(Constants.ELEGANTRESUME_ID,"");
        String resumeId="";
        if(elegantResumeId==null||"".equals(elegantResumeId)){
            resumeId="1";
        }else{
            if("1".equals(elegantResumeId)){
                resumeId="2";
            }else{
                resumeId="1";
            }
        }
        requestMap.put("method","user_resume.titleset");
        //requestMap.put("resume_id",resumeId);
        requestMap.put("resume_type","1");
        requestMap.put("resume_language","zh");
        //Log.i("简历信息",requestMap.toString());
        return requestMap;
    }
    /**
     * 添加或者修改简历的个人信息
     * @param personalInformationData 添加/修改的内容
     * @return
     */
    public static HashMap<String,String> sendPersonalInformationToResume(PersonalInformationData personalInformationData){
        HashMap<String,String> requestMap=new HashMap<>();
        String birth=personalInformationData.getBirth();
        String year=birth.substring(0,birth.indexOf("-"));
        String month=birth.substring(birth.indexOf("-")+1,birth.lastIndexOf("-"));
        String day=birth.substring(birth.lastIndexOf("-")+1);
        requestMap.put("method","user_resume.baseinfoset");
        requestMap.put("resume_language","zh");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        int resumeId=sUtils.getIntValue(Constants.RESUME_ID,10);
        if(resumeId!=10) {
            requestMap.put("resume_id", resumeId+"");
        }
        if(personalInformationData.getImageUrl()!=null&&!"".equals(personalInformationData.getImageUrl())) {
            requestMap.put("pic_filekey", personalInformationData.getImageUrl());
        }
        requestMap.put("name",personalInformationData.getName());
        requestMap.put("sex",personalInformationData.getSex());
        requestMap.put("year",year);
        requestMap.put("month",month);
        requestMap.put("location",personalInformationData.getLivePlace());
        requestMap.put("day",day);
        requestMap.put("work_beginyear",personalInformationData.getWorkTime());
        requestMap.put("post_rank",personalInformationData.getPositionTitle());
        requestMap.put("ydphone",personalInformationData.getPhoneNumber());
        requestMap.put("emailaddress",personalInformationData.getEmail());
        requestMap.put("is_app","1");
        //System.out.println(requestMap.toString()+"haha");
        return requestMap;
    }

    /**
     *添加/修改教育背景
     * @param educationData  添加/修改的内容
     * @return
     */
    public static HashMap<String,String> sendEducationToResume(EducationData educationData){
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=educationData.getStartTime();
        String endTime=educationData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.educationset");
        if(!"".equals(educationData.getEducationId())&&educationData.getEducationId()!=null){
            requestMap.put("education_id",educationData.getEducationId());
        }
        requestMap.put("fromyear",fromYear);
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        int resumeId=sUtils.getIntValue(Constants.RESUME_ID,10);
        if(resumeId!=10) {
            requestMap.put("resume_id", resumeId+"");
        }
        requestMap.put("frommonth",fromMonth);
        requestMap.put("toyear",toYear);
        requestMap.put("tomonth",toMonth);
        requestMap.put("schoolname",educationData.getSchoolName());
        requestMap.put("degree",educationData.getDegree());
        requestMap.put("moremajor",educationData.getProfession());
        requestMap.put("edudetail","");
        //Log.i("okht",requestMap.toString());
        return requestMap;
    }

    /**
     * 添加/修改工作经历
     * @param workExpData 添加/修改的内容
     * @return
     */
    public static HashMap<String,String> sendWorkExpToResume(WorkExpData workExpData){
        //Log.i("现在的数据",workExpData.toString());
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=workExpData.getStartTime();
        String endTime=workExpData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.experienceset");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        int resumeId=sUtils.getIntValue(Constants.RESUME_ID,10);
        if(resumeId!=10) {
            requestMap.put("resume_id", resumeId+"");
        }
        if(!"".equals(workExpData.getExperienceId())&&workExpData.getExperienceId()!=null){
            requestMap.put("experience_id",workExpData.getExperienceId());
        }
        requestMap.put("company",workExpData.getCompany());
        requestMap.put("fromyear",fromYear);
        requestMap.put("frommonth",fromMonth);
        requestMap.put("toyear",toYear);
        requestMap.put("tomonth",toMonth);
        requestMap.put("companyaddress",workExpData.getWorkPlace());
        requestMap.put("salary",workExpData.getGrossPay());
        requestMap.put("position",workExpData.getPosition());
        requestMap.put("responsiblity",workExpData.getResponsibilityDescription());
        return requestMap;
    }

    /**
     * 修改/添加求职意向
     * @param jobOrderData 修改/添加的内容
     * @return
     */
    public static HashMap<String,String> sendJobOrderToResume(JobOrderData jobOrderData){
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.orderset");
        int resumeId=sUtils.getIntValue(Constants.RESUME_ID,10);
        if(resumeId!=10) {
            requestMap.put("resume_id", resumeId+"");
        }
        if(jobOrderData.getJobStyle()==null||"".equals(jobOrderData.getJobStyle())){
            requestMap.put("current_workstate", "11");
        }else{
            requestMap.put("current_workstate", jobOrderData.getJobStyle());
        }
        requestMap.put("jobtype",jobOrderData.getWorkType());
        requestMap.put("industry",jobOrderData.getIndustry());
        requestMap.put("func",jobOrderData.getExpectPosition());
        requestMap.put("lingyu",jobOrderData.getExpectArea());
        requestMap.put("order_salary",jobOrderData.getSalary());
        requestMap.put("workarea",jobOrderData.getAddress());
        requestMap.put("resume_language","zh");
        if("".equals(jobOrderData.getMode())) {
            requestMap.put("save_mode", "1");
        }else{
            requestMap.put("save_mode",jobOrderData.getMode());
        }
        //Log.i("数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 添加/修改简历标题
     * @param type
     * @return
     */
    public static HashMap<String,String> setResumeType(String type){
        HashMap<String,String> requestMap=new HashMap<>();
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        String elegantResumeId=sUtils.getStringValue(Constants.ELEGANTRESUME_ID,"");
        String resumeId="";
        if(elegantResumeId==null||"".equals(elegantResumeId)){
            resumeId="1";
        }else{
            if("1".equals(elegantResumeId)){
                resumeId="2";
            }else{
                resumeId="1";
            }
        }
        requestMap.put("method","user_resume.titleset");
        requestMap.put("resume_id",resumeId);
        requestMap.put("resume_type",type);
        requestMap.put("resume_language","zh");
        //Log.i("简历信息",requestMap.toString());
        return requestMap;
    }

    /**
     * 设置默认简历
     * @param resumeId  简历的id
     * @param important  0:不是默认   1：默认
     * @return
     */
    public static HashMap<String,String> setDefaultResume(String resumeId,String important){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.setimportant");
        requestMap.put("resume_id",resumeId);
        requestMap.put("resume_language","zh");
        requestMap.put("resume_id",resumeId);
        requestMap.put("important",important);
        return requestMap;
    }

    /**
     * 获取简历中的个人信息
     * @return
     */
    public static HashMap<String,String> getResumePersonnalInfo(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.baseinfoget");
        requestMap.put("resume_language","zh");
        return requestMap;
    }

    /**
     * 上传图片
     * @param content  加密的图片的内容
     * @return
     */
    public static HashMap<String,String> upLoadImage(String content){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.uploadphoto");
        requestMap.put("photo_content",content);
        //Log.i("数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取求职意向的内容
     * @return
     */
    public static HashMap<String,String> getJobOrderInfo(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.orderget");
        //Log.i("数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取教育背景的内容
     * @param educationId  教育背景的id
     * @return
     */
    public static HashMap<String,String> getEducation(String educationId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.educationget");
        requestMap.put("education_id",educationId);
        requestMap.put("resume_language","zh");
        return requestMap;
    }

    /**
     * 删除教育背景
     * @param educationId 教育背景的id
     * @return
     */
    public static HashMap<String,String> deleteEducation(String educationId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.educationdel");
        requestMap.put("education_id",educationId);
        requestMap.put("resume_language","zh");
        return requestMap;
    }

    /**
     * 获取项目经历的内容
     * @param experienceId  项目经历的id
     * @return
     */
    public static HashMap<String,String> getWorkExp(String experienceId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.experienceget");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("experience_id",experienceId);
        return  requestMap;
    }

    /**
     * 删除工作经历
     * @param experienceId  工作经历的id
     * @return
     */
    public static HashMap<String,String> deleteWorkExp(String experienceId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.experiencedel");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("experience_id",experienceId);
        return  requestMap;
    }

    /**
     * 添加/修改项目经历
     * @param projectExpData  添加/修改的内容
     * @return
     */
    public static HashMap<String,String> addOrReplaceProjectExp(ProjectExpData projectExpData){
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=projectExpData.getStartTime();
        String endTime=projectExpData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.projectset");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        if(projectExpData.getProjectId()!=null&&!"".equals(projectExpData.getProjectId())) {
            requestMap.put("project_id", projectExpData.getProjectId());
        }
        requestMap.put("fromyear",fromYear);
        requestMap.put("frommonth",fromMonth);
        requestMap.put("toyear",toYear);
        requestMap.put("tomonth",toMonth);
        requestMap.put("projectname",projectExpData.getProjectName());
        requestMap.put("projectdesc",projectExpData.getProjectDes());
        requestMap.put("responsibility",projectExpData.getProjectResponsibility());
        requestMap.put("position",projectExpData.getProjectPosition());
        return  requestMap;
    }

    /**
     * 删除项目经历
     * @param projectId  项目经历的id
     * @return
     */
    public static HashMap<String,String> deleteProjectInfo(String projectId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.projectdel");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("project_id",projectId);
        return  requestMap;
    }

    /**
     * 获取项目经历的内容
     * @param projectId 项目经历的id
     * @return
     */
    public static HashMap<String,String> getProjectInfo(String projectId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.projectget");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("project_id",projectId);
        return  requestMap;
    }

    /**
     * 添加/修改培训经历
     * @param trainExpData  添加/修改的内容
     * @return
     */
    public static HashMap<String,String> addOrReplaceTrainExp(TrainExpData trainExpData){
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=trainExpData.getStartTime();
        String endTime=trainExpData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.plantset");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        if(trainExpData.getTrainId()!=null&&!"".equals(trainExpData.getTrainId())) {
            requestMap.put("plant_id", trainExpData.getTrainId());
        }
        requestMap.put("fromyear",fromYear);
        requestMap.put("frommonth",fromMonth);
        requestMap.put("toyear",toYear);
        requestMap.put("tomonth",toMonth);
        requestMap.put("institution",trainExpData.getTrainInstruction());
        requestMap.put("course",trainExpData.getTrainClass());
        requestMap.put("traindetail",trainExpData.getTrainDes());
       /* requestMap.put("",);*/
        return requestMap;
    }

    /**
     * 删除培训经历
     * @param trainId  培训经历的id
     * @return
     */
    public static HashMap<String,String> deleteTrainExp(String trainId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.plantdel");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("plant_id",trainId);
        return  requestMap;
    }

    /**
     * 获取培训经历的内容
     * @param trainId  培训经历的id
     * @return
     */
    public static HashMap<String,String> getTrainExp(String trainId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.plantget");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("plant_id", trainId);
        return  requestMap;
    }

    /**
     * 添加/修改语言能力
     * @param languageLevelData  添加/修改的内容
     * @return
     */
    public static HashMap<String,String> addOrReplaceLanguageLevel(LanguageLevelData languageLevelData){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.languageset");
        requestMap.put("langname",languageLevelData.getLanguageId());
        requestMap.put("read_level", languageLevelData.getReadLevel());
        requestMap.put("speak_level", languageLevelData.getSpeakLevel());
        return requestMap;
    }

    /**
     * 删除语言能力
     * @param LanguageId  语言能力的id
     * @return
     */
    public static HashMap<String,String> deleteLanguageLevel(String LanguageId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.languagedel");
        requestMap.put("langname",LanguageId);
        return  requestMap;
    }

    /**
     * 获取语言能力的内容
     * @param languageId  语言能力的id
     * @return
     */
    public static HashMap<String,String> getLanguage(String languageId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.languageget");
        requestMap.put("langname",languageId);
        return  requestMap;
    }

    /**
     * 添加/修改专业技能
     * @param professionSkillData  添加/修改的内容
     * @return
     */
    public static HashMap<String,String> addOrReplaceProfessionSkill(ProfessionSkillData professionSkillData){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.skillset");
        if(professionSkillData.getSkillId()!=null&&!"".equals(professionSkillData.getSkillId())) {
            requestMap.put("skill_id", professionSkillData.getSkillId());
        }
        requestMap.put("skilltitle", professionSkillData.getSkillName());
        requestMap.put("usetime",professionSkillData.getSkillUseTime());
        requestMap.put("ability", professionSkillData.getSkillLevel());
        return requestMap;
    }

    /**
     * 删除专业技能
     * @param skillId 专业技能的id
     * @return
     */
    public static HashMap<String,String> deleteProfessionSkill(String skillId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.skilldel");
        requestMap.put("skill_id",skillId);
        return  requestMap;
    }

    /**
     * 获取专业技能的数据
     * @param skillId 专业技能的id
     * @return
     */
    public static HashMap<String,String> getProfessionSkill(String skillId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.skillget");
        requestMap.put("skill_id",skillId);
        return  requestMap;
    }

    /**
     * 添加/修改自我介绍
     * @param content  自我介绍的内容
     * @return
     */
    public static HashMap<String,String> addOrReplaceIntroduction(String content){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.assessset");
        requestMap.put("introduction",content);
        return  requestMap;
    }
    public static HashMap<String,String> getIntroduction(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.assessget");
        return  requestMap;
    }

    /**
     * 设置简历的公开度
     * @param openstate  0：公开  2：保密
     * @return
     */
    public static HashMap<String,String> setHide(String openstate){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.setopenstate");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("openstate",openstate);
        return  requestMap;
    }

    /**
     * 获取推荐职位的数据集合
     * @param limit   获取的限制数量
     * @return
     */
    public static HashMap<String,String> getRecommendJobScore(int limit){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.recomlist");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("limit",""+limit);
        return requestMap;
    }

    /**
     * 获取符合求职意向的职位列表
     * @param page  页码
     * @param pageNum 每页加载的数量
     * @return
     */
    public static HashMap<String,String> getRecommendJobInfo(int page,int pageNum){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.jobrecom");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        if(!"".equals(sUtils.getStringValue(Constants.INDUSTRY_ID,""))&&sUtils.getStringValue(Constants.INDUSTRY_ID,"")!=null) {
            requestMap.put("industry", sUtils.getStringValue(Constants.INDUSTRY_ID, ""));
        }
        requestMap.put("page",""+page);
        requestMap.put("page_nums",""+pageNum);
       // Log.i("推荐职位的信息",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取搜索职位的数据
     * @param jobSearchBean 搜索条件
     * @param page  页码
     * @return
     */
    public static HashMap<String,String> getJobSearchList(JobSearchBean jobSearchBean,int page){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.search");
        if(jobSearchBean.getPositionId()!=null&&!"".equals(jobSearchBean.getPositionId())) {
            requestMap.put("func", jobSearchBean.getPositionId());
        }
        if(!"".equals(jobSearchBean.getPlaceId())&&jobSearchBean.getPlaceId()!=null) {
            requestMap.put("area", jobSearchBean.getPlaceId());
        }
        if(!"".equals(jobSearchBean.getSearchName())&&jobSearchBean.getSearchName()!=null) {
            if(jobSearchBean.getPositionId()==null||"".equals(jobSearchBean.getPositionId())) {
                requestMap.put("searchword", jobSearchBean.getSearchName());
            }
        }
        if(!"".equals(jobSearchBean.getWorkExp())&&jobSearchBean.getWorkExp()!=null) {
            requestMap.put("filter_workyear", jobSearchBean.getWorkExp());
        }
        if(!"".equals(jobSearchBean.getWorkType())&&jobSearchBean.getWorkType()!=null) {
            requestMap.put("filter_worktype", jobSearchBean.getWorkType());
        }
        if(!"".equals(jobSearchBean.getJobTime())&&jobSearchBean.getJobTime()!=null) {
            requestMap.put("filter_issuedate", jobSearchBean.getJobTime());
        }
        if(!"".equals(jobSearchBean.getDegree())&&jobSearchBean.getDegree()!=null){
            requestMap.put("filter_study",jobSearchBean.getDegree());
        }
        if(!"".equals(jobSearchBean.getCompanyType())&&jobSearchBean.getCompanyType()!=null) {
            requestMap.put("companytype", jobSearchBean.getCompanyType());
        }
        if(!"".equals(jobSearchBean.getSalary_left())&&jobSearchBean.getSalary_left()!=null) {
            requestMap.put("salaryfrom", jobSearchBean.getSalary_left());
        }
        if(!"".equals(jobSearchBean.getSalary_right())&&jobSearchBean.getSalary_right()!=null) {
            requestMap.put("salaryto", jobSearchBean.getSalary_right());
        }
        if(!"".equals(jobSearchBean.getIndustryId())&&jobSearchBean.getIndustryId()!=null) {
            requestMap.put("industry", jobSearchBean.getIndustryId());
        }
        if(!"".equals(jobSearchBean.getFieldId())&&jobSearchBean.getFieldId()!=null) {
            requestMap.put("lingyu", jobSearchBean.getFieldId());
        }
        requestMap.put("page", page+"");
        requestMap.put("page_nums", "20");
        requestMap.put("wordtype",jobSearchBean.getJobType()+"");
        //Log.i("数据1",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取搜索置顶职位数据
     * @param jobSearchBean 搜索条件
     * @return
     */
    public static HashMap<String,String> getTopSearchJob(JobSearchBean jobSearchBean){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.topsearch");
        if(jobSearchBean.getPositionId()!=null&&!"".equals(jobSearchBean.getPositionId())) {
            requestMap.put("func", jobSearchBean.getPositionId());
        }
        if(!"".equals(jobSearchBean.getPlaceId())&&jobSearchBean.getPlaceId()!=null) {
            requestMap.put("area", jobSearchBean.getPlaceId());
        }
        if(!"".equals(jobSearchBean.getSearchName())&&jobSearchBean.getSearchName()!=null) {
            requestMap.put("searchword", jobSearchBean.getSearchName());
        }
        if(!"".equals(jobSearchBean.getIndustryId())&&jobSearchBean.getIndustryId()!=null) {
            requestMap.put("industry", jobSearchBean.getIndustryId());
        }
        //Log.i("现在的数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取职位的信息
     * @param jobId 职位的id
     * @return
     */
    public static HashMap<String,String> getPositionData(String jobId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.jobinfo");
        requestMap.put("job_id",""+jobId);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 设置企业来信为已读
     * @param recordId 来信id
     * @return
     */
    public static HashMap<String,String> setInviteIsRead(String recordId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.invitedread");
        requestMap.put("record_id",""+recordId);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取企业来信的内容
     * @param recordId  企业来信的id
     * @return
     */
    public static HashMap<String,String> getInviteInfo(String recordId){
        HashMap<String,String> requestMap=new HashMap<>();
        SharedPreferencesUtils sUtis=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("method","user_stow.userinvited");
        requestMap.put("id",""+recordId);
        requestMap.put("user_id",sUtis.getStringValue(Constants.USERID,""));

        //Log.i("你好",requestMap.toString());
        return requestMap;
    }

    /**
     * 收藏职位
     * @param jobId  职位id
     * @return
     */
    public static HashMap<String,String> collectionJob(String jobId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.favour");
        requestMap.put("job_id",""+jobId);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 投递职位
     * @param jobId  职位的id
     * @return
     */
    public static HashMap<String,String> deliverJob(String jobId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.apply");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("resume_id",sUtils.getIntValue(Constants.RESUME_ID,0)+"");
        requestMap.put("job_id",""+jobId);
        requestMap.put("resume_language","zh");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取公司的详情
     * @param companyId   公司的id
     * @return
     */
    public static HashMap<String,String> getCompanyData(String companyId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.enterprise");
        requestMap.put("enterprise_id",companyId);
        return requestMap;
    }

    /**
     * 验证手机号码是否已经注册
     * @param phone  手机号码
     * @return
     */
    public static HashMap<String,String> validPhoneIsExit(String phone){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_check.phoneexists");
        requestMap.put("phone",phone);
        return requestMap;
    }

    /**
     * 获取该公司所发布的职位
     * @param CompanyId  公司的id
     * @return
     */
    public static HashMap<String,String> getReleaseJob(String CompanyId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.alljobs");
        requestMap.put("enterprise_id",CompanyId);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 收藏记录
     * @param page  页码
     * @return
     */
    public static HashMap<String,String> getCollectionData(int page){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.favourite");
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 删除收藏的内容
     * @param collectionId  收藏的id
     * @param jobId  职位的id
     * @return
     */
    public static HashMap<String,String> deleteCollectionData(String collectionId,String jobId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.delefavourite");
        requestMap.put("record_id",collectionId);
        requestMap.put("job_id",jobId);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取投递反馈的数据
     * @param page  页码
     * @param isRead  是否已读
     * @param isInvite  是否邀请
     * @return
     */
    public static HashMap<String,String> getDeliverFeedback(int page,int isRead,int isInvite){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.applied");
        requestMap.put("is_read",isRead+"");
        requestMap.put("is_invite",isInvite+"");
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 设置投递反馈已读
     * @param id  投递反馈的id
     * @return
     */
    public static HashMap<String,String> setDeliverFeedBack(String id){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.appliedread");
        requestMap.put("record_id",id);
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取谁看我了的数据
     * @param page  页码
     * @return
     */
    public static HashMap<String,String> getWhoSeeMe(int page){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.browsed");
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取企业来信列表信息
     * @param page  页码
     * @return
     */
    public static HashMap<String,String> getInviteInterview(int page){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_stow.invited");
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 意见反馈
     * @param content  内容
     * @param email   邮箱
     * @return
     */
    public static HashMap<String,String> feedBack(String content,String email){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.feedback");
        requestMap.put("email",email);
        requestMap.put("content",content);
        requestMap.put("cate_id","20");
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取发现的内容
     * @param page  页码
     * @param ad_type  广告的类型
     * @return
     */
    public static HashMap<String,String> getFind(int page,String ad_type){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","mobilead.list");
        requestMap.put("c_type", ad_type);
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        requestMap.put("industry",sUtils.getStringValue(Constants.INDUSTRY_ID,""));
       /* Log.i("你好",requestMap.toString());*/
        return requestMap;
    }

    /**
     * 获取广告
     * @param cid
     * @param aId
     * @return
     */
    public static HashMap<String,String> getNotice(String cid,String aId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","mobilead.list");
        if(!"".equals(aId)) {
            requestMap.put("a_id", aId);
        }
        requestMap.put("c_id",cid);
        requestMap.put("page_nums","8");
        //Log.i("现在的数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 获取职位的匹配度
     * @param id  匹配的id
     * @return
     */
    public static HashMap<String,String> getResumeScore(String id){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","job.resumematch");
        requestMap.put("match", id);
        //Log.i("id",id+"-----");
        return requestMap;
    }

    /**
     * 获取就业指导的列表
     * @param page  页码
     * @param guidanceId  就业指导的id
     * @return
     */
    public static HashMap<String,String> getGuidanceTitle(int page,String guidanceId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","news.gettitle");
        requestMap.put("column_id",guidanceId);
        requestMap.put("page",page+"");
        requestMap.put("page_nums","20");
        return requestMap;
    }

    /**
     * 获取就业指导的内容
     * @param newId  新闻的id
     * @return
     */
    public static HashMap<String,String> getGuidanceTitleInfo(String newId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","news.gettitlecon");
        requestMap.put("news_id",newId);
        return requestMap;
    }

    /**
     *获取屏蔽的内容
     * @return
     */
    public static HashMap<String,String> getShieldCompanyData(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_eliminate.list");
        return requestMap;
    }
    public static HashMap<String,String> queryShieldCompanyDataByKeyWord(String content){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_eliminate.entelist");
        requestMap.put("eliminate_txt",content);
        requestMap.put("nums",50+"");
        return requestMap;
    }

    /**
     * 设置屏蔽的内容
     * @param companyName  屏蔽的关键词
     * @return
     */
    public static HashMap<String,String> setShieldCompany(String companyName){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_eliminate.set");
        requestMap.put("eliminate_txt",companyName);
        return requestMap;
    }

    /**
     * 用户退出
     * @return
     */
    public static HashMap<String,String> getLoginOut(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.logout");
        return requestMap;
    }

    /**
     * 删除屏蔽的关键词
     * @param shieldId  屏蔽的id
     * @return
     */
    public static HashMap<String,String> deleteShieldCompany(String shieldId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_eliminate.del");
        requestMap.put("id",shieldId);
        return requestMap;
    }

    /**
     * 升级简历
     * @param resumeId  简历的id
     * @return
     */
    public static HashMap<String,String> updateResume(String resumeId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.upgradetype");
        requestMap.put("resume_id",resumeId);
        return requestMap;
    }

    /**
     * 更新简历的事件
     * @param resumeId  简历id
     * @return
     */
    public static HashMap<String,String> refreshResume(String resumeId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.updatetime");
        requestMap.put("resume_id",resumeId);
        return requestMap;
    }

    /**
     * 修改手机号码
     * @param phone  手机号码
     * @param validCode  验证码
     * @return
     */
    public static HashMap<String,String> changePhone(String phone,String validCode){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.chgphone");
        requestMap.put("user_phone",phone);
        requestMap.put("token",validCode);
        //Log.i("现在的 数据",requestMap.toString());
        return requestMap;
    }

    /**
     * 修改用户密码
     * @param oldPsw  旧密码
      * @param newPsw  新密码
     * @return
     */
    public static HashMap<String,String> changePsw(String oldPsw,String newPsw){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.updatepwd");
        requestMap.put("old_pwd",oldPsw);
        requestMap.put("new_pwd",newPsw);
        //Log.i("数据中",requestMap.toString());
        return requestMap;
    }
}
