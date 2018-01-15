package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class MultipleResumeBean {

    /**
     * error_code : 0
     * resume_list : [{"user_id":"5692217","resume_id":"1","title":"1111","key_word":"沟通协调能力强 适应能力强 对工作充满激情","resume_type":"1","open":"2","uptime":"2017-12-06","fill_scale":"50","castbehalf":"1","important":"0","modify_time":"1506410577","add_time":"2017-05-08","talent_svr_state":"0","is_app":"0","other_language":"","resume_language":"zh","func":"11:241102,11:241138,11:241104","jobtype":"13","order_salary":"1000"},{"title":"简历2","modify_time":"1512456286","fill_scale":"100","key_word":"沟通协调能力强 适应能力强 认真负责 富有团队精神","add_time":"2016-09-30","important":"1","user_id":"5692217","resume_id":"3","resume_type":"1","open":"2","uptime":"2017-12-05","castbehalf":"1","talent_svr_state":"0","is_app":"0","other_language":"en","resume_language":"zh","func":"11:240101,11:241101,11:255000,11:396000,11:249000","jobtype":"13","order_salary":"2000"},{"user_id":"5692217","resume_id":"4","title":"简历3","key_word":"勤奋乐观 对工作充满激情 想象力丰富","resume_type":"1","open":"2","uptime":"2017-11-22","fill_scale":"50","castbehalf":"1","important":"0","modify_time":"1508373539","add_time":"2016-09-29","talent_svr_state":"0","is_app":"1","other_language":"","resume_language":"zh","func":"","jobtype":"13","order_salary":"22223"},{"user_id":"5692217","resume_id":"5","title":"简历4","key_word":"认真负责 富有团队精神 想象力丰富 专业知识广阔","resume_type":"2","open":"0","uptime":"2017-12-04","fill_scale":"75","castbehalf":"1","important":"0","modify_time":"1508373539","add_time":"2016-09-30","talent_svr_state":"0","is_app":"0","other_language":"","resume_language":"zh","func":"11:396102,11:240101,11:240102,11:241101,11:245000","jobtype":"14","order_salary":"2323"}]
     * base_info : [{"user_id":"5692217","name":"测试请忽略","sex":"2","year":"1988","month":"2","day":"4","height":"181","nationality":"11","hukou":"1110","idnumber":"123123123","cardtype":"14","marriage":"11","salary":"11","current_salary":"111","work_beginyear":"-1","location":"1801","ydphone":"15803307702","emailaddress":"anxuezhe@163.com","address":"测试请忽略","zipcode":"233433","homepage":"测试请忽略","echo_yes":"1","resume_language":"zh","post_rank":"11","polity":"13","blood":"AB","current_workstate":"15","pic_filekey":"31139f35eea2a7b00559f958bb8da327","modify_time":"1512468840","ydphone_verify_status":"1","telephone":"","im_account":"234324","im_type":"18"},{"user_id":"5692217","name":"asd","sex":"2","year":"1988","month":"2","day":"4","height":"181","nationality":"11","hukou":"1110","idnumber":"123123123","cardtype":"14","marriage":"11","salary":"11","current_salary":"111","work_beginyear":"-1","location":"1801","ydphone":"15803307702","emailaddress":"anxuezhe@163.com","address":"asd","zipcode":"233433","homepage":"测试请忽略","echo_yes":"1","resume_language":"en","post_rank":"11","polity":"13","blood":"AB","current_workstate":"15","pic_filekey":"31139f35eea2a7b00559f958bb8da327","modify_time":"1512468840","ydphone_verify_status":"1","telephone":"","im_account":"234324","im_type":"18"}]
     * language_list : [{"langname":"11","user_id":"5692217","read_level":"2","speak_level":"2"}]
     */

    private String error_code;
    private List<ResumeListBean> resume_list;
    private List<BaseInfoBean> base_info;
    private List<LanguageListBean> language_list;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<ResumeListBean> getResume_list() {
        return resume_list;
    }

    public void setResume_list(List<ResumeListBean> resume_list) {
        this.resume_list = resume_list;
    }

    public List<BaseInfoBean> getBase_info() {
        return base_info;
    }

    public void setBase_info(List<BaseInfoBean> base_info) {
        this.base_info = base_info;
    }

    public List<LanguageListBean> getLanguage_list() {
        return language_list;
    }

    public void setLanguage_list(List<LanguageListBean> language_list) {
        this.language_list = language_list;
    }

    public static class ResumeListBean {
        /**
         * user_id : 5692217
         * resume_id : 1
         * title : 1111
         * key_word : 沟通协调能力强 适应能力强 对工作充满激情
         * resume_type : 1
         * open : 2
         * uptime : 2017-12-06
         * fill_scale : 50
         * castbehalf : 1
         * important : 0
         * modify_time : 1506410577
         * add_time : 2017-05-08
         * talent_svr_state : 0
         * is_app : 0
         * other_language :
         * resume_language : zh
         * func : 11:241102,11:241138,11:241104
         * jobtype : 13
         * order_salary : 1000
         */

        private String user_id;
        private String resume_id;
        private String title;
        private String key_word;
        private String resume_type;
        private String open;
        private String uptime;
        private String fill_scale;
        private String castbehalf;
        private String important;
        private String modify_time;
        private String add_time;
        private String talent_svr_state;
        private String is_app;
        private String other_language;
        private String resume_language;
        private String func;
        private String jobtype;
        private String order_salary;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey_word() {
            return key_word;
        }

        public void setKey_word(String key_word) {
            this.key_word = key_word;
        }

        public String getResume_type() {
            return resume_type;
        }

        public void setResume_type(String resume_type) {
            this.resume_type = resume_type;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getFill_scale() {
            return fill_scale;
        }

        public void setFill_scale(String fill_scale) {
            this.fill_scale = fill_scale;
        }

        public String getCastbehalf() {
            return castbehalf;
        }

        public void setCastbehalf(String castbehalf) {
            this.castbehalf = castbehalf;
        }

        public String getImportant() {
            return important;
        }

        public void setImportant(String important) {
            this.important = important;
        }

        public String getModify_time() {
            return modify_time;
        }

        public void setModify_time(String modify_time) {
            this.modify_time = modify_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTalent_svr_state() {
            return talent_svr_state;
        }

        public void setTalent_svr_state(String talent_svr_state) {
            this.talent_svr_state = talent_svr_state;
        }

        public String getIs_app() {
            return is_app;
        }

        public void setIs_app(String is_app) {
            this.is_app = is_app;
        }

        public String getOther_language() {
            return other_language;
        }

        public void setOther_language(String other_language) {
            this.other_language = other_language;
        }

        public String getResume_language() {
            return resume_language;
        }

        public void setResume_language(String resume_language) {
            this.resume_language = resume_language;
        }

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public String getJobtype() {
            return jobtype;
        }

        public void setJobtype(String jobtype) {
            this.jobtype = jobtype;
        }

        public String getOrder_salary() {
            return order_salary;
        }

        public void setOrder_salary(String order_salary) {
            this.order_salary = order_salary;
        }

        @Override
        public String toString() {
            return "ResumeListBean{" +
                    "user_id='" + user_id + '\'' +
                    ", resume_id='" + resume_id + '\'' +
                    ", title='" + title + '\'' +
                    ", key_word='" + key_word + '\'' +
                    ", resume_type='" + resume_type + '\'' +
                    ", open='" + open + '\'' +
                    ", uptime='" + uptime + '\'' +
                    ", fill_scale='" + fill_scale + '\'' +
                    ", castbehalf='" + castbehalf + '\'' +
                    ", important='" + important + '\'' +
                    ", modify_time='" + modify_time + '\'' +
                    ", add_time='" + add_time + '\'' +
                    ", talent_svr_state='" + talent_svr_state + '\'' +
                    ", is_app='" + is_app + '\'' +
                    ", other_language='" + other_language + '\'' +
                    ", resume_language='" + resume_language + '\'' +
                    ", func='" + func + '\'' +
                    ", jobtype='" + jobtype + '\'' +
                    ", order_salary='" + order_salary + '\'' +
                    '}';
        }
    }

    public static class BaseInfoBean {
        /**
         * user_id : 5692217
         * name : 测试请忽略
         * sex : 2
         * year : 1988
         * month : 2
         * day : 4
         * height : 181
         * nationality : 11
         * hukou : 1110
         * idnumber : 123123123
         * cardtype : 14
         * marriage : 11
         * salary : 11
         * current_salary : 111
         * work_beginyear : -1
         * location : 1801
         * ydphone : 15803307702
         * emailaddress : anxuezhe@163.com
         * address : 测试请忽略
         * zipcode : 233433
         * homepage : 测试请忽略
         * echo_yes : 1
         * resume_language : zh
         * post_rank : 11
         * polity : 13
         * blood : AB
         * current_workstate : 15
         * pic_filekey : 31139f35eea2a7b00559f958bb8da327
         * modify_time : 1512468840
         * ydphone_verify_status : 1
         * telephone :
         * im_account : 234324
         * im_type : 18
         */

        private String user_id;
        private String name;
        private String sex;
        private String year;
        private String month;
        private String day;
        private String height;
        private String nationality;
        private String hukou;
        private String idnumber;
        private String cardtype;
        private String marriage;
        private String salary;
        private String current_salary;
        private String work_beginyear;
        private String location;
        private String ydphone;
        private String emailaddress;
        private String address;
        private String zipcode;
        private String homepage;
        private String echo_yes;
        private String resume_language;
        private String post_rank;
        private String polity;
        private String blood;
        private String current_workstate;
        private String pic_filekey;
        private String modify_time;
        private String ydphone_verify_status;
        private String telephone;
        private String im_account;
        private String im_type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getHukou() {
            return hukou;
        }

        public void setHukou(String hukou) {
            this.hukou = hukou;
        }

        public String getIdnumber() {
            return idnumber;
        }

        public void setIdnumber(String idnumber) {
            this.idnumber = idnumber;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getCurrent_salary() {
            return current_salary;
        }

        public void setCurrent_salary(String current_salary) {
            this.current_salary = current_salary;
        }

        public String getWork_beginyear() {
            return work_beginyear;
        }

        public void setWork_beginyear(String work_beginyear) {
            this.work_beginyear = work_beginyear;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getYdphone() {
            return ydphone;
        }

        public void setYdphone(String ydphone) {
            this.ydphone = ydphone;
        }

        public String getEmailaddress() {
            return emailaddress;
        }

        public void setEmailaddress(String emailaddress) {
            this.emailaddress = emailaddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public String getEcho_yes() {
            return echo_yes;
        }

        public void setEcho_yes(String echo_yes) {
            this.echo_yes = echo_yes;
        }

        public String getResume_language() {
            return resume_language;
        }

        public void setResume_language(String resume_language) {
            this.resume_language = resume_language;
        }

        public String getPost_rank() {
            return post_rank;
        }

        public void setPost_rank(String post_rank) {
            this.post_rank = post_rank;
        }

        public String getPolity() {
            return polity;
        }

        public void setPolity(String polity) {
            this.polity = polity;
        }

        public String getBlood() {
            return blood;
        }

        public void setBlood(String blood) {
            this.blood = blood;
        }

        public String getCurrent_workstate() {
            return current_workstate;
        }

        public void setCurrent_workstate(String current_workstate) {
            this.current_workstate = current_workstate;
        }

        public String getPic_filekey() {
            return pic_filekey;
        }

        public void setPic_filekey(String pic_filekey) {
            this.pic_filekey = pic_filekey;
        }

        public String getModify_time() {
            return modify_time;
        }

        public void setModify_time(String modify_time) {
            this.modify_time = modify_time;
        }

        public String getYdphone_verify_status() {
            return ydphone_verify_status;
        }

        public void setYdphone_verify_status(String ydphone_verify_status) {
            this.ydphone_verify_status = ydphone_verify_status;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getIm_account() {
            return im_account;
        }

        public void setIm_account(String im_account) {
            this.im_account = im_account;
        }

        public String getIm_type() {
            return im_type;
        }

        public void setIm_type(String im_type) {
            this.im_type = im_type;
        }

        @Override
        public String toString() {
            return "BaseInfoBean{" +
                    "user_id='" + user_id + '\'' +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", year='" + year + '\'' +
                    ", month='" + month + '\'' +
                    ", day='" + day + '\'' +
                    ", height='" + height + '\'' +
                    ", nationality='" + nationality + '\'' +
                    ", hukou='" + hukou + '\'' +
                    ", idnumber='" + idnumber + '\'' +
                    ", cardtype='" + cardtype + '\'' +
                    ", marriage='" + marriage + '\'' +
                    ", salary='" + salary + '\'' +
                    ", current_salary='" + current_salary + '\'' +
                    ", work_beginyear='" + work_beginyear + '\'' +
                    ", location='" + location + '\'' +
                    ", ydphone='" + ydphone + '\'' +
                    ", emailaddress='" + emailaddress + '\'' +
                    ", address='" + address + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    ", homepage='" + homepage + '\'' +
                    ", echo_yes='" + echo_yes + '\'' +
                    ", resume_language='" + resume_language + '\'' +
                    ", post_rank='" + post_rank + '\'' +
                    ", polity='" + polity + '\'' +
                    ", blood='" + blood + '\'' +
                    ", current_workstate='" + current_workstate + '\'' +
                    ", pic_filekey='" + pic_filekey + '\'' +
                    ", modify_time='" + modify_time + '\'' +
                    ", ydphone_verify_status='" + ydphone_verify_status + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", im_account='" + im_account + '\'' +
                    ", im_type='" + im_type + '\'' +
                    '}';
        }
    }

    public static class LanguageListBean {
        /**
         * langname : 11
         * user_id : 5692217
         * read_level : 2
         * speak_level : 2
         */

        private String langname;
        private String user_id;
        private String read_level;
        private String speak_level;

        public String getLangname() {
            return langname;
        }

        public void setLangname(String langname) {
            this.langname = langname;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRead_level() {
            return read_level;
        }

        public void setRead_level(String read_level) {
            this.read_level = read_level;
        }

        public String getSpeak_level() {
            return speak_level;
        }

        public void setSpeak_level(String speak_level) {
            this.speak_level = speak_level;
        }

        @Override
        public String toString() {
            return "LanguageListBean{" +
                    "langname='" + langname + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", read_level='" + read_level + '\'' +
                    ", speak_level='" + speak_level + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MultipleResumeBean{" +
                "error_code='" + error_code + '\'' +
                ", resume_list=" + resume_list +
                ", base_info=" + base_info +
                ", language_list=" + language_list +
                '}';
    }
}
