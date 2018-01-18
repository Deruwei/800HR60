package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/16.
 */

public class GuidanceInfoBean {

    /**
     * error_code : 0
     * _run_time : 0.0083379745483398
     * title_content_list : [{"id":"179641","type":"1","catid":"827","title":"2017年河南第四季度人才走势 看看哪些岗位最缺人","subhead":"","thumb":"","pictures":[],"keywords":"","description":"","flag_top":"0","flag_top_date":"1831608159","flag_first":"0","flag_commend":"0","flag_essence":"0","url":"","listorder":"100","status":"2","add_uid":"5373","author":"","contact":"","inputtime":"12:02","updatetime":"1516075359","content":"　　通过对河南省人才交流中心、省辖市人才中心、省直管县人才中心为主体的29个信息采集点的数据采集，2017年第四季度河南人才市场供求情况分析昨日新鲜出炉，全面反映出全省人才市场供需情况。\n　　2017年第四季度河南省人才交流中心共举办各类现场招聘会35场，其中专场招聘会23场、综合招聘会12场，共组织1256家用人单位进场招聘，提供各类岗位3.39万个，4.57万人进场求职择业，2.84万人达成初步就业意向。\n　　岗位需求3.39万4.57万人求职\n　　统计数据显示，2017年第四季度用人单位岗位需求为3.39万，同比上升17.11%，求职供给人数为4.57万，同比上升29.76%，求人倍率为0.74，就业总体形势趋稳，但结构性矛盾依然存在。\n　　计算机、互联网、电子商务类职位供需两旺\n　　从数据可看出，计算机、互联网、电子商务类职位居河南人才市场第四季度十大热门招聘职位类别的第二位，占岗位总需求的14.81%；计算机、互联网、电子商务类职位占市场供求人数的11.22%，位居河南人才市场第四季度十大热门应聘职位类别的第一，在市场中呈现供求两旺的态势。\n　　在新动能的推动下，互联网经济规模逐年走高，岗位需求不断放大，伴随着大众创业、万众创新的深入推进，更多的年轻一代在互联网领域深耕，互联网的经济结构也在发生深刻变化，呈现了较强的就业增长动力。\n　　制造、加工等高技能人才短缺\n　　统计显示，建筑、机械、生产、营运、质量、安全类职位市场供给人数占本季度求职总人数的16.75%。但是在目前经济转型下，企业走的都是新型工业化道路，与优化产业结构和转变经济增长方式的要求相比，高技能人才的总量、结构和素质还不能完全适应经济社会发展的需要，特别是在制造、加工、建筑等传统产业和新一代信息技术、生物工程等高新技术产业以及现代服务业领域高技能人才的严重短缺，已成为制约企业发展和阻碍产业升级的瓶颈。\n　　有住房补贴更吸引毕业生\n　　对于用人单位来说，人才争夺真正的较量在五险一金、住房补贴和培训晋升等手段。毕业生更希望有个高水平的职业平台施展所学和获得其他职业资源，企业仅凭高薪留住高学历毕业生吸引力已然不足。相关数据还表明，40%左右的毕业生月薪在2800～3200元，月薪在3200～4000元的毕业生占10%左右，少部分毕业生月薪也有超过4000元的，企业所给付的薪酬差异随着毕业生专业技能的高低也在逐渐扩大。\n　　十大热门招聘职位\n　　排名所属职业　所占比例\n　　1市场营销/公关/销售　28.21%\n　　2计算机/互联网/电子商务14.81%\n　　3百货/连锁/零售服务　5.78%\n　　4人力资源/行政　5.66%\n　　5证券/银行/保险　4.49%\n　　6通信/电器　4.14%\n　　7房地产开发/物业管理　4.08%\n　　8建筑/机械　3.87%\n　　9财务/审计/税务　3.50%\n　　10农/林/牧/渔　3.40%\n　　十大热门应聘职位\n　　排名所属职业　 所占比例\n　　1计算机/互联网/电子商务11.22%\n　　2建筑/机械10.74%\n　　3市场营销/公关/销售8.25%\n　　4采购/贸易/物流/仓储6.60%\n　　5财务/审计/税务6.41%\n　　6生产/营运/质量/安全6.01%\n　　7人力资源/行政5.55%\n　　8通信/电器5.02%\n　　9高级管理4.75%\n　　10技工4.67%\n　　备注：这些数据，不仅反映了2017年招聘和应聘的特点，也为2018年的招聘和应聘提供了方向。\n","paginationtype":"0","maxcharperpage":"800","crop":"0","voteid":"0","feedbackid":"0","copyfrom":"新华网","istiming":"0","timingtime":"1516075242","views":"30","audit_userid":"5373","audit_time":"1516075368","all_catid":"479,80,83,827"}]
     */

    private int error_code;
    private String _run_time;
    private List<TitleContentListBean> title_content_list;

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

    public List<TitleContentListBean> getTitle_content_list() {
        return title_content_list;
    }

    public void setTitle_content_list(List<TitleContentListBean> title_content_list) {
        this.title_content_list = title_content_list;
    }

    public static class TitleContentListBean {
        /**
         * id : 179641
         * type : 1
         * catid : 827
         * title : 2017年河南第四季度人才走势 看看哪些岗位最缺人
         * subhead :
         * thumb :
         * pictures : []
         * keywords :
         * description :
         * flag_top : 0
         * flag_top_date : 1831608159
         * flag_first : 0
         * flag_commend : 0
         * flag_essence : 0
         * url :
         * listorder : 100
         * status : 2
         * add_uid : 5373
         * author :
         * contact :
         * inputtime : 12:02
         * updatetime : 1516075359
         * content : 　　通过对河南省人才交流中心、省辖市人才中心、省直管县人才中心为主体的29个信息采集点的数据采集，2017年第四季度河南人才市场供求情况分析昨日新鲜出炉，全面反映出全省人才市场供需情况。
         　　2017年第四季度河南省人才交流中心共举办各类现场招聘会35场，其中专场招聘会23场、综合招聘会12场，共组织1256家用人单位进场招聘，提供各类岗位3.39万个，4.57万人进场求职择业，2.84万人达成初步就业意向。
         　　岗位需求3.39万4.57万人求职
         　　统计数据显示，2017年第四季度用人单位岗位需求为3.39万，同比上升17.11%，求职供给人数为4.57万，同比上升29.76%，求人倍率为0.74，就业总体形势趋稳，但结构性矛盾依然存在。
         　　计算机、互联网、电子商务类职位供需两旺
         　　从数据可看出，计算机、互联网、电子商务类职位居河南人才市场第四季度十大热门招聘职位类别的第二位，占岗位总需求的14.81%；计算机、互联网、电子商务类职位占市场供求人数的11.22%，位居河南人才市场第四季度十大热门应聘职位类别的第一，在市场中呈现供求两旺的态势。
         　　在新动能的推动下，互联网经济规模逐年走高，岗位需求不断放大，伴随着大众创业、万众创新的深入推进，更多的年轻一代在互联网领域深耕，互联网的经济结构也在发生深刻变化，呈现了较强的就业增长动力。
         　　制造、加工等高技能人才短缺
         　　统计显示，建筑、机械、生产、营运、质量、安全类职位市场供给人数占本季度求职总人数的16.75%。但是在目前经济转型下，企业走的都是新型工业化道路，与优化产业结构和转变经济增长方式的要求相比，高技能人才的总量、结构和素质还不能完全适应经济社会发展的需要，特别是在制造、加工、建筑等传统产业和新一代信息技术、生物工程等高新技术产业以及现代服务业领域高技能人才的严重短缺，已成为制约企业发展和阻碍产业升级的瓶颈。
         　　有住房补贴更吸引毕业生
         　　对于用人单位来说，人才争夺真正的较量在五险一金、住房补贴和培训晋升等手段。毕业生更希望有个高水平的职业平台施展所学和获得其他职业资源，企业仅凭高薪留住高学历毕业生吸引力已然不足。相关数据还表明，40%左右的毕业生月薪在2800～3200元，月薪在3200～4000元的毕业生占10%左右，少部分毕业生月薪也有超过4000元的，企业所给付的薪酬差异随着毕业生专业技能的高低也在逐渐扩大。
         　　十大热门招聘职位
         　　排名所属职业　所占比例
         　　1市场营销/公关/销售　28.21%
         　　2计算机/互联网/电子商务14.81%
         　　3百货/连锁/零售服务　5.78%
         　　4人力资源/行政　5.66%
         　　5证券/银行/保险　4.49%
         　　6通信/电器　4.14%
         　　7房地产开发/物业管理　4.08%
         　　8建筑/机械　3.87%
         　　9财务/审计/税务　3.50%
         　　10农/林/牧/渔　3.40%
         　　十大热门应聘职位
         　　排名所属职业　 所占比例
         　　1计算机/互联网/电子商务11.22%
         　　2建筑/机械10.74%
         　　3市场营销/公关/销售8.25%
         　　4采购/贸易/物流/仓储6.60%
         　　5财务/审计/税务6.41%
         　　6生产/营运/质量/安全6.01%
         　　7人力资源/行政5.55%
         　　8通信/电器5.02%
         　　9高级管理4.75%
         　　10技工4.67%
         　　备注：这些数据，不仅反映了2017年招聘和应聘的特点，也为2018年的招聘和应聘提供了方向。

         * paginationtype : 0
         * maxcharperpage : 800
         * crop : 0
         * voteid : 0
         * feedbackid : 0
         * copyfrom : 新华网
         * istiming : 0
         * timingtime : 1516075242
         * views : 30
         * audit_userid : 5373
         * audit_time : 1516075368
         * all_catid : 479,80,83,827
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
        private String content;
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
        private String all_catid;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getAll_catid() {
            return all_catid;
        }

        public void setAll_catid(String all_catid) {
            this.all_catid = all_catid;
        }

        public List<?> getPictures() {
            return pictures;
        }

        public void setPictures(List<?> pictures) {
            this.pictures = pictures;
        }
    }
}
