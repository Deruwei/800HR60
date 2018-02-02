package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/6.
 */

public class NoDataBean {
    private double error_code;
    private String error_field;

    public String getError_field() {
        return error_field;
    }

    public void setError_field(String error_field) {
        this.error_field = error_field;
    }

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "NoDataBean{" +
                "error_code=" + error_code +
                '}';
    }
}
