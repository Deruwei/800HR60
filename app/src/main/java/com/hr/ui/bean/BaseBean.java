package com.hr.ui.bean;

/**
 * Created by wdr on 2017/11/14.
 */

public class BaseBean {

    /**
     * error_code : 0
     * secret_key : f13fda22d12e2839
     * session_key : e34125k2vsbcb3rc8orb8jg7o22b71cf924f6a4941568eb58a3f11664e
     * svr_api_ver : 1.1
     */

    private double error_code;
    private String secret_key;
    private String session_key;
    private String svr_api_ver;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getSvr_api_ver() {
        return svr_api_ver;
    }

    public void setSvr_api_ver(String svr_api_ver) {
        this.svr_api_ver = svr_api_ver;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "error_code=" + error_code +
                ", secret_key='" + secret_key + '\'' +
                ", session_key='" + session_key + '\'' +
                ", svr_api_ver='" + svr_api_ver + '\'' +
                '}';
    }
}
