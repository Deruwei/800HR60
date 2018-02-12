package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/12.
 */

public class CollectionBean {


    /**
     * error_code : 0
     * favourite_list : [{"user_id":"5692217","enterprise_id":"ciaTs","job_id":"YPFCN","job_name":"地铁项目总监","enterprise_name":"上海市建设工程监理咨询有限公司","industry":"11","job_work_area":"2102","expiration_date":"1542556799","applied_num":"8","release_time":"2018-02-05","topjob_type":"1","study":"本科及以上","workyear":"十年以上","language":"","post_rank":"","salary":"20000 - 25000","applied_time":"0","workplace":"山东-青岛市","record_id":"319689283","favourite_time":"2018-02-11","is_expire":0,"is_apply":0},{"user_id":"5692217","enterprise_id":"Kmmvx","job_id":"reYQg","job_name":"建筑学实习生","enterprise_name":"青岛理工大学建筑设计研究院","industry":"11","job_work_area":"2102","expiration_date":"1524758399","applied_num":"167","release_time":"2017-04-27","topjob_type":"1","study":"不限","workyear":"不限","language":"","post_rank":"","salary":"面议","applied_time":"0","workplace":"山东-青岛市","record_id":"319685958","favourite_time":"2018-02-11","is_expire":0,"is_apply":0}]
     * navpage_info : {"current_page":"1","total_pages":2,"record_nums":33,"page_nums":"20"}
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<FavouriteListBean> favourite_list;

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

    public List<FavouriteListBean> getFavourite_list() {
        return favourite_list;
    }

    public void setFavourite_list(List<FavouriteListBean> favourite_list) {
        this.favourite_list = favourite_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 2
         * record_nums : 33
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

    public static class FavouriteListBean {
        /**
         * user_id : 5692217
         * enterprise_id : ciaTs
         * job_id : YPFCN
         * job_name : 地铁项目总监
         * enterprise_name : 上海市建设工程监理咨询有限公司
         * industry : 11
         * job_work_area : 2102
         * expiration_date : 1542556799
         * applied_num : 8
         * release_time : 2018-02-05
         * topjob_type : 1
         * study : 本科及以上
         * workyear : 十年以上
         * language :
         * post_rank :
         * salary : 20000 - 25000
         * applied_time : 0
         * workplace : 山东-青岛市
         * record_id : 319689283
         * favourite_time : 2018-02-11
         * is_expire : 0
         * is_apply : 0
         */

        private String user_id;
        private String enterprise_id;
        private String job_id;
        private String job_name;
        private String enterprise_name;
        private String industry;
        private String job_work_area;
        private String expiration_date;
        private String applied_num;
        private String release_time;
        private String topjob_type;
        private String study;
        private String workyear;
        private String language;
        private String post_rank;
        private String salary;
        private String workplace;
        private String record_id;
        private String favourite_time;
        private int is_expire;
        private int is_apply;

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

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getTopjob_type() {
            return topjob_type;
        }

        public void setTopjob_type(String topjob_type) {
            this.topjob_type = topjob_type;
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

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

       /* public String getApplied_time() {
            return applied_time;
        }

        public void setApplied_time(String applied_time) {
            this.applied_time = applied_time;
        }*/

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

        public String getFavourite_time() {
            return favourite_time;
        }

        public void setFavourite_time(String favourite_time) {
            this.favourite_time = favourite_time;
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

        @Override
        public String toString() {
            return "FavouriteListBean{" +
                    "user_id='" + user_id + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", job_id='" + job_id + '\'' +
                    ", job_name='" + job_name + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", industry='" + industry + '\'' +
                    ", job_work_area='" + job_work_area + '\'' +
                    ", expiration_date='" + expiration_date + '\'' +
                    ", applied_num='" + applied_num + '\'' +
                    ", release_time='" + release_time + '\'' +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", study='" + study + '\'' +
                    ", workyear='" + workyear + '\'' +
                    ", language='" + language + '\'' +
                    ", post_rank='" + post_rank + '\'' +
                    ", salary='" + salary + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", record_id='" + record_id + '\'' +
                    ", favourite_time='" + favourite_time + '\'' +
                    ", is_expire=" + is_expire +
                    ", is_apply=" + is_apply +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "error_code='" + error_code + '\'' +
                ", navpage_info=" + navpage_info +
                ", favourite_list=" + favourite_list +
                '}';
    }
}
