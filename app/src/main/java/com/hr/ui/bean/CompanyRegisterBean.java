package com.hr.ui.bean;

/**
 * Created by wdr on 2018/2/6.
 */

public class CompanyRegisterBean {
    private String siteCode;
    private String userName;
    private String password;
    private String passwordRe;
    private String enterpriseName;
    private String linkMan;
    private String howToknow;
    private String phone;
    private String email;

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRe() {
        return passwordRe;
    }

    public void setPasswordRe(String passwordRe) {
        this.passwordRe = passwordRe;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getHowToknow() {
        return howToknow;
    }

    public void setHowToknow(String howToknow) {
        this.howToknow = howToknow;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CompanyRegisterBean{" +
                "siteCode='" + siteCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", passwordRe='" + passwordRe + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", howToknow='" + howToknow + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
