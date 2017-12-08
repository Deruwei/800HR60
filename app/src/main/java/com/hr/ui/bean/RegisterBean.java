package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/6.
 */

public class RegisterBean {
    private double error_code;
    private  int user_id;
    private int industry;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "error_code=" + error_code +
                ", user_id=" + user_id +
                ", industry=" + industry +
                '}';
    }
}
