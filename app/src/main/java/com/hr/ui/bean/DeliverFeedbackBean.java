package com.hr.ui.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class DeliverFeedbackBean {

    /**
     * error_code : 0
     * applied_list : [{"user_id":"9505708","enterprise_id":"ZrDAK","job_id":"YP8dQ","job_name":"骨科主任及学科带头人","enterprise_name":"国创高科（北京）医疗科技有限公司","industry":"14","job_work_area":"1113","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 20:37","last_applied_time":"1515674249","expiration_date":"1521043199","applied_num":"4","salary":"10000 - 20000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"1 - 49人","workplace":"北京-昌平区","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411773","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"ZrDAK","job_id":"YP8dE","job_name":"骨科主任及学科带头人","enterprise_name":"国创高科（北京）医疗科技有限公司","industry":"14","job_work_area":"1100","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 20:28","last_applied_time":"1515673706","expiration_date":"1521043199","applied_num":"10","salary":"10000 - 20000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"1 - 49人","workplace":"北京","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411771","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"SeMCJ","job_id":"YPhyY","job_name":"推拿按摩技师","enterprise_name":"北京禾普诊所有限公司","industry":"14","job_work_area":"1100","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 20:26","last_applied_time":"1515673593","expiration_date":"1519833599","applied_num":"10","salary":"8000 - 20000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"50 - 99人","workplace":"北京","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411770","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"SeMCJ","job_id":"YPGB1","job_name":"医生","enterprise_name":"北京禾普诊所有限公司","industry":"14","job_work_area":"1100","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 20:23","last_applied_time":"1515673387","expiration_date":"1517414399","applied_num":"11","salary":"10000 - 30000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"50 - 99人","workplace":"北京","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411768","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"SeMCJ","job_id":"YPhyI","job_name":"推拿按摩技师","enterprise_name":"北京禾普诊所有限公司","industry":"14","job_work_area":"1105","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 20:21","last_applied_time":"1515673265","expiration_date":"1519833599","applied_num":"9","salary":"8000 - 20000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"50 - 99人","workplace":"北京-朝阳区","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411767","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"SeZEZ","job_id":"YP0Ci","job_name":"医药销售人员","enterprise_name":"北京创盟精准医学科技有限公司","industry":"14","job_work_area":"1100","read_t 01-15 09:41:56.735 13468-13557/com.hr.ui D/OkHttp: ime":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 19:29","last_applied_time":"1515670147","expiration_date":"1523203199","applied_num":"8","salary":"3000 - 9000","topjob_type":"1","company_type":"","stuff_munber":"50 - 99人","workplace":"北京","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411764","is_favourite":0,"is_expire":0,"is_apply":1},{"user_id":"9505708","enterprise_id":"SeMCJ","job_id":"YPGBG","job_name":"医生","enterprise_name":"北京禾普诊所有限公司","industry":"14","job_work_area":"1105","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-11 19:22","last_applied_time":"1515669744","expiration_date":"1517414399","applied_num":"8","salary":"10000 - 30000","topjob_type":"1","company_type":"私营/民营企业","stuff_munber":"50 - 99人","workplace":"北京-朝阳区","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411763","is_favourite":1,"is_expire":0,"is_apply":1}]
     * navpage_info : {"current_page":"1","total_pages":1,"record_nums":7,"page_nums":"20"}
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<AppliedListBean> applied_list;

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

    public List<AppliedListBean> getApplied_list() {
        return applied_list;
    }

    public void setApplied_list(List<AppliedListBean> applied_list) {
        this.applied_list = applied_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 1
         * record_nums : 7
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

    public static class AppliedListBean {
        /**
         * user_id : 9505708
         * enterprise_id : ZrDAK
         * job_id : YP8dQ
         * job_name : 骨科主任及学科带头人
         * enterprise_name : 国创高科（北京）医疗科技有限公司
         * industry : 14
         * job_work_area : 1113
         * read_time :
         * invite_time :
         * invite_id : 0
         * applied_time : 2018-01-11 20:37
         * last_applied_time : 1515674249
         * expiration_date : 1521043199
         * applied_num : 4
         * salary : 10000 - 20000
         * topjob_type : 1
         * company_type : 私营/民营企业
         * stuff_munber : 1 - 49人
         * workplace : 北京-昌平区
         * resume_list : [{"title":"我的简历","resume_language":"zh","resume_id":"1"}]
         * record_id : 318411773
         * is_favourite : 0
         * is_expire : 0
         * is_apply : 1
         * read_t 01-15 09:41:56.735 13468-13557/com.hr.ui D/OkHttp: ime :
         */

        private String user_id;
        private String enterprise_id;
        private String job_id;
        private String job_name;
        private String enterprise_name;
        private String industry;
        private String job_work_area;
        private String read_time;
        private String invite_time;
        private String invite_id;
        private String applied_time;
        private String last_applied_time;
        private String expiration_date;
        private String applied_num;
        private String salary;
        private String topjob_type;
        private String company_type;
        private String stuff_munber;
        private String workplace;
        private String record_id;
        private int is_favourite;
        private int is_expire;
        private int is_apply;
        @SerializedName("read_t 01-15 09:41:56.735 13468-13557/com.hr.ui D/OkHttp: ime")
        private String _$Read_t01150941567351346813557ComHrUiDOkHttpIme217; // FIXME check this code
        private List<ResumeListBean> resume_list;

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

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getJob_work_area() {
            return job_work_area;
        }

        public void setJob_work_area(String job_work_area) {
            this.job_work_area = job_work_area;
        }

        public String getRead_time() {
            return read_time;
        }

        public void setRead_time(String read_time) {
            this.read_time = read_time;
        }

        public String getInvite_time() {
            return invite_time;
        }

        public void setInvite_time(String invite_time) {
            this.invite_time = invite_time;
        }

        public String getInvite_id() {
            return invite_id;
        }

        public void setInvite_id(String invite_id) {
            this.invite_id = invite_id;
        }

        public String getApplied_time() {
            return applied_time;
        }

        public void setApplied_time(String applied_time) {
            this.applied_time = applied_time;
        }

        public String getLast_applied_time() {
            return last_applied_time;
        }

        public void setLast_applied_time(String last_applied_time) {
            this.last_applied_time = last_applied_time;
        }

        public String getExpiration_date() {
            return expiration_date;
        }

        public void setExpiration_date(String expiration_date) {
            this.expiration_date = expiration_date;
        }

        public String getApplied_num() {
            return applied_num;
        }

        public void setApplied_num(String applied_num) {
            this.applied_num = applied_num;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
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

        public String getWorkplace() {
            return workplace;
        }

        public void setWorkplace(String workplace) {
            this.workplace = workplace;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public int getIs_favourite() {
            return is_favourite;
        }

        public void setIs_favourite(int is_favourite) {
            this.is_favourite = is_favourite;
        }

        public int getIs_expire() {
            return is_expire;
        }

        public void setIs_expire(int is_expire) {
            this.is_expire = is_expire;
        }

        public int getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(int is_apply) {
            this.is_apply = is_apply;
        }

        public String get_$Read_t01150941567351346813557ComHrUiDOkHttpIme217() {
            return _$Read_t01150941567351346813557ComHrUiDOkHttpIme217;
        }

        public void set_$Read_t01150941567351346813557ComHrUiDOkHttpIme217(String _$Read_t01150941567351346813557ComHrUiDOkHttpIme217) {
            this._$Read_t01150941567351346813557ComHrUiDOkHttpIme217 = _$Read_t01150941567351346813557ComHrUiDOkHttpIme217;
        }

        public List<ResumeListBean> getResume_list() {
            return resume_list;
        }

        public void setResume_list(List<ResumeListBean> resume_list) {
            this.resume_list = resume_list;
        }

        public static class ResumeListBean {
            /**
             * title : 我的简历
             * resume_language : zh
             * resume_id : 1
             */

            private String title;
            private String resume_language;
            private String resume_id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }
        }
    }
}
