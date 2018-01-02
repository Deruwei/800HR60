package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/2.
 */

public class ProjectBean {

    /**
     * error_code : 0
     * project_list : [{"user_id":"9495365","fromyear":"2013","frommonth":"3","toyear":"2015","tomonth":"1","projectname":"ffffff","projectdesc":"fffffff","responsibility":"ffffffff","position":"ffff","resume_id":"1","resume_language":"zh","companyname":"","companyhide":"0","achievement":"","reterence":"","project_id":"4556647"}]
     * _run_time : 0.0054590702056885
     */

    private int error_code;
    private String _run_time;
    private List<ProjectListBean> project_list;

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

    public List<ProjectListBean> getProject_list() {
        return project_list;
    }

    public void setProject_list(List<ProjectListBean> project_list) {
        this.project_list = project_list;
    }

    public static class ProjectListBean {
        /**
         * user_id : 9495365
         * fromyear : 2013
         * frommonth : 3
         * toyear : 2015
         * tomonth : 1
         * projectname : ffffff
         * projectdesc : fffffff
         * responsibility : ffffffff
         * position : ffff
         * resume_id : 1
         * resume_language : zh
         * companyname :
         * companyhide : 0
         * achievement :
         * reterence :
         * project_id : 4556647
         */

        private String user_id;
        private String fromyear;
        private String frommonth;
        private String toyear;
        private String tomonth;
        private String projectname;
        private String projectdesc;
        private String responsibility;
        private String position;
        private String resume_id;
        private String resume_language;
        private String companyname;
        private String companyhide;
        private String achievement;
        private String reterence;
        private String project_id;

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

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getProjectdesc() {
            return projectdesc;
        }

        public void setProjectdesc(String projectdesc) {
            this.projectdesc = projectdesc;
        }

        public String getResponsibility() {
            return responsibility;
        }

        public void setResponsibility(String responsibility) {
            this.responsibility = responsibility;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getCompanyhide() {
            return companyhide;
        }

        public void setCompanyhide(String companyhide) {
            this.companyhide = companyhide;
        }

        public String getAchievement() {
            return achievement;
        }

        public void setAchievement(String achievement) {
            this.achievement = achievement;
        }

        public String getReterence() {
            return reterence;
        }

        public void setReterence(String reterence) {
            this.reterence = reterence;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }
    }
}
