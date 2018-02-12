package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/5.
 */

public class ValidCodeBean {
    public int error_code;
    private String error_field;
    public int token_times;

    public String getError_field() {
        return error_field;
    }

    public void setError_field(String error_field) {
        this.error_field = error_field;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
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
