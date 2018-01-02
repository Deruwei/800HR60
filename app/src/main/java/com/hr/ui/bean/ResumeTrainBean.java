package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeTrainBean {
    /**
     * error_code : 0
     * plant_list : [{"user_id":"9495365","fromyear":"2015","frommonth":"3","toyear":"2017","tomonth":"6","institution":"2222","place":"1613","course":"2222","certification":"2eeee","traindetail":"2222","resume_id":"1","resume_language":"zh","certi_number":"","plant_id":"1943046"}]
     * _run_time : 0.0057268142700195
     */

    private int error_code;
    private String _run_time;
    private List<PlantListBean> plant_list;

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

    public List<PlantListBean> getPlant_list() {
        return plant_list;
    }

    public void setPlant_list(List<PlantListBean> plant_list) {
        this.plant_list = plant_list;
    }

    public static class PlantListBean {
        /**
         * user_id : 9495365
         * fromyear : 2015
         * frommonth : 3
         * toyear : 2017
         * tomonth : 6
         * institution : 2222
         * place : 1613
         * course : 2222
         * certification : 2eeee
         * traindetail : 2222
         * resume_id : 1
         * resume_language : zh
         * certi_number :
         * plant_id : 1943046
         */

        private String user_id;
        private String fromyear;
        private String frommonth;
        private String toyear;
        private String tomonth;
        private String institution;
        private String place;
        private String course;
        private String certification;
        private String traindetail;
        private String resume_id;
        private String resume_language;
        private String certi_number;
        private String plant_id;

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

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getCertification() {
            return certification;
        }

        public void setCertification(String certification) {
            this.certification = certification;
        }

        public String getTraindetail() {
            return traindetail;
        }

        public void setTraindetail(String traindetail) {
            this.traindetail = traindetail;
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

        public String getCerti_number() {
            return certi_number;
        }

        public void setCerti_number(String certi_number) {
            this.certi_number = certi_number;
        }

        public String getPlant_id() {
            return plant_id;
        }

        public void setPlant_id(String plant_id) {
            this.plant_id = plant_id;
        }
    }
}
