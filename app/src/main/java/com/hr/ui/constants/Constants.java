package com.hr.ui.constants;

import com.hr.ui.api.HostType;

/**
 * Created by wdr on 2017/11/20.
 */

public class Constants {
    public static final String ARGS = "args";
    //用户的id
    public static final String USERID = "userId";
    public static  String  ALIAS;
    public static String TYPE_THIRDPARTLOGIN = "";
    public static final String RESUME_ID="resume_id";
    public static final String ELEGANTRESUME_ID="elegantResume_id";
    public static final String JUST_ELEGANTRESUME="just_elegantResume";
    public static final String BASEINFO_STR="baseInfo_str";
    public static final String RESUME_TYPE="resume_type";
    public static final String VALID_TYPE="valid_type";
    public static final String INDUSTRY_ID="industryId";
    public static final String RESUME_STARTTYPE="start_type";//表示快速简历页面在哪页停止，然后进入主页面
    public static final String RESUME_STOPTYPE="stop_type";
    //数据库名称
    public static final String CURRENT_TIME="current_time";
    public static final String DB_NAME="hr_db";
    public static final int NETWORK_2G=1;
    public static final int NETWORK_3G=2;
    public static final int NETWORK_4G=3;
    public static final int NETWORK_WIFI=4;
    public static final int NETWORK_UNKNOWN=0;
    public static final String BINDING="0";
    //是否是自动登录
    public static final String ISAUTOLOGIN="isAutoLogin";
    //自动登录的类型  手机号码登录  用户登录  自动登录
    public static final String AUTOLOGINTYPE="autoLoginType";
    public static final String CITY_VER="city_ver";
    public static final String JOB_VER="job_ver";
    public static final String LINGYU_VER="lingYu_ver";
    public static final String PERSONIMAGE="person_image";
    public static final String RESUME_OPENTYPE="resume_open";
    public static final String CITYNAME="city_name";
    public static final String ISFIRSTINTO="is_firstInto";
    public static final String BIRTHYEAR="birth_year";
    public static final String ISHAVENEWS="is_have_new";
    public static final String EDUCATION_ID="education_id";
    public static final String WORKEXP_ID="workExp_id";
    /**
     * The constant DEBUG_TAG.
     */
    public static final String DEBUG_TAG = "logger";// LogCat的标记
    //api来源于
    public static final  String DNFROM="800hr";
    //当前使用的设备
    public static final String OS_NAME = "android";
    //api版本 正式库现在为4.0  测试库 2.0最佳
    public static final String API_VER = "4.0";
    //头像的头地址
    public static final String IMAGE_BASEPATH="http://file.800hr.com/";
    //普通图片的头地址
    public static final String IMAGE_BASEPATH2="http://img.800hr.com";
    //正式
    public static final String API_URL = "https://api.800hr.com/svrdo.php";
    public static final String API_MIDDLE="svrdo.php";
    //测试
     public static final String API_URL_Text = "https://api.800hr.com/svrdo_v0.php";
     public static final String  API_MIDDLE_TEST="svrdo.php";
    // SharedPreference中标识设备ID的字符
    public static final String DEVICE_USER_ID = "device_user_id";
    //标识是否是第一次进入app
    public static final String IS_GUIDE = "GUIDE";
    //标识是否是第一次请求验证码
    public static final String IS_FIRSTGETVALIDCODE = "ValidCode";
    // 字符集编码
    public static final String ENCODE = "utf-8";
    // SharedPreference文件名
    public static final String PREFS_NAME = "800HR_HD";
    //用户名
    public static final String USERNAME = "username";
    //手机号
    public static final String USERPHONE = "user_phone";
    //自动登录
    public static final String AUTO_LOGIN = "autoLogin1";
    //密码
    public static final String PASSWORD = "password";
    //AppCode
    public static final String APPCODE="personal";
    //sp存储的session_key名称
    public static final String SESSION_KEY_SP="session_key";
    //验证码需要的类型
    public static final String VALIDCODE_REGISTER_YTPE="1";
    public static final String VALIDCODE_LOGIN_YTPE="2";
    public static final String VALIDCODE_FINDPSW_YTPE="3";
    public static final String VALIDCODE_RESETORVALIDPHONE_YTPE="4";
    public static final String VALIDCODE_RESETPHONEORPSW_YTPE="5";
    //
    public static final String PRE_SECRET_KRY= "2cb7G62b2drGdJobcQJ1O8813aV7fds7";
    public static String INIT_SECRET_KRY = "2cb7G62b2drGdJobcQJ1O8813aV7fds7.800hr.com.800hr.";
    public static String SVR_API_VER;
    public static String SESSION_KEY;
    public static String IS_FERSH="is_fresh";
    public static String IS_NEEDSAVEWARNNING="warnning";
    // 第一次请求会话连接时用的加密密钥
    public static final String SECRET_KEY= "2cb7G62b2drGdJobcQJ1O8813aV7fds7.800hr.com.800hr.";
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.HR:
                host="https://api.800hr.com/";
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
