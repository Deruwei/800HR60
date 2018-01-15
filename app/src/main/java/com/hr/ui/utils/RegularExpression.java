package com.hr.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wdr on 2017/12/5.
 */

public class RegularExpression {
    /**
     * 验证是否是正确的手机号码
     * @param str
     * @return
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(19[8,9])|(16[6])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证是否邮箱格式正确
     * @param str
     * @return
     */
    //"^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
    public static boolean isEmail(String str){
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证密码是否是6-16位的a-zA-Z0-9类型的密码
     * @param psw
     * @return
     */
    //"^[a-zA-Z0-9]{6,16}$"
    public static boolean isPsw(String psw){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(psw);
        return matcher.matches();
    }
}
