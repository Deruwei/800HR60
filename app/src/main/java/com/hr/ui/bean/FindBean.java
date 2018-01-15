package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class FindBean {

    /**
     * error_code : 0
     * list : [{"a_id":"533","c_id":"85","title":"欢迎加入水石国际！","topic_type":"1","topic_url":"http://m.buildhr.com/act/2015/shuishi/","enterprise_id":"","ad_txt":"为了你的未来，我们全力提供综合快速的成长平台，并竭力塑造独特魅力的企业文化。","pic_path":"http://img.800hr.com/mad/2015/06/021515401357.jpg","pic_s_path":"","click_num":"7993","edit_time":"1433229340","stuff_munber":"","company_type":""},{"a_id":"776","c_id":"37","title":"中国石油天然气第七建设有限公司","topic_type":"1","topic_url":"http://www.chenhr.com/topic/2017/zgsy/","enterprise_id":"ZTqAD","ad_txt":"","pic_path":"","pic_s_path":"","click_num":"28","edit_time":"1507533208","stuff_munber":"1000人以上","company_type":"国有企业"},{"a_id":"534","c_id":"23","title":"金融英才网","topic_type":"1","topic_url":"http://m.bankhr.com/","enterprise_id":"MJPY","ad_txt":"","pic_path":"http://img.800hr.com/mad/2015/06/041123442359.jpg","pic_s_path":"http://img.800hr.com/mad/2015/06/041123440249.jpg","click_num":"2548","edit_time":"1433388224","stuff_munber":"","company_type":""},{"a_id":"771","c_id":"37","title":"锦州锦港石化有限公司","topic_type":"1","topic_url":"http://www.chenhr.com/company/5UCSl/","enterprise_id":"5UCSl","ad_txt":"","pic_path":"","pic_s_path":"","click_num":"300","edit_time":"1502938656","stuff_munber":"1000人以上","company_type":"私营/民营企业"},{"a_id":"559","c_id":"87","title":"中化泉州石化有限公司诚聘","topic_type":"1","topic_url":"http://m.chenhr.com/act/2015/xgs/zhqz/","enterprise_id":"","ad_txt":"中化泉州石化有限公司是国有大型骨干企业、世界五百强之一中国中化集团公司的全资子公司。承担中化泉州1200万吨/年炼化项目的建设、运营任务。","pic_path":"http://img.800hr.com/mad/2016/01/281135531678.jpg","pic_s_path":"","click_num":"4341","edit_time":"1453952154","stuff_munber":"","company_type":""},{"a_id":"772","c_id":"35","title":"三亚海昌梦幻不夜城发展有限公司","topic_type":"2","topic_url":"","enterprise_id":"Sem0A","ad_txt":"","pic_path":"","pic_s_path":"","click_num":"257","edit_time":"1504158549","stuff_munber":"1 - 49人","company_type":"私营/民营企业"},{"a_id":"778","c_id":"92","title":"北京红根基建筑有限公司","topic_type":"1","topic_url":"www.baidu.com","enterprise_id":"QC7uI","ad_txt":"测试专用","pic_path":"http://img.800hr.com/mad/2017/10/111546221237.jpg","pic_s_path":"http://img.800hr.com/mad/2017/10/111546220258.png","click_num":"0","edit_time":"1507708778","stuff_munber":"","company_type":""},{"a_id":"774","c_id":"83","title":"中国民生银行股份有限公司","topic_type":"1","topic_url":"http://career.cmbc.com.cn:8080/index.html#/app/home","enterprise_id":"W7xQG","ad_txt":"","pic_path":"http://img.800hr.com/mad/2017/09/211020390467.jpg","pic_s_path":"","click_num":"25","edit_time":"1505960439","stuff_munber":"1000人以上","company_type":"其他"}]
     * navpage_info : {"current_page":"1","total_pages":10,"record_nums":192,"page_nums":"20"}
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 10
         * record_nums : 192
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

    public static class ListBean {
        /**
         * a_id : 533
         * c_id : 85
         * title : 欢迎加入水石国际！
         * topic_type : 1
         * topic_url : http://m.buildhr.com/act/2015/shuishi/
         * enterprise_id :
         * ad_txt : 为了你的未来，我们全力提供综合快速的成长平台，并竭力塑造独特魅力的企业文化。
         * pic_path : http://img.800hr.com/mad/2015/06/021515401357.jpg
         * pic_s_path :
         * click_num : 7993
         * edit_time : 1433229340
         * stuff_munber :
         * company_type :
         */

        private String a_id;
        private String c_id;
        private String title;
        private String topic_type;
        private String topic_url;
        private String enterprise_id;
        private String ad_txt;
        private String pic_path;
        private String pic_s_path;
        private String click_num;
        private String edit_time;
        private String stuff_munber;
        private String company_type;

        public String getA_id() {
            return a_id;
        }

        public void setA_id(String a_id) {
            this.a_id = a_id;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTopic_type() {
            return topic_type;
        }

        public void setTopic_type(String topic_type) {
            this.topic_type = topic_type;
        }

        public String getTopic_url() {
            return topic_url;
        }

        public void setTopic_url(String topic_url) {
            this.topic_url = topic_url;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getAd_txt() {
            return ad_txt;
        }

        public void setAd_txt(String ad_txt) {
            this.ad_txt = ad_txt;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getPic_s_path() {
            return pic_s_path;
        }

        public void setPic_s_path(String pic_s_path) {
            this.pic_s_path = pic_s_path;
        }

        public String getClick_num() {
            return click_num;
        }

        public void setClick_num(String click_num) {
            this.click_num = click_num;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }

        public String getStuff_munber() {
            return stuff_munber;
        }

        public void setStuff_munber(String stuff_munber) {
            this.stuff_munber = stuff_munber;
        }

        public String getCompany_type() {
            return company_type;
        }

        public void setCompany_type(String company_type) {
            this.company_type = company_type;
        }
    }
}
