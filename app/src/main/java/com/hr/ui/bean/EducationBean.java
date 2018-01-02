package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/2.
 */

public class EducationBean {

    /**
     * error_code : 0
     * education_list : [{"user_id":"9495365","fromyear":"2015","frommonth":"9","toyear":"2016","tomonth":"10","schoolname":"jdjdjj","moremajor":"hdjdjdj","degree":"16","edudetail":"","is_overseas":"0","country":"0","resume_id":"1","resume_language":"zh","recruit_students":"0","degreecerti_no":"","a_degreecerti_no":"","education_id":"9567635"}]
     * _run_time : 0.0078420639038086
     */

    private int error_code;
    private String _run_time;
    private List<EducationListBean> education_list;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String get_run_time() {
        return _run_time;
    }

    public void set_run_time(String _run_time) {
        this._run_time = _run_time;
    }

    public List<EducationListBean> getEducation_list() {
        return education_list;
    }

    public void setEducation_list(List<EducationListBean> education_list) {
        this.education_list = education_list;
    }

    public static class EducationListBean {
        /**
         * user_id : 9495365
         * fromyear : 2015
         * frommonth : 9
         * toyear : 2016
         * tomonth : 10
         * schoolname : jdjdjj
         * moremajor : hdjdjdj
         * degree : 16
         * edudetail :
         * is_overseas : 0
         * country : 0
         * resume_id : 1
         * resume_language : zh
         * recruit_students : 0
         * degreecerti_no :
         * a_degreecerti_no :
         * education_id : 9567635
         */

        private String user_id;
        private String fromyear;
        private String frommonth;
        private String toyear;
        private String tomonth;
        private String schoolname;
        private String moremajor;
        private String degree;
        private String edudetail;
        private String is_overseas;
        private String country;
        private String resume_id;
        private String resume_language;
        private String recruit_students;
        private String degreecerti_no;
        private String a_degreecerti_no;
        private String education_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFromyear() {
            return fromyear;
        }

        public void setFromyear(String fromyear) {
            this.fromyear = fromyear;
        }

        public String getFrommonth() {
            return frommonth;
        }

        public void setFrommonth(String frommonth) {
            this.frommonth = frommonth;
        }

        public String getToyear() {
            return toyear;
        }

        public void setToyear(String toyear) {
            this.toyear = toyear;
        }

        public String getTomonth() {
            return tomonth;
        }

        public void setTomonth(String tomonth) {
            this.tomonth = tomonth;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getMoremajor() {
            return moremajor;
        }

        public void setMoremajor(String moremajor) {
            this.moremajor = moremajor;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getEdudetail() {
            return edudetail;
        }

        public void setEdudetail(String edudetail) {
            this.edudetail = edudetail;
        }

        public String getIs_overseas() {
            return is_overseas;
        }

        public void setIs_overseas(String is_overseas) {
            this.is_overseas = is_overseas;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getResume_id() {
            return resume_id;
        }

        public void setResume_id(String resume_id) {
            this.resume_id = resume_id;
        }

        public String getResume_language() {
            return resume_language;
        }

        public void setResume_language(String resume_language) {
            this.resume_language = resume_language;
        }

        public String getRecruit_students() {
            return recruit_students;
        }

        public void setRecruit_students(String recruit_students) {
            this.recruit_students = recruit_students;
        }

        public String getDegreecerti_no() {
            return degreecerti_no;
        }

        public void setDegreecerti_no(String degreecerti_no) {
            this.degreecerti_no = degreecerti_no;
        }

        public String getA_degreecerti_no() {
            return a_degreecerti_no;
        }

        public void setA_degreecerti_no(String a_degreecerti_no) {
            this.a_degreecerti_no = a_degreecerti_no;
        }

        public String getEducation_id() {
            return education_id;
        }

        public void setEducation_id(String education_id) {
            this.education_id = education_id;
        }
    }
}
