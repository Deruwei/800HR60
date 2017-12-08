package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/5.
 */

public class ValidCodeBean {
    public double error_code;
    public int token_times;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public int getToken_times() {
        return token_times;
    }

    public void setToken_times(int token_times) {
        this.token_times = token_times;
    }

    @Override
    public String toString() {
        return "ValidCodeBean{" +
                "error_code=" + error_code +
                ", token_times=" + token_times +
                '}';
    }
}
