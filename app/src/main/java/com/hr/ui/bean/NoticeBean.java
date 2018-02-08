package com.hr.ui.bean;

/**
 * Created by wdr on 2018/2/8.
 */

public class NoticeBean {

    /**
     * error_code : 0
     * notice_info : {"phonecode":"wifi02_00_00_00_00_00","industry":"11","func":"","area":"1200","searchword":"旅游规划","rushjob_state":"1","invite_state":"1","sound_state":"2","notice_bgntime":"8:00","notice_endtime":"22:00","os_name":"android","baidu_user_id":"null","baidu_channel_id":"null","workyear":"0","worktype":"0","issuedate":"0","study":"0","stuffmunber":"0","salary":"0","push_way":"2"}
     */

    private String error_code;
    private NoticeInfoBean notice_info;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public NoticeInfoBean getNotice_info() {
        return notice_info;
    }

    public void setNotice_info(NoticeInfoBean notice_info) {
        this.notice_info = notice_info;
    }

    public static class NoticeInfoBean {
        /**
         * phonecode : wifi02_00_00_00_00_00
         * industry : 11
         * func :
         * area : 1200
         * searchword : 旅游规划
         * rushjob_state : 1
         * invite_state : 1
         * sound_state : 2
         * notice_bgntime : 8:00
         * notice_endtime : 22:00
         * os_name : android
         * baidu_user_id : null
         * baidu_channel_id : null
         * workyear : 0
         * worktype : 0
         * issuedate : 0
         * study : 0
         * stuffmunber : 0
         * salary : 0
         * push_way : 2
         */

        private String phonecode;
        private String industry;
        private String func;
        private String area;
        private String searchword;
        private String rushjob_state;
        private String invite_state;
        private String sound_state;
        private String notice_bgntime;
        private String notice_endtime;
        private String os_name;
        private String baidu_user_id;
        private String baidu_channel_id;
        private String workyear;
        private String worktype;
        private String issuedate;
        private String study;
        private String stuffmunber;
        private String salary;
        private String push_way;

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSearchword() {
            return searchword;
        }

        public void setSearchword(String searchword) {
            this.searchword = searchword;
        }

        public String getRushjob_state() {
            return rushjob_state;
        }

        public void setRushjob_state(String rushjob_state) {
            this.rushjob_state = rushjob_state;
        }

        public String getInvite_state() {
            return invite_state;
        }

        public void setInvite_state(String invite_state) {
            this.invite_state = invite_state;
        }

        public String getSound_state() {
            return sound_state;
        }

        public void setSound_state(String sound_state) {
            this.sound_state = sound_state;
        }

        public String getNotice_bgntime() {
            return notice_bgntime;
        }

        public void setNotice_bgntime(String notice_bgntime) {
            this.notice_bgntime = notice_bgntime;
        }

        public String getNotice_endtime() {
            return notice_endtime;
        }

        public void setNotice_endtime(String notice_endtime) {
            this.notice_endtime = notice_endtime;
        }

        public String getOs_name() {
            return os_name;
        }

        public void setOs_name(String os_name) {
            this.os_name = os_name;
        }

        public String getBaidu_user_id() {
            return baidu_user_id;
        }

        public void setBaidu_user_id(String baidu_user_id) {
            this.baidu_user_id = baidu_user_id;
        }

        public String getBaidu_channel_id() {
            return baidu_channel_id;
        }

        public void setBaidu_channel_id(String baidu_channel_id) {
            this.baidu_channel_id = baidu_channel_id;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getWorktype() {
            return worktype;
        }

        public void setWorktype(String worktype) {
            this.worktype = worktype;
        }

        public String getIssuedate() {
            return issuedate;
        }

        public void setIssuedate(String issuedate) {
            this.issuedate = issuedate;
        }

        public String getStudy() {
            return study;
        }

        public void setStudy(String study) {
            this.study = study;
        }

        public String getStuffmunber() {
            return stuffmunber;
        }

        public void setStuffmunber(String stuffmunber) {
            this.stuffmunber = stuffmunber;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getPush_way() {
            return push_way;
        }

        public void setPush_way(String push_way) {
            this.push_way = push_way;
        }
    }
}
