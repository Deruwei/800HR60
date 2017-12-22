package com.hr.ui.api;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hr.ui.bean.EducationData;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.AndroidUtils;
import com.hr.ui.utils.NetworkMng;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by wdr on 2017/11/21.
 */

public class ApiParameter {
    public static final String TAG=ApiParameter.class.getSimpleName();
    public static HashMap<String,String> getConnect(Context context){
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(context);
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method", "user.connect");
        requestMap.put("api_ver", Constants.API_VER);
        requestMap.put("client_ver", "5.5.2");
        requestMap.put("os_name", Constants.OS_NAME);
        requestMap.put("os_ver", android.os.Build.VERSION.RELEASE);
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false)) {// 第一次运行
            sUtils.setBooleanValue(Constants.IS_GUIDE, true);
            requestMap.put("new_setup", "1");
            sUtils.setStringValue(Constants.DEVICE_USER_ID, newRandomUUID());
        } else {
            //Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
            requestMap.put("new_setup", "0");
        }
        requestMap.put("appcode", Constants.APPCODE);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        requestMap.put("width", String.valueOf(displayMetrics.widthPixels));
        requestMap.put("height", String.valueOf(displayMetrics.heightPixels));
        String username = sUtils.getStringValue(Constants.DEVICE_USER_ID, "");
        requestMap.put("phonecode", username);
        requestMap.put("model", AndroidUtils.get_model());
        requestMap.put("dnfrom", Constants.DNFROM);
        requestMap.put("network_type", new NetworkMng(context).getNetworkType());
        return requestMap;
    }
    private static String newRandomUUID() {// add by yl
        String uuidRaw = UUID.randomUUID().toString();
        return uuidRaw.replaceAll("-", "");
    }

    /**
     * 手机验证码
     * @param type 1表示不是第一次请求请求 0代表第一次请求手机验证码
     * @param context
     * @param phoneNum
     * @param way
     * @return
     */
    public static HashMap<String,String> getValidCode(Context context,String phoneNum,String captcha,int type,String way){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.phonetoken");
        requestMap.put("user_phone",phoneNum);
        requestMap.put("type",way);
        if(type==1) {
            requestMap.put("captcha", captcha);
        }
        //Log.i(TAG,requestMap.toString());
        return requestMap;
    }

    /**
     * 图形验证码
     * @param context
     * @return
     */
    public static HashMap<String,String> getAutoCode(Context context){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.captcha");
        return requestMap;
    }

    /**
     * 注册参数
     * @param phoneNumber
     * @param validCode
     * @param psw
     * @return
     */
    public static HashMap<String,String> getRegister(String phoneNumber,String validCode,String psw){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user.phoneregister");
        requestMap.put("user_phone",phoneNumber);
        requestMap.put("industry","11");
        requestMap.put("user_pwd",psw);
        requestMap.put("token",validCode);
        return requestMap;
    }

    /**
     * 手机用户登录(type:1 标识手机登录 2 表示用户名登录)
     * @param phoneNumber
     * @param psw
     * @return
     */
    public static HashMap<String,String> getLogin(String phoneNumber,String psw,int type){
        HashMap<String,String> requestMap=new HashMap<>();
        if(type==1) {
            requestMap.put("method", "user.phonelogin");
            requestMap.put("user_phone", phoneNumber);
            requestMap.put("industry", "11");
            requestMap.put("user_pwd", psw);
        }else if (type==2){
            requestMap.put("method", "user.login");
            requestMap.put("user_name", phoneNumber);
            requestMap.put("industry", "11");
            requestMap.put("user_pwd", psw);
        }
        return requestMap;
    }
    /**
     * 手机找回密码
     * @param phoneNumber
     * @param psw
     * @param validCode
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
     * @param email
     * @param userName
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
     * @param thirdPartBean
     * @return
     */
    public static HashMap<String,String> getThirdPartLogin(ThirdLoginBean thirdPartBean){
        HashMap<String,String>  requestMap=new HashMap<>();
        requestMap.put("method","user.thirdlogin");
        requestMap.put("third_code",thirdPartBean.getType());
        requestMap.put("third_uid",thirdPartBean.getUId());
        requestMap.put("industry","11");
        if(!"".equals(thirdPartBean.getSUId())&&thirdPartBean.getSUId()!=null) {
            requestMap.put("suid", thirdPartBean.getSUId());
        }else{
            requestMap.put("suid", "");
        }
        //Log.i(TAG,requestMap.toString());
        return requestMap;
    }

    /**
     * 第三方绑定
     * @param thirdPartBean
     * @param userName
     * @param psw
     * @param type
     * @return
     */
    public static HashMap<String,String> getThirdPartBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type){
        HashMap<String,String>  requestMap=new HashMap<>();
        requestMap.put("method","user.thirdbinding");
        requestMap.put("third_code",thirdPartBean.getType());
        requestMap.put("third_uid",thirdPartBean.getUId());
        requestMap.put("industry","11");
        requestMap.put("third_userinfo","");
        requestMap.put("binding",""+Constants.BINDING);
        if(type==1){
            requestMap.put("user_name",userName);
            requestMap.put("user_pwd", psw);
        }
        return requestMap;
    }

    /**
     * 获取简历列表
     * @return
     */
    public static HashMap<String,String> getResumeList(){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.resumelist");
        requestMap.put("is_complete","0");
        requestMap.put("mb","");
        return requestMap;
    }

    /**
     * 获取具体简历的信息
     * @param resumeId
     * @return
     */
    public static HashMap<String,String> getResumeDate(String resumeId){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.get");
        requestMap.put("resume_id",resumeId);
        requestMap.put("resume_language","");
        return requestMap;
    }

    /**
     * 添加或者修改简历的个人信息
     * @param personalInformationData
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
        requestMap.put("pic_filekey","");
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
        System.out.println(requestMap.toString()+"haha");
        return requestMap;
    }
    public static HashMap<String,String> sendEducationToResume(EducationData educationData){
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=educationData.getStartTime();
        String endTime=educationData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.educationset");
        requestMap.put("fromyear",fromYear);
        requestMap.put("frommonth",fromMonth);
        requestMap.put("toyear",toYear);
        requestMap.put("tomonth",toMonth);
        requestMap.put("schoolname",educationData.getSchoolName());
        requestMap.put("degree",educationData.getDegree());
        requestMap.put("moremajor",educationData.getProfession());
        requestMap.put("edudetail","");
        return requestMap;
    }
    public static HashMap<String,String> sendWorkExpToResume(WorkExpData workExpData){
        HashMap<String,String> requestMap=new HashMap<>();
        String startTime=workExpData.getStartTime();
        String endTime=workExpData.getEndTime();
        String fromYear=startTime.substring(0,startTime.indexOf("-"));
        String fromMonth=startTime.substring(startTime.indexOf("-")+1);
        String toYear=endTime.substring(0,endTime.indexOf("-"));
        String toMonth=endTime.substring(endTime.indexOf("-")+1);
        requestMap.put("method","user_resume.experienceset");
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
    public static HashMap<String,String> sendJobOrderToResume(JobOrderData jobOrderData){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.orderset");
        requestMap.put("current_workstate","11");
        requestMap.put("jobtype",jobOrderData.getWorkType());
        requestMap.put("industry",jobOrderData.getIndustry());
        requestMap.put("func",jobOrderData.getExpectPosition());
        requestMap.put("lingyu",jobOrderData.getExpectArea());
        requestMap.put("order_salary",jobOrderData.getSalary());
        requestMap.put("workarea",jobOrderData.getAddress());
        requestMap.put("resume_language","zh");
        requestMap.put("save_mode","1");
        return requestMap;
    }

    /**
     * 修改简历的类型
     * @param type
     * @return
     */
    public static HashMap<String,String> setResumeType(String type){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.typeset");
        requestMap.put("resume_type",type);
        return requestMap;
    }

    public static HashMap<String,String> setDefaultResume(String resumeId,String important){
        HashMap<String,String> requestMap=new HashMap<>();
        requestMap.put("method","user_resume.setimportant");
        requestMap.put("resume_id",resumeId);
        requestMap.put("resume_language","zh");
        requestMap.put("resume_id",resumeId);
        requestMap.put("important",important);
        return requestMap;
    }
}
