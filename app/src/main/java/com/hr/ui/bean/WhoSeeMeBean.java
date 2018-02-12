package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class WhoSeeMeBean {


    /**
     * error_code : 0
     * browsed_list : [{"enterprise_id":"Bi92A","enterprise_name":"北京红根基建筑有限公司","user_id":"5692217","resume_id":"2","industry":"11","company_type":"政府机关/非盈利机构","ent_logo":"","industry_name":"建筑","stuffmunber":"1000人以上","record_id":"55245830","browsed_time":"2017-12-21","recruit_info":{"job_name":"负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管","total":"268"}},{"enterprise_id":"iyQnu","enterprise_name":"北京红根基医药有限公司","user_id":"5692217","resume_id":"2","industry":"14","company_type":"私营/民营企业","ent_logo":"/entelogo/829/94/e757o_yzcp57.jpg","industry_name":"医药","stuffmunber":"500 - 999人","record_id":"55245833","browsed_time":"2017-12-14","recruit_info":{"jobname":"","total":0}}]
     * navpage_info : {"current_page":"1","total_pages":1,"record_nums":2,"page_nums":"20"}
     */

    private int error_code;
    private NavpageInfoBean navpage_info;
    private List<BrowsedListBean> browsed_list;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public NavpageInfoBean getNavpage_info() {
        return navpage_info;
    }

    public void setNavpage_info(NavpageInfoBean navpage_info) {
        this.navpage_info = navpage_info;
    }

    public List<BrowsedListBean> getBrowsed_list() {
        return browsed_list;
    }

    public void setBrowsed_list(List<BrowsedListBean> browsed_list) {
        this.browsed_list = browsed_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 1
         * record_nums : 2
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

    public static class BrowsedListBean {
        /**
         * enterprise_id : Bi92A
         * enterprise_name : 北京红根基建筑有限公司
         * user_id : 5692217
         * resume_id : 2
         * industry : 11
         * company_type : 政府机关/非盈利机构
         * ent_logo :
         * industry_name : 建筑
         * stuffmunber : 1000人以上
         * record_id : 55245830
         * browsed_time : 2017-12-21
         * recruit_info : {"job_name":"负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管","total":"268"}
         */

        private String enterprise_id;
        private String enterprise_name;
        private String user_id;
        private String resume_id;
        private String industry;
        private String company_type;
        private String ent_logo;
        private String industry_name;
        private String stuffmunber;
        private String record_id;
        private String browsed_time;
        private RecruitInfoBean recruit_info;

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getResume_id() {
            return resume_id;
        }

        public void setResume_id(String resume_id) {
            this.resume_id = resume_id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getCompany_type() {
            return company_type;
        }

        public void setCompany_type(String company_type) {
            this.company_type = company_type;
        }

        public String getEnt_logo() {
            return ent_logo;
        }

        public void setEnt_logo(String ent_logo) {
            this.ent_logo = ent_logo;
        }

        public String getIndustry_name() {
            return industry_name;
        }

        public void setIndustry_name(String industry_name) {
            this.industry_name = industry_name;
        }

        public String getStuffmunber() {
            return stuffmunber;
        }

        public void setStuffmunber(String stuffmunber) {
            this.stuffmunber = stuffmunber;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public String getBrowsed_time() {
            return browsed_time;
        }

        public void setBrowsed_time(String browsed_time) {
            this.browsed_time = browsed_time;
        }

        public RecruitInfoBean getRecruit_info() {
            return recruit_info;
        }

        public void setRecruit_info(RecruitInfoBean recruit_info) {
            this.recruit_info = recruit_info;
        }

        public static class RecruitInfoBean {
            /**
             * job_name : 负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管网运营的现场管理工作负责排水管
             * total : 268
             */

            private String job_name;
            private String total;

            public String getJob_name() {
                return job_name;
            }

            public void setJob_name(String job_name) {
                this.job_name = job_name;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            @Override
            public String toString() {
                return "RecruitInfoBean{" +
                        "job_name='" + job_name + '\'' +
                        ", total='" + total + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "BrowsedListBean{" +
                    "enterprise_id='" + enterprise_id + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", resume_id='" + resume_id + '\'' +
                    ", industry='" + industry + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", ent_logo='" + ent_logo + '\'' +
                    ", industry_name='" + industry_name + '\'' +
                    ", stuffmunber='" + stuffmunber + '\'' +
                    ", record_id='" + record_id + '\'' +
                    ", browsed_time='" + browsed_time + '\'' +
                    ", recruit_info=" + recruit_info +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WhoSeeMeBean{" +
                "error_code=" + error_code +
                ", navpage_info=" + navpage_info +
                ", browsed_list=" + browsed_list +
                '}';
    }
}
