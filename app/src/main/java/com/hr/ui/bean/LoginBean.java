package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/12.
 */
@Entity
public class LoginBean {
    @Id
    private long loginType;//0手机登录 1用户登录 2QQ登录 3微信登录
    private String name;
    private String password;
    private String thirdPartLoginType;
    private String thirdPartUid;
    private String thirdPartSUid;
    @Generated(hash = 922374866)
    public LoginBean(long loginType, String name, String password,
            String thirdPartLoginType, String thirdPartUid, String thirdPartSUid) {
        this.loginType = loginType;
        this.name = name;
        this.password = password;
        this.thirdPartLoginType = thirdPartLoginType;
        this.thirdPartUid = thirdPartUid;
        this.thirdPartSUid = thirdPartSUid;
    }
    @Generated(hash = 1112702939)
    public LoginBean() {
    }
    public long getLoginType() {
        return this.loginType;
    }
    public void setLoginType(long loginType) {
        this.loginType = loginType;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getThirdPartLoginType() {
        return this.thirdPartLoginType;
    }
    public void setThirdPartLoginType(String thirdPartLoginType) {
        this.thirdPartLoginType = thirdPartLoginType;
    }
    public String getThirdPartUid() {
        return this.thirdPartUid;
    }
    public void setThirdPartUid(String thirdPartUid) {
        this.thirdPartUid = thirdPartUid;
    }
    public String getThirdPartSUid() {
        return this.thirdPartSUid;
    }
    public void setThirdPartSUid(String thirdPartSUid) {
        this.thirdPartSUid = thirdPartSUid;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginType=" + loginType +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", thirdPartLoginType='" + thirdPartLoginType + '\'' +
                ", thirdPartUid='" + thirdPartUid + '\'' +
                ", thirdPartSUid='" + thirdPartSUid + '\'' +
                '}';
    }
}
