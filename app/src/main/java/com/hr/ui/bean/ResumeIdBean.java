package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/18.
 */

public class ResumeIdBean {
    private  double error_code;
    private int resume_id;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public int getResume_id() {
        return resume_id;
    }

    public void setResume_id(int resume_id) {
        this.resume_id = resume_id;
    }

    @Override
    public String toString() {
        return "ResumeIdBean{" +
                "error_code=" + error_code +
                ", resume_id=" + resume_id +
                '}';
    }
}
