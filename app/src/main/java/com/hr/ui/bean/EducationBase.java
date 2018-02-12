package com.hr.ui.bean;

/**
 * Created by wdr on 2018/1/23.
 */

public class EducationBase {

    /**
     * error_code : 0
     * education_id : 9572761
     * _run_time : 0.024403810501099
     * resume_id : 1
     */

    private int error_code;
    private String education_id;
    private String _run_time;
    private String resume_id;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getEducation_id() {
        return education_id;
    }

    public void setEducation_id(String education_id) {
        this.education_id = education_id;
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
        return "EducationBase{" +
                "error_code=" + error_code +
                ", education_id='" + education_id + '\'' +
                ", _run_time='" + _run_time + '\'' +
                ", resume_id='" + resume_id + '\'' +
                '}';
    }
}
