package com.hr.ui.bean;

public class ResultBean {
    private String error_code;
    private OrigenBean origenBean;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public OrigenBean getOrigenBean() {
        return origenBean;
    }

    public void setOrigenBean(OrigenBean origenBean) {
        this.origenBean = origenBean;
    }
}
