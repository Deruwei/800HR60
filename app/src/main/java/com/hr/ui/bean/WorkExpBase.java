package com.hr.ui.bean;

/**
 * Created by wdr on 2018/1/23.
 */

public class WorkExpBase {

    /**
     * error_code : 0
     * experience_id : 10170966
     * _run_time : 0.022368907928467
     * resume_id : 1
     */

    private int error_code;
    private String experience_id;
    private String _run_time;
    private String resume_id;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getExperience_id() {
        return experience_id;
    }

    public void setExperience_id(String experience_id) {
        this.experience_id = experience_id;
    }

    public String get_run_time() {
        return _run_time;
    }

    public void set_run_time(String _run_time) {
        this._run_time = _run_time;
    }

    public String getResume_id() {
        return resume_id;
    }

    public void setResume_id(String resume_id) {
        this.resume_id = resume_id;
    }

    @Override
    public String toString() {
        return "WorkExpBase{" +
                "error_code=" + error_code +
                ", experience_id='" + experience_id + '\'' +
                ", _run_time='" + _run_time + '\'' +
                ", resume_id='" + resume_id + '\'' +
                '}';
    }
}
