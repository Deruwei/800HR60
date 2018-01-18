package com.hr.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class InviteBean implements Serializable {

    /**
     * error_code : 0
     * invited_list : [{"user_id":"5692217","enterprise_id":"Bi92A","is_new":"0","enterprise_name":"北京红根基建筑有限公司","job_name":"运营经理","job_id":"YPWEB","is_email":"1","is_sms":"0","industry":"11","email_content":"测试请忽略：\n    您好！恭喜您通过我们的面试环节！根据我们在面试时的协定，现在我们正式聘请您为我公司的【运营经理】，以下是聘用须知：\n\n录用职位：运营经理\n入职时间：2017-12-26 10:00\n联 系 人：安先生\n联系电话：010-85296341-1234,0433-8456123-1234\n乘车路线：\n\n欢迎您加入我公司，成为我们的一员。\n\n\n                                                北京红根基建筑有限公司\n                                                2017年12月25日\n职位链接：http://www.buildhr.com/job/YPWEB\n公司介绍：http://www.buildhr.com/company/Bi92A","sms_content":"","industry_name":"建筑","job_refresh_time":"2017-10-23","topjob_type":"1","company_type":"政府机关/非盈利机构","stuff_munber":"100 - 499人","record_id":"1163037","invited_title":"北京红根基建筑有限公司【运营经理】职位聘用通知书","invited_time":"2017-12-25"}]
     * navpage_info : {"current_page":"1","total_pages":1,"record_nums":1,"page_nums":"20"}
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
         * record_nums : 1
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
    }

    public static class InvitedListBean implements  Serializable{
        /**
         * user_id : 5692217
         * enterprise_id : Bi92A
         * is_new : 0
         * enterprise_name : 北京红根基建筑有限公司
         * job_name : 运营经理
         * job_id : YPWEB
         * is_email : 1
         * is_sms : 0
         * industry : 11
         * email_content : 测试请忽略：
         您好！恭喜您通过我们的面试环节！根据我们在面试时的协定，现在我们正式聘请您为我公司的【运营经理】，以下是聘用须知：

         录用职位：运营经理
         入职时间：2017-12-26 10:00
         联 系 人：安先生
         联系电话：010-85296341-1234,0433-8456123-1234
         乘车路线：

         欢迎您加入我公司，成为我们的一员。


         北京红根基建筑有限公司
         2017年12月25日
         职位链接：http://www.buildhr.com/job/YPWEB
         公司介绍：http://www.buildhr.com/company/Bi92A
         * sms_content :
         * industry_name : 建筑
         * job_refresh_time : 2017-10-23
         * topjob_type : 1
         * company_type : 政府机关/非盈利机构
         * stuff_munber : 100 - 499人
         * record_id : 1163037
         * invited_title : 北京红根基建筑有限公司【运营经理】职位聘用通知书
         * invited_time : 2017-12-25
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

        public void setInvited_time(String invited_time) {
            this.invited_time = invited_time;
        }
    }
}
