package com.hr.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class InviteBean implements Serializable {

    /**
     * error_code : 0
     * invited_list : [{"user_id":"5692217","enterprise_id":"5bwS5","is_new":"1","enterprise_name":"金坛区尧塘绿源环境艺术设计中心","job_name":"施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师","job_id":"QYAnV","is_email":"1","is_sms":"0","industry":"11","email_content":"安易耀久：\n    您好！您的简历已收到，并通过了我们的初次筛选，很荣幸通知您来参加面试。具体事宜如下：\n\n面试职位：施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师\n面试时间：2018-02-20 10:00\n面试地点：8687972@address\n联 系 人：张先生\n联系电话：8687972@phone\n乘车路线：\n\n                                                金坛区尧塘绿源环境艺术设计中心\n                                                2018年02月02日\n职位链接：http://www.buildhr.com/job/QYAnV\n公司介绍：http://www.buildhr.com/company/5bwS5","sms_content":"","industry_name":"建筑","job_refresh_time":"2018-01-17","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"1 - 49人","record_id":"1164166","invited_title":"金坛区尧塘绿源环境艺术设计中心邀请您面试【施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师】一职","invited_time":"2018-02-02"}]
     * navpage_info : {"current_page":"1","total_pages":1,"record_nums":10,"page_nums":"20"}
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<InvitedListBean> invited_list;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public NavpageInfoBean getNavpage_info() {
        return navpage_info;
    }

    public void setNavpage_info(NavpageInfoBean navpage_info) {
        this.navpage_info = navpage_info;
    }

    public List<InvitedListBean> getInvited_list() {
        return invited_list;
    }

    public void setInvited_list(List<InvitedListBean> invited_list) {
        this.invited_list = invited_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 1
         * record_nums : 10
         * page_nums : 20
         */

        private String current_page;
        private int total_pages;
        private int record_nums;
        private String page_nums;

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public int getRecord_nums() {
            return record_nums;
        }

        public void setRecord_nums(int record_nums) {
            this.record_nums = record_nums;
        }

        public String getPage_nums() {
            return page_nums;
        }

        public void setPage_nums(String page_nums) {
            this.page_nums = page_nums;
        }

        @Override
        public String toString() {
            return "NavpageInfoBean{" +
                    "current_page='" + current_page + '\'' +
                    ", total_pages=" + total_pages +
                    ", record_nums=" + record_nums +
                    ", page_nums='" + page_nums + '\'' +
                    '}';
        }
    }

    public static class InvitedListBean implements Serializable{
        /**
         * user_id : 5692217
         * enterprise_id : 5bwS5
         * is_new : 1
         * enterprise_name : 金坛区尧塘绿源环境艺术设计中心
         * job_name : 施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师
         * job_id : QYAnV
         * is_email : 1
         * is_sms : 0
         * industry : 11
         * email_content : 安易耀久：
         您好！您的简历已收到，并通过了我们的初次筛选，很荣幸通知您来参加面试。具体事宜如下：

         面试职位：施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师
         面试时间：2018-02-20 10:00
         面试地点：8687972@address
         联 系 人：张先生
         联系电话：8687972@phone
         乘车路线：

         金坛区尧塘绿源环境艺术设计中心
         2018年02月02日
         职位链接：http://www.buildhr.com/job/QYAnV
         公司介绍：http://www.buildhr.com/company/5bwS5
         * sms_content :
         * industry_name : 建筑
         * job_refresh_time : 2018-01-17
         * topjob_type : 1
         * company_type : 私营/民营企业
         * stuff_munber : 1 - 49人
         * record_id : 1164166
         * invited_title : 金坛区尧塘绿源环境艺术设计中心邀请您面试【施工图设计师施工图设计师施工图设计师施工图设计师施工图设计师】一职
         * invited_time : 2018-02-02
         */

        private String user_id;
        private String enterprise_id;
        private String is_new;
        private String enterprise_name;
        private String job_name;
        private String job_id;
        private String is_email;
        private String is_sms;
        private String industry;
        private String email_content;
        private String sms_content;
        private String industry_name;
        private String job_refresh_time;
        private String topjob_type;
        private String company_type;
        private String stuff_munber;
        private String record_id;
        private String invited_title;
        private String invited_time;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getIs_email() {
            return is_email;
        }

        public void setIs_email(String is_email) {
            this.is_email = is_email;
        }

        public String getIs_sms() {
            return is_sms;
        }

        public void setIs_sms(String is_sms) {
            this.is_sms = is_sms;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getEmail_content() {
            return email_content;
        }

        public void setEmail_content(String email_content) {
            this.email_content = email_content;
        }

        public String getSms_content() {
            return sms_content;
        }

        public void setSms_content(String sms_content) {
            this.sms_content = sms_content;
        }

        public String getIndustry_name() {
            return industry_name;
        }

        public void setIndustry_name(String industry_name) {
            this.industry_name = industry_name;
        }

        public String getJob_refresh_time() {
            return job_refresh_time;
        }

        public void setJob_refresh_time(String job_refresh_time) {
            this.job_refresh_time = job_refresh_time;
        }

        public String getTopjob_type() {
            return topjob_type;
        }

        public void setTopjob_type(String topjob_type) {
            this.topjob_type = topjob_type;
        }

        public String getCompany_type() {
            return company_type;
        }

        public void setCompany_type(String company_type) {
            this.company_type = company_type;
        }

        public String getStuff_munber() {
            return stuff_munber;
        }

        public void setStuff_munber(String stuff_munber) {
            this.stuff_munber = stuff_munber;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public String getInvited_title() {
            return invited_title;
        }

        public void setInvited_title(String invited_title) {
            this.invited_title = invited_title;
        }

        public String getInvited_time() {
            return invited_time;
        }

        public void setInvited_time(String invited_time){
            this.invited_time=invited_time;
        }

        @Override
        public String toString() {
            return "InvitedListBean{" +
                    "user_id='" + user_id + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", is_new='" + is_new + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", job_name='" + job_name + '\'' +
                    ", job_id='" + job_id + '\'' +
                    ", is_email='" + is_email + '\'' +
                    ", is_sms='" + is_sms + '\'' +
                    ", industry='" + industry + '\'' +
                    ", email_content='" + email_content + '\'' +
                    ", sms_content='" + sms_content + '\'' +
                    ", industry_name='" + industry_name + '\'' +
                    ", job_refresh_time='" + job_refresh_time + '\'' +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", stuff_munber='" + stuff_munber + '\'' +
                    ", record_id='" + record_id + '\'' +
                    ", invited_title='" + invited_title + '\'' +
                    ", invited_time='" + invited_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InviteBean{" +
                "error_code='" + error_code + '\'' +
                ", navpage_info=" + navpage_info +
                ", invited_list=" + invited_list +
                '}';
    }
}
