package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/16.
 */

public class GuidanceBean {

    /**
     * error_code : 0
     * navpage_info : {"current_page":"1","total_pages":122,"record_nums":2432,"page_nums":"20"}
     * title_list : [{"id":"179539","type":"1","catid":"471","title":"简历通过率从10%提升到80%的6个Tips","subhead":"","thumb":"","pictures":[],"keywords":"","description":"","flag_top":"0","flag_top_date":"0","flag_first":"0","flag_commend":"0","flag_essence":"0","url":"","listorder":"100","status":"2","add_uid":"5802","author":"","contact":"","inputtime":"2018-01-12","updatetime":"1515741452","paginationtype":"1","maxcharperpage":"1300","crop":"0","voteid":"0","feedbackid":"0","copyfrom":"HRoot","istiming":"0","timingtime":"1515741053","views":"125","audit_userid":"5802","audit_time":"1515741465"},{"id":"178624","type":"1","catid":"473","title":"能让HR青睐并邀约的简历，原来这样写！","subhead":"","thumb":"","pictures":[],"keywords":"","description":"","flag_top":"0","flag_top_date":"0","flag_first":"0","flag_commend":"0","flag_essence":"0","url":"","listorder":"100","status":"2","add_uid":"5373","author":"","contact":"","inputtime":"2017-12-11","updatetime":"1512979659","paginationtype":"0","maxcharperpage":"800","crop":"0","voteid":"0","feedbackid":"0","copyfrom":"中人网","istiming":"0","timingtime":"1512979594","views":"418","audit_userid":"5373","audit_time":"1512979674"},{"id":"178623","type":"1","catid":"474","title":"写一份给自己的简历：你会聘请自己吗？","subhead":"","thumb":"","pictures":[],"keywords":"","description":"","flag_top":"0","flag_top_date":"0","flag_first":"0","flag_commend":"0","flag_essence":"0","url":"","listorder":"100","status":"2","add_uid":"5373","author":"","contact":"","inputtime":"2017-12-11","updatetime":"1512979563","paginationtype":"0","maxcharperpage":"800","crop":"0","voteid":"0","feedbackid":"0","copyfrom":"中人网","istiming":"0","timingtime":"1512979252","views":"207","audit_userid":"5373","audit_time":"1512979571"}]
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<TitleListBean> title_list;

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

    public List<TitleListBean> getTitle_list() {
        return title_list;
    }

    public void setTitle_list(List<TitleListBean> title_list) {
        this.title_list = title_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 122
         * record_nums : 2432
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

    public static class TitleListBean {
        /**
         * id : 179539
         * type : 1
         * catid : 471
         * title : 简历通过率从10%提升到80%的6个Tips
         * subhead :
         * thumb :
         * pictures : []
         * keywords :
         * description :
         * flag_top : 0
         * flag_top_date : 0
         * flag_first : 0
         * flag_commend : 0
         * flag_essence : 0
         * url :
         * listorder : 100
         * status : 2
         * add_uid : 5802
         * author :
         * contact :
         * inputtime : 2018-01-12
         * updatetime : 1515741452
         * paginationtype : 1
         * maxcharperpage : 1300
         * crop : 0
         * voteid : 0
         * feedbackid : 0
         * copyfrom : HRoot
         * istiming : 0
         * timingtime : 1515741053
         * views : 125
         * audit_userid : 5802
         * audit_time : 1515741465
         */

        private String id;
        private String type;
        private String catid;
        private String title;
        private String subhead;
        private String thumb;
        private String keywords;
        private String description;
        private String flag_top;
        private String flag_top_date;
        private String flag_first;
        private String flag_commend;
        private String flag_essence;
        private String url;
        private String listorder;
        private String status;
        private String add_uid;
        private String author;
        private String contact;
        private String inputtime;
        private String updatetime;
        private String paginationtype;
        private String maxcharperpage;
        private String crop;
        private String voteid;
        private String feedbackid;
        private String copyfrom;
        private String istiming;
        private String timingtime;
        private String views;
        private String audit_userid;
        private String audit_time;
        private List<?> pictures;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubhead() {
            return subhead;
        }

        public void setSubhead(String subhead) {
            this.subhead = subhead;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFlag_top() {
            return flag_top;
        }

        public void setFlag_top(String flag_top) {
            this.flag_top = flag_top;
        }

        public String getFlag_top_date() {
            return flag_top_date;
        }

        public void setFlag_top_date(String flag_top_date) {
            this.flag_top_date = flag_top_date;
        }

        public String getFlag_first() {
            return flag_first;
        }

        public void setFlag_first(String flag_first) {
            this.flag_first = flag_first;
        }

        public String getFlag_commend() {
            return flag_commend;
        }

        public void setFlag_commend(String flag_commend) {
            this.flag_commend = flag_commend;
        }

        public String getFlag_essence() {
            return flag_essence;
        }

        public void setFlag_essence(String flag_essence) {
            this.flag_essence = flag_essence;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdd_uid() {
            return add_uid;
        }

        public void setAdd_uid(String add_uid) {
            this.add_uid = add_uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getPaginationtype() {
            return paginationtype;
        }

        public void setPaginationtype(String paginationtype) {
            this.paginationtype = paginationtype;
        }

        public String getMaxcharperpage() {
            return maxcharperpage;
        }

        public void setMaxcharperpage(String maxcharperpage) {
            this.maxcharperpage = maxcharperpage;
        }

        public String getCrop() {
            return crop;
        }

        public void setCrop(String crop) {
            this.crop = crop;
        }

        public String getVoteid() {
            return voteid;
        }

        public void setVoteid(String voteid) {
            this.voteid = voteid;
        }

        public String getFeedbackid() {
            return feedbackid;
        }

        public void setFeedbackid(String feedbackid) {
            this.feedbackid = feedbackid;
        }

        public String getCopyfrom() {
            return copyfrom;
        }

        public void setCopyfrom(String copyfrom) {
            this.copyfrom = copyfrom;
        }

        public String getIstiming() {
            return istiming;
        }

        public void setIstiming(String istiming) {
            this.istiming = istiming;
        }

        public String getTimingtime() {
            return timingtime;
        }

        public void setTimingtime(String timingtime) {
            this.timingtime = timingtime;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getAudit_userid() {
            return audit_userid;
        }

        public void setAudit_userid(String audit_userid) {
            this.audit_userid = audit_userid;
        }

        public String getAudit_time() {
            return audit_time;
        }

        public void setAudit_time(String audit_time) {
            this.audit_time = audit_time;
        }

        public List<?> getPictures() {
            return pictures;
        }

        public void setPictures(List<?> pictures) {
            this.pictures = pictures;
        }
    }
}
