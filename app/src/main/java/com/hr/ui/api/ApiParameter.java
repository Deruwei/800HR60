package com.hr.ui.api;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hr.ui.bean.ThirdPartBean;
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
            requestMap.put("new_setup", "0");
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
    public static HashMap<String,String> getThirdPartLogin(ThirdPartBean thirdPartBean){
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
    public static HashMap<String,String> getThirdPartBinding(ThirdPartBean thirdPartBean,String userName,String psw,int type){
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
}
