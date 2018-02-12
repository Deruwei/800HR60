package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class DeliverFeedbackBean {


    /**
     * error_code : 0
     * applied_list : [{"user_id":"9505733","enterprise_id":"Bi92A","job_id":"I9NYp","job_name":"我的职位十月","enterprise_name":"北京红根基建筑有限公司","industry":"11","isnew":"0","job_work_area":"1609","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-29 16:05","last_applied_time":"1517213155","expiration_date":"0","applied_num":"0","salary":"","company_type":"政府机关/非盈利机构","stuff_munber":"100 - 499人","workplace":"江苏-南通市","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318412009","is_favourite":0,"is_expire":1,"is_apply":1},{"user_id":"9505733","enterprise_id":"Bi92A","job_id":"I9NZF","job_name":"我的职位十月","enterprise_name":"北京红根基建筑有限公司","industry":"11","isnew":"0","job_work_area":"1613","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-29 16:04","last_applied_time":"1517213040","expiration_date":"0","applied_num":"0","salary":"","company_type":"政府机关/非盈利机构","stuff_munber":"100 - 499人","workplace":"江苏-苏州市","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318412008","is_favourite":0,"is_expire":1,"is_apply":1},{"user_id":"9505733","enterprise_id":"i3inm","job_id":"I94Xz","job_name":"临床研究员","enterprise_name":"北京欧博方医药科技有限公司","industry":"14","isnew":"0","job_work_area":"1100","read_time":"","invite_time":"","invite_id":"0","applied_time":"2018-01-24 17:16","last_applied_time":"1516785395","expiration_date":"1541779199","applied_num":"5","salary":"10000 - 15000","topjob_type":"1","release_time":"1511884800","study":"本科及以上","workyear":"五年以上","language":"英语良好","post_rank":"","company_type":"私营/民营企业","stuff_munber":"100 - 499人","workplace":"北京","resume_list":[{"title":"我的简历","resume_language":"zh","resume_id":"1"}],"record_id":"318411942","is_favourite":0,"is_expire":0,"is_apply":0}]
     * navpage_info : {"current_page":"1","total_pages":1,"record_nums":3,"page_nums":"20"}
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
         * record_nums : 3
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

    public static class AppliedListBean {
        /**
         * user_id : 9505733
         * enterprise_id : Bi92A
         * job_id : I9NYp
         * job_name : 我的职位十月
         * enterprise_name : 北京红根基建筑有限公司
         * industry : 11
         * isnew : 0
         * job_work_area : 1609
         * read_time :
         * invite_time :
         * invite_id : 0
         * applied_time : 2018-01-29 16:05
         * last_applied_time : 1517213155
         * expiration_date : 0
         * applied_num : 0
         * salary :
         * company_type : 政府机关/非盈利机构
         * stuff_munber : 100 - 499人
         * workplace : 江苏-南通市
         * resume_list : [{"title":"我的简历","resume_language":"zh","resume_id":"1"}]
         * record_id : 318412009
         * is_favourite : 0
         * is_expire : 1
         * is_apply : 1
         * topjob_type : 1
         * release_time : 1511884800
         * study : 本科及以上
         * workyear : 五年以上
         * language : 英语良好
         * post_rank :
         */

        private String user_id;
        private String enterprise_id;
        private String job_id;
        private String job_name;
        private String enterprise_name;
        private String industry;
        private String isnew;
        private String job_work_area;
        private String read_time;
        private String invite_time;
        private String invite_id;
        private String applied_time;
        private String last_applied_time;
        private String expiration_date;
        private String applied_num;
        private String salary;
        private String company_type;
        private String stuff_munber;
        private String workplace;
        private String record_id;
        private int is_favourite;
        private int is_expire;
        private int is_apply;
        private String topjob_type;
        private String release_time;
        private String study;
        private String workyear;
        private String language;
        private String post_rank;
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

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
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

        public String getTopjob_type() {
            return topjob_type;
        }

        public void setTopjob_type(String topjob_type) {
            this.topjob_type = topjob_type;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getStudy() {
            return study;
        }

        public void setStudy(String study) {
            this.study = study;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPost_rank() {
            return post_rank;
        }

        public void setPost_rank(String post_rank) {
            this.post_rank = post_rank;
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

            @Override
            public String toString() {
                return "ResumeListBean{" +
                        "title='" + title + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        '}';
            }

        }

        @Override
        public String toString() {
            return "AppliedListBean{" +
                    "user_id='" + user_id + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", job_id='" + job_id + '\'' +
                    ", job_name='" + job_name + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", industry='" + industry + '\'' +
                    ", isnew='" + isnew + '\'' +
                    ", job_work_area='" + job_work_area + '\'' +
                    ", read_time='" + read_time + '\'' +
                    ", invite_time='" + invite_time + '\'' +
                    ", invite_id='" + invite_id + '\'' +
                    ", applied_time='" + applied_time + '\'' +
                    ", last_applied_time='" + last_applied_time + '\'' +
                    ", expiration_date='" + expiration_date + '\'' +
                    ", applied_num='" + applied_num + '\'' +
                    ", salary='" + salary + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", stuff_munber='" + stuff_munber + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", record_id='" + record_id + '\'' +
                    ", is_favourite=" + is_favourite +
                    ", is_expire=" + is_expire +
                    ", is_apply=" + is_apply +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", release_time='" + release_time + '\'' +
                    ", study='" + study + '\'' +
                    ", workyear='" + workyear + '\'' +
                    ", language='" + language + '\'' +
                    ", post_rank='" + post_rank + '\'' +
                    ", resume_list=" + resume_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeliverFeedbackBean{" +
                "error_code='" + error_code + '\'' +
                ", navpage_info=" + navpage_info +
                ", applied_list=" + applied_list +
                '}';
    }
}
