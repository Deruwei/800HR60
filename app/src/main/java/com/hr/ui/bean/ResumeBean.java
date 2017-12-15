package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class ResumeBean {

    /**
     * error_code : 0
     * resume_info : {"title_info":{"user_id":"9491509","resume_id":"1","title":"我的简历","key_word":"","apply":"0","click":"0","resume_language":"zh","resume_type":"1","open":"0","uptime":"1513042043","fill_scale":"85","castbehalf":"1","add_time":"1512979996","important":"1","modify_time":"1513042043","talent_svr_state":"0"},"base_info":{"user_id":"9491509","name":"iejkk","sex":"1","year":"1998","month":"9","day":"11","height":"0","nationality":"11","hukou":"0","idnumber":"","cardtype":"0","marriage":"0","salary":"22","current_salary":"64646","work_beginyear":"1999","high_education":"16","location":"1802","ydphone":"13581848569","emailaddress":"jdjjs@qq.com","address":"","zipcode":"","homepage":"","echo_yes":"1","resume_language":"zh","post_rank":"12","polity":"0","blood":"","last_position":"bidhdh","current_workstate":"15","pic_filekey":"","modify_time":"1513042010","current_yearsalary":"0","capability":"0","other_benefits":"","other_benefits_txt":"","linkinfo_show":"1","ydphone_verify_status":"2","telephone":"","im_account":"","im_type":""},"order_info":{"user_id":"9491509","jobtype":"13","industry":"11","func":"240101","workarea":"1602","zhixi":"","order_salary":"66464","order_salary_noshow":"0","resume_id":"1","resume_language":"zh","jobname":"","lingyu":"111300","order_yearsalary":"0","order_industry":[{"industry":"11","func":"240101","lingyu":"111300"}],"current_workstate":"15","castbehalf":"1"},"assess_info":{"user_id":"9491509","introduction":"计算机等级记得记得就","resume_id":"1","resume_language":"zh","virtue":"","weakness":""},"language_list":[{"langname":"11","user_id":"9491509","read_level":"4","speak_level":"3","grade_exam":[{"id":"1103","score":"568"}]}],"education_list":[{"user_id":"9491509","fromyear":"2004","frommonth":"10","toyear":"2009","tomonth":"1","schoolname":"bdjdj","moremajor":"bdjjdj","degree":"16","edudetail":"","is_overseas":"0","country":"0","resume_id":"1","resume_language":"zh","recruit_students":"0","degreecerti_no":"","a_degreecerti_no":"","education_id":"9558623"}],"experience_list":[{"user_id":"9491509","fromyear":"2014","frommonth":"1","toyear":"2016","tomonth":"9","company":"bdjdjdj","companyhide":"0","industry":"0","companytype":"0","stuffmunber":"0","division":"","companyaddress":"1200","position":"bidhdh","responsiblity":"bxjdndmmdmd","offreason":"","achievement":"","zhixi":"0","zhicheng":"0","is_overseas":"0","country":"0","resume_id":"1","resume_language":"zh","lingyu":"0","func":"0","salary":"64646","salary_hide":"0","reterence":"","yearsalary":"0","underling":"","superior":"","experience_id":"10148692"}],"project_list":[{"user_id":"9491509","fromyear":"2008","frommonth":"10","toyear":"2015","tomonth":"12","projectname":"红色警戒十几万","projectdesc":"家电家具单开双控","responsibility":"抱着你辛苦辛苦面对面的","position":"那乍办","resume_id":"1","resume_language":"zh","companyname":"","companyhide":"0","achievement":"","reterence":"","project_id":"4523385"}],"plant_list":[{"user_id":"9491509","fromyear":"2013","frommonth":"11","toyear":"2015","tomonth":"12","institution":"锦江大酒店就","place":"1200","course":"计算机技术","certification":"计算机考试","traindetail":"嗯就是计算机三级","resume_id":"1","resume_language":"zh","certi_number":"","plant_id":"1938709"}],"skill_list":[{"user_id":"9491509","usetime":"964","ability":"5","skilltitle":"贾九转金身决","resume_id":"1","resume_language":"zh","skill_id":"3152630"}],"subjoin_list":[],"slave_list":[],"attachment":[]}
     * _run_time : 0.033774852752686
     */

    private int error_code;
    private ResumeInfoBean resume_info;
    private String _run_time;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResumeInfoBean getResume_info() {
        return resume_info;
    }

    public void setResume_info(ResumeInfoBean resume_info) {
        this.resume_info = resume_info;
    }

    public String get_run_time() {
        return _run_time;
    }

    public void set_run_time(String _run_time) {
        this._run_time = _run_time;
    }

    public static class ResumeInfoBean {
        /**
         * title_info : {"user_id":"9491509","resume_id":"1","title":"我的简历","key_word":"","apply":"0","click":"0","resume_language":"zh","resume_type":"1","open":"0","uptime":"1513042043","fill_scale":"85","castbehalf":"1","add_time":"1512979996","important":"1","modify_time":"1513042043","talent_svr_state":"0"}
         * base_info : {"user_id":"9491509","name":"iejkk","sex":"1","year":"1998","month":"9","day":"11","height":"0","nationality":"11","hukou":"0","idnumber":"","cardtype":"0","marriage":"0","salary":"22","current_salary":"64646","work_beginyear":"1999","high_education":"16","location":"1802","ydphone":"13581848569","emailaddress":"jdjjs@qq.com","address":"","zipcode":"","homepage":"","echo_yes":"1","resume_language":"zh","post_rank":"12","polity":"0","blood":"","last_position":"bidhdh","current_workstate":"15","pic_filekey":"","modify_time":"1513042010","current_yearsalary":"0","capability":"0","other_benefits":"","other_benefits_txt":"","linkinfo_show":"1","ydphone_verify_status":"2","telephone":"","im_account":"","im_type":""}
         * order_info : {"user_id":"9491509","jobtype":"13","industry":"11","func":"240101","workarea":"1602","zhixi":"","order_salary":"66464","order_salary_noshow":"0","resume_id":"1","resume_language":"zh","jobname":"","lingyu":"111300","order_yearsalary":"0","order_industry":[{"industry":"11","func":"240101","lingyu":"111300"}],"current_workstate":"15","castbehalf":"1"}
         * assess_info : {"user_id":"9491509","introduction":"计算机等级记得记得就","resume_id":"1","resume_language":"zh","virtue":"","weakness":""}
         * language_list : [{"langname":"11","user_id":"9491509","read_level":"4","speak_level":"3","grade_exam":[{"id":"1103","score":"568"}]}]
         * education_list : [{"user_id":"9491509","fromyear":"2004","frommonth":"10","toyear":"2009","tomonth":"1","schoolname":"bdjdj","moremajor":"bdjjdj","degree":"16","edudetail":"","is_overseas":"0","country":"0","resume_id":"1","resume_language":"zh","recruit_students":"0","degreecerti_no":"","a_degreecerti_no":"","education_id":"9558623"}]
         * experience_list : [{"user_id":"9491509","fromyear":"2014","frommonth":"1","toyear":"2016","tomonth":"9","company":"bdjdjdj","companyhide":"0","industry":"0","companytype":"0","stuffmunber":"0","division":"","companyaddress":"1200","position":"bidhdh","responsiblity":"bxjdndmmdmd","offreason":"","achievement":"","zhixi":"0","zhicheng":"0","is_overseas":"0","country":"0","resume_id":"1","resume_language":"zh","lingyu":"0","func":"0","salary":"64646","salary_hide":"0","reterence":"","yearsalary":"0","underling":"","superior":"","experience_id":"10148692"}]
         * project_list : [{"user_id":"9491509","fromyear":"2008","frommonth":"10","toyear":"2015","tomonth":"12","projectname":"红色警戒十几万","projectdesc":"家电家具单开双控","responsibility":"抱着你辛苦辛苦面对面的","position":"那乍办","resume_id":"1","resume_language":"zh","companyname":"","companyhide":"0","achievement":"","reterence":"","project_id":"4523385"}]
         * plant_list : [{"user_id":"9491509","fromyear":"2013","frommonth":"11","toyear":"2015","tomonth":"12","institution":"锦江大酒店就","place":"1200","course":"计算机技术","certification":"计算机考试","traindetail":"嗯就是计算机三级","resume_id":"1","resume_language":"zh","certi_number":"","plant_id":"1938709"}]
         * skill_list : [{"user_id":"9491509","usetime":"964","ability":"5","skilltitle":"贾九转金身决","resume_id":"1","resume_language":"zh","skill_id":"3152630"}]
         * subjoin_list : []
         * slave_list : []
         * attachment : []
         */

        private TitleInfoBean title_info;
        private BaseInfoBean base_info;
        private OrderInfoBean order_info;
        private AssessInfoBean assess_info;
        private List<LanguageListBean> language_list;
        private List<EducationListBean> education_list;
        private List<ExperienceListBean> experience_list;
        private List<ProjectListBean> project_list;
        private List<PlantListBean> plant_list;
        private List<SkillListBean> skill_list;
        private List<?> subjoin_list;
        private List<?> slave_list;
        private List<?> attachment;

        public TitleInfoBean getTitle_info() {
            return title_info;
        }

        public void setTitle_info(TitleInfoBean title_info) {
            this.title_info = title_info;
        }

        public BaseInfoBean getBase_info() {
            return base_info;
        }

        public void setBase_info(BaseInfoBean base_info) {
            this.base_info = base_info;
        }

        public OrderInfoBean getOrder_info() {
            return order_info;
        }

        public void setOrder_info(OrderInfoBean order_info) {
            this.order_info = order_info;
        }

        public AssessInfoBean getAssess_info() {
            return assess_info;
        }

        public void setAssess_info(AssessInfoBean assess_info) {
            this.assess_info = assess_info;
        }

        public List<LanguageListBean> getLanguage_list() {
            return language_list;
        }

        public void setLanguage_list(List<LanguageListBean> language_list) {
            this.language_list = language_list;
        }

        public List<EducationListBean> getEducation_list() {
            return education_list;
        }

        public void setEducation_list(List<EducationListBean> education_list) {
            this.education_list = education_list;
        }

        public List<ExperienceListBean> getExperience_list() {
            return experience_list;
        }

        public void setExperience_list(List<ExperienceListBean> experience_list) {
            this.experience_list = experience_list;
        }

        public List<ProjectListBean> getProject_list() {
            return project_list;
        }

        public void setProject_list(List<ProjectListBean> project_list) {
            this.project_list = project_list;
        }

        public List<PlantListBean> getPlant_list() {
            return plant_list;
        }

        public void setPlant_list(List<PlantListBean> plant_list) {
            this.plant_list = plant_list;
        }

        public List<SkillListBean> getSkill_list() {
            return skill_list;
        }

        public void setSkill_list(List<SkillListBean> skill_list) {
            this.skill_list = skill_list;
        }

        public List<?> getSubjoin_list() {
            return subjoin_list;
        }

        public void setSubjoin_list(List<?> subjoin_list) {
            this.subjoin_list = subjoin_list;
        }

        public List<?> getSlave_list() {
            return slave_list;
        }

        public void setSlave_list(List<?> slave_list) {
            this.slave_list = slave_list;
        }

        public List<?> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<?> attachment) {
            this.attachment = attachment;
        }

        public static class TitleInfoBean {
            /**
             * user_id : 9491509
             * resume_id : 1
             * title : 我的简历
             * key_word :
             * apply : 0
             * click : 0
             * resume_language : zh
             * resume_type : 1
             * open : 0
             * uptime : 1513042043
             * fill_scale : 85
             * castbehalf : 1
             * add_time : 1512979996
             * important : 1
             * modify_time : 1513042043
             * talent_svr_state : 0
             */

            private String user_id;
            private String resume_id;
            private String title;
            private String key_word;
            private String apply;
            private String click;
            private String resume_language;
            private String resume_type;
            private String open;
            private String uptime;
            private String fill_scale;
            private String castbehalf;
            private String add_time;
            private String important;
            private String modify_time;
            private String talent_svr_state;

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

            public String getApply() {
                return apply;
            }

            public void setApply(String apply) {
                this.apply = apply;
            }

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
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

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
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

            public String getTalent_svr_state() {
                return talent_svr_state;
            }

            public void setTalent_svr_state(String talent_svr_state) {
                this.talent_svr_state = talent_svr_state;
            }
        }

        public static class BaseInfoBean {
            /**
             * user_id : 9491509
             * name : iejkk
             * sex : 1
             * year : 1998
             * month : 9
             * day : 11
             * height : 0
             * nationality : 11
             * hukou : 0
             * idnumber :
             * cardtype : 0
             * marriage : 0
             * salary : 22
             * current_salary : 64646
             * work_beginyear : 1999
             * high_education : 16
             * location : 1802
             * ydphone : 13581848569
             * emailaddress : jdjjs@qq.com
             * address :
             * zipcode :
             * homepage :
             * echo_yes : 1
             * resume_language : zh
             * post_rank : 12
             * polity : 0
             * blood :
             * last_position : bidhdh
             * current_workstate : 15
             * pic_filekey :
             * modify_time : 1513042010
             * current_yearsalary : 0
             * capability : 0
             * other_benefits :
             * other_benefits_txt :
             * linkinfo_show : 1
             * ydphone_verify_status : 2
             * telephone :
             * im_account :
             * im_type :
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
            private String high_education;
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
            private String last_position;
            private String current_workstate;
            private String pic_filekey;
            private String modify_time;
            private String current_yearsalary;
            private String capability;
            private String other_benefits;
            private String other_benefits_txt;
            private String linkinfo_show;
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

            public String getHigh_education() {
                return high_education;
            }

            public void setHigh_education(String high_education) {
                this.high_education = high_education;
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

            public String getLast_position() {
                return last_position;
            }

            public void setLast_position(String last_position) {
                this.last_position = last_position;
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

            public String getCurrent_yearsalary() {
                return current_yearsalary;
            }

            public void setCurrent_yearsalary(String current_yearsalary) {
                this.current_yearsalary = current_yearsalary;
            }

            public String getCapability() {
                return capability;
            }

            public void setCapability(String capability) {
                this.capability = capability;
            }

            public String getOther_benefits() {
                return other_benefits;
            }

            public void setOther_benefits(String other_benefits) {
                this.other_benefits = other_benefits;
            }

            public String getOther_benefits_txt() {
                return other_benefits_txt;
            }

            public void setOther_benefits_txt(String other_benefits_txt) {
                this.other_benefits_txt = other_benefits_txt;
            }

            public String getLinkinfo_show() {
                return linkinfo_show;
            }

            public void setLinkinfo_show(String linkinfo_show) {
                this.linkinfo_show = linkinfo_show;
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
                        ", high_education='" + high_education + '\'' +
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
                        ", last_position='" + last_position + '\'' +
                        ", current_workstate='" + current_workstate + '\'' +
                        ", pic_filekey='" + pic_filekey + '\'' +
                        ", modify_time='" + modify_time + '\'' +
                        ", current_yearsalary='" + current_yearsalary + '\'' +
                        ", capability='" + capability + '\'' +
                        ", other_benefits='" + other_benefits + '\'' +
                        ", other_benefits_txt='" + other_benefits_txt + '\'' +
                        ", linkinfo_show='" + linkinfo_show + '\'' +
                        ", ydphone_verify_status='" + ydphone_verify_status + '\'' +
                        ", telephone='" + telephone + '\'' +
                        ", im_account='" + im_account + '\'' +
                        ", im_type='" + im_type + '\'' +
                        '}';
            }
        }

        public static class OrderInfoBean {
            /**
             * user_id : 9491509
             * jobtype : 13
             * industry : 11
             * func : 240101
             * workarea : 1602
             * zhixi :
             * order_salary : 66464
             * order_salary_noshow : 0
             * resume_id : 1
             * resume_language : zh
             * jobname :
             * lingyu : 111300
             * order_yearsalary : 0
             * order_industry : [{"industry":"11","func":"240101","lingyu":"111300"}]
             * current_workstate : 15
             * castbehalf : 1
             */

            private String user_id;
            private String jobtype;
            private String industry;
            private String func;
            private String workarea;
            private String zhixi;
            private String order_salary;
            private String order_salary_noshow;
            private String resume_id;
            private String resume_language;
            private String jobname;
            private String lingyu;
            private String order_yearsalary;
            private String current_workstate;
            private String castbehalf;
            private List<OrderIndustryBean> order_industry;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getJobtype() {
                return jobtype;
            }

            public void setJobtype(String jobtype) {
                this.jobtype = jobtype;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public String getFunc() {
                return func;
            }

            public void setFunc(String func) {
                this.func = func;
            }

            public String getWorkarea() {
                return workarea;
            }

            public void setWorkarea(String workarea) {
                this.workarea = workarea;
            }

            public String getZhixi() {
                return zhixi;
            }

            public void setZhixi(String zhixi) {
                this.zhixi = zhixi;
            }

            public String getOrder_salary() {
                return order_salary;
            }

            public void setOrder_salary(String order_salary) {
                this.order_salary = order_salary;
            }

            public String getOrder_salary_noshow() {
                return order_salary_noshow;
            }

            public void setOrder_salary_noshow(String order_salary_noshow) {
                this.order_salary_noshow = order_salary_noshow;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getJobname() {
                return jobname;
            }

            public void setJobname(String jobname) {
                this.jobname = jobname;
            }

            public String getLingyu() {
                return lingyu;
            }

            public void setLingyu(String lingyu) {
                this.lingyu = lingyu;
            }

            public String getOrder_yearsalary() {
                return order_yearsalary;
            }

            public void setOrder_yearsalary(String order_yearsalary) {
                this.order_yearsalary = order_yearsalary;
            }

            public String getCurrent_workstate() {
                return current_workstate;
            }

            public void setCurrent_workstate(String current_workstate) {
                this.current_workstate = current_workstate;
            }

            public String getCastbehalf() {
                return castbehalf;
            }

            public void setCastbehalf(String castbehalf) {
                this.castbehalf = castbehalf;
            }

            public List<OrderIndustryBean> getOrder_industry() {
                return order_industry;
            }

            public void setOrder_industry(List<OrderIndustryBean> order_industry) {
                this.order_industry = order_industry;
            }

            public static class OrderIndustryBean {
                /**
                 * industry : 11
                 * func : 240101
                 * lingyu : 111300
                 */

                private String industry;
                private String func;
                private String lingyu;

                public String getIndustry() {
                    return industry;
                }

                public void setIndustry(String industry) {
                    this.industry = industry;
                }

                public String getFunc() {
                    return func;
                }

                public void setFunc(String func) {
                    this.func = func;
                }

                public String getLingyu() {
                    return lingyu;
                }

                public void setLingyu(String lingyu) {
                    this.lingyu = lingyu;
                }

                @Override
                public String toString() {
                    return "OrderIndustryBean{" +
                            "industry='" + industry + '\'' +
                            ", func='" + func + '\'' +
                            ", lingyu='" + lingyu + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "OrderInfoBean{" +
                        "user_id='" + user_id + '\'' +
                        ", jobtype='" + jobtype + '\'' +
                        ", industry='" + industry + '\'' +
                        ", func='" + func + '\'' +
                        ", workarea='" + workarea + '\'' +
                        ", zhixi='" + zhixi + '\'' +
                        ", order_salary='" + order_salary + '\'' +
                        ", order_salary_noshow='" + order_salary_noshow + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", jobname='" + jobname + '\'' +
                        ", lingyu='" + lingyu + '\'' +
                        ", order_yearsalary='" + order_yearsalary + '\'' +
                        ", current_workstate='" + current_workstate + '\'' +
                        ", castbehalf='" + castbehalf + '\'' +
                        ", order_industry=" + order_industry +
                        '}';
            }
        }

        public static class AssessInfoBean {
            /**
             * user_id : 9491509
             * introduction : 计算机等级记得记得就
             * resume_id : 1
             * resume_language : zh
             * virtue :
             * weakness :
             */

            private String user_id;
            private String introduction;
            private String resume_id;
            private String resume_language;
            private String virtue;
            private String weakness;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getVirtue() {
                return virtue;
            }

            public void setVirtue(String virtue) {
                this.virtue = virtue;
            }

            public String getWeakness() {
                return weakness;
            }

            public void setWeakness(String weakness) {
                this.weakness = weakness;
            }

            @Override
            public String toString() {
                return "AssessInfoBean{" +
                        "user_id='" + user_id + '\'' +
                        ", introduction='" + introduction + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", virtue='" + virtue + '\'' +
                        ", weakness='" + weakness + '\'' +
                        '}';
            }
        }

        public static class LanguageListBean {
            /**
             * langname : 11
             * user_id : 9491509
             * read_level : 4
             * speak_level : 3
             * grade_exam : [{"id":"1103","score":"568"}]
             */

            private String langname;
            private String user_id;
            private String read_level;
            private String speak_level;
            private List<GradeExamBean> grade_exam;

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

            public List<GradeExamBean> getGrade_exam() {
                return grade_exam;
            }

            public void setGrade_exam(List<GradeExamBean> grade_exam) {
                this.grade_exam = grade_exam;
            }

            public static class GradeExamBean {
                /**
                 * id : 1103
                 * score : 568
                 */

                private String id;
                private String score;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                @Override
                public String toString() {
                    return "GradeExamBean{" +
                            "id='" + id + '\'' +
                            ", score='" + score + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "LanguageListBean{" +
                        "langname='" + langname + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", read_level='" + read_level + '\'' +
                        ", speak_level='" + speak_level + '\'' +
                        ", grade_exam=" + grade_exam +
                        '}';
            }
        }

        public static class EducationListBean {
            /**
             * user_id : 9491509
             * fromyear : 2004
             * frommonth : 10
             * toyear : 2009
             * tomonth : 1
             * schoolname : bdjdj
             * moremajor : bdjjdj
             * degree : 16
             * edudetail :
             * is_overseas : 0
             * country : 0
             * resume_id : 1
             * resume_language : zh
             * recruit_students : 0
             * degreecerti_no :
             * a_degreecerti_no :
             * education_id : 9558623
             */

            private String user_id;
            private String fromyear;
            private String frommonth;
            private String toyear;
            private String tomonth;
            private String schoolname;
            private String moremajor;
            private String degree;
            private String edudetail;
            private String is_overseas;
            private String country;
            private String resume_id;
            private String resume_language;
            private String recruit_students;
            private String degreecerti_no;
            private String a_degreecerti_no;
            private String education_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getFromyear() {
                return fromyear;
            }

            public void setFromyear(String fromyear) {
                this.fromyear = fromyear;
            }

            public String getFrommonth() {
                return frommonth;
            }

            public void setFrommonth(String frommonth) {
                this.frommonth = frommonth;
            }

            public String getToyear() {
                return toyear;
            }

            public void setToyear(String toyear) {
                this.toyear = toyear;
            }

            public String getTomonth() {
                return tomonth;
            }

            public void setTomonth(String tomonth) {
                this.tomonth = tomonth;
            }

            public String getSchoolname() {
                return schoolname;
            }

            public void setSchoolname(String schoolname) {
                this.schoolname = schoolname;
            }

            public String getMoremajor() {
                return moremajor;
            }

            public void setMoremajor(String moremajor) {
                this.moremajor = moremajor;
            }

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getEdudetail() {
                return edudetail;
            }

            public void setEdudetail(String edudetail) {
                this.edudetail = edudetail;
            }

            public String getIs_overseas() {
                return is_overseas;
            }

            public void setIs_overseas(String is_overseas) {
                this.is_overseas = is_overseas;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getRecruit_students() {
                return recruit_students;
            }

            public void setRecruit_students(String recruit_students) {
                this.recruit_students = recruit_students;
            }

            public String getDegreecerti_no() {
                return degreecerti_no;
            }

            public void setDegreecerti_no(String degreecerti_no) {
                this.degreecerti_no = degreecerti_no;
            }

            public String getA_degreecerti_no() {
                return a_degreecerti_no;
            }

            public void setA_degreecerti_no(String a_degreecerti_no) {
                this.a_degreecerti_no = a_degreecerti_no;
            }

            public String getEducation_id() {
                return education_id;
            }

            public void setEducation_id(String education_id) {
                this.education_id = education_id;
            }

            @Override
            public String toString() {
                return "EducationListBean{" +
                        "user_id='" + user_id + '\'' +
                        ", fromyear='" + fromyear + '\'' +
                        ", frommonth='" + frommonth + '\'' +
                        ", toyear='" + toyear + '\'' +
                        ", tomonth='" + tomonth + '\'' +
                        ", schoolname='" + schoolname + '\'' +
                        ", moremajor='" + moremajor + '\'' +
                        ", degree='" + degree + '\'' +
                        ", edudetail='" + edudetail + '\'' +
                        ", is_overseas='" + is_overseas + '\'' +
                        ", country='" + country + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", recruit_students='" + recruit_students + '\'' +
                        ", degreecerti_no='" + degreecerti_no + '\'' +
                        ", a_degreecerti_no='" + a_degreecerti_no + '\'' +
                        ", education_id='" + education_id + '\'' +
                        '}';
            }
        }

        public static class ExperienceListBean {
            /**
             * user_id : 9491509
             * fromyear : 2014
             * frommonth : 1
             * toyear : 2016
             * tomonth : 9
             * company : bdjdjdj
             * companyhide : 0
             * industry : 0
             * companytype : 0
             * stuffmunber : 0
             * division :
             * companyaddress : 1200
             * position : bidhdh
             * responsiblity : bxjdndmmdmd
             * offreason :
             * achievement :
             * zhixi : 0
             * zhicheng : 0
             * is_overseas : 0
             * country : 0
             * resume_id : 1
             * resume_language : zh
             * lingyu : 0
             * func : 0
             * salary : 64646
             * salary_hide : 0
             * reterence :
             * yearsalary : 0
             * underling :
             * superior :
             * experience_id : 10148692
             */

            private String user_id;
            private String fromyear;
            private String frommonth;
            private String toyear;
            private String tomonth;
            private String company;
            private String companyhide;
            private String industry;
            private String companytype;
            private String stuffmunber;
            private String division;
            private String companyaddress;
            private String position;
            private String responsiblity;
            private String offreason;
            private String achievement;
            private String zhixi;
            private String zhicheng;
            private String is_overseas;
            private String country;
            private String resume_id;
            private String resume_language;
            private String lingyu;
            private String func;
            private String salary;
            private String salary_hide;
            private String reterence;
            private String yearsalary;
            private String underling;
            private String superior;
            private String experience_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getFromyear() {
                return fromyear;
            }

            public void setFromyear(String fromyear) {
                this.fromyear = fromyear;
            }

            public String getFrommonth() {
                return frommonth;
            }

            public void setFrommonth(String frommonth) {
                this.frommonth = frommonth;
            }

            public String getToyear() {
                return toyear;
            }

            public void setToyear(String toyear) {
                this.toyear = toyear;
            }

            public String getTomonth() {
                return tomonth;
            }

            public void setTomonth(String tomonth) {
                this.tomonth = tomonth;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getCompanyhide() {
                return companyhide;
            }

            public void setCompanyhide(String companyhide) {
                this.companyhide = companyhide;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public String getCompanytype() {
                return companytype;
            }

            public void setCompanytype(String companytype) {
                this.companytype = companytype;
            }

            public String getStuffmunber() {
                return stuffmunber;
            }

            public void setStuffmunber(String stuffmunber) {
                this.stuffmunber = stuffmunber;
            }

            public String getDivision() {
                return division;
            }

            public void setDivision(String division) {
                this.division = division;
            }

            public String getCompanyaddress() {
                return companyaddress;
            }

            public void setCompanyaddress(String companyaddress) {
                this.companyaddress = companyaddress;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getResponsiblity() {
                return responsiblity;
            }

            public void setResponsiblity(String responsiblity) {
                this.responsiblity = responsiblity;
            }

            public String getOffreason() {
                return offreason;
            }

            public void setOffreason(String offreason) {
                this.offreason = offreason;
            }

            public String getAchievement() {
                return achievement;
            }

            public void setAchievement(String achievement) {
                this.achievement = achievement;
            }

            public String getZhixi() {
                return zhixi;
            }

            public void setZhixi(String zhixi) {
                this.zhixi = zhixi;
            }

            public String getZhicheng() {
                return zhicheng;
            }

            public void setZhicheng(String zhicheng) {
                this.zhicheng = zhicheng;
            }

            public String getIs_overseas() {
                return is_overseas;
            }

            public void setIs_overseas(String is_overseas) {
                this.is_overseas = is_overseas;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getLingyu() {
                return lingyu;
            }

            public void setLingyu(String lingyu) {
                this.lingyu = lingyu;
            }

            public String getFunc() {
                return func;
            }

            public void setFunc(String func) {
                this.func = func;
            }

            public String getSalary() {
                return salary;
            }

            public void setSalary(String salary) {
                this.salary = salary;
            }

            public String getSalary_hide() {
                return salary_hide;
            }

            public void setSalary_hide(String salary_hide) {
                this.salary_hide = salary_hide;
            }

            public String getReterence() {
                return reterence;
            }

            public void setReterence(String reterence) {
                this.reterence = reterence;
            }

            public String getYearsalary() {
                return yearsalary;
            }

            public void setYearsalary(String yearsalary) {
                this.yearsalary = yearsalary;
            }

            public String getUnderling() {
                return underling;
            }

            public void setUnderling(String underling) {
                this.underling = underling;
            }

            public String getSuperior() {
                return superior;
            }

            public void setSuperior(String superior) {
                this.superior = superior;
            }

            public String getExperience_id() {
                return experience_id;
            }

            public void setExperience_id(String experience_id) {
                this.experience_id = experience_id;
            }

            @Override
            public String toString() {
                return "ExperienceListBean{" +
                        "user_id='" + user_id + '\'' +
                        ", fromyear='" + fromyear + '\'' +
                        ", frommonth='" + frommonth + '\'' +
                        ", toyear='" + toyear + '\'' +
                        ", tomonth='" + tomonth + '\'' +
                        ", company='" + company + '\'' +
                        ", companyhide='" + companyhide + '\'' +
                        ", industry='" + industry + '\'' +
                        ", companytype='" + companytype + '\'' +
                        ", stuffmunber='" + stuffmunber + '\'' +
                        ", division='" + division + '\'' +
                        ", companyaddress='" + companyaddress + '\'' +
                        ", position='" + position + '\'' +
                        ", responsiblity='" + responsiblity + '\'' +
                        ", offreason='" + offreason + '\'' +
                        ", achievement='" + achievement + '\'' +
                        ", zhixi='" + zhixi + '\'' +
                        ", zhicheng='" + zhicheng + '\'' +
                        ", is_overseas='" + is_overseas + '\'' +
                        ", country='" + country + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", lingyu='" + lingyu + '\'' +
                        ", func='" + func + '\'' +
                        ", salary='" + salary + '\'' +
                        ", salary_hide='" + salary_hide + '\'' +
                        ", reterence='" + reterence + '\'' +
                        ", yearsalary='" + yearsalary + '\'' +
                        ", underling='" + underling + '\'' +
                        ", superior='" + superior + '\'' +
                        ", experience_id='" + experience_id + '\'' +
                        '}';
            }
        }

        public static class ProjectListBean {
            /**
             * user_id : 9491509
             * fromyear : 2008
             * frommonth : 10
             * toyear : 2015
             * tomonth : 12
             * projectname : 红色警戒十几万
             * projectdesc : 家电家具单开双控
             * responsibility : 抱着你辛苦辛苦面对面的
             * position : 那乍办
             * resume_id : 1
             * resume_language : zh
             * companyname :
             * companyhide : 0
             * achievement :
             * reterence :
             * project_id : 4523385
             */

            private String user_id;
            private String fromyear;
            private String frommonth;
            private String toyear;
            private String tomonth;
            private String projectname;
            private String projectdesc;
            private String responsibility;
            private String position;
            private String resume_id;
            private String resume_language;
            private String companyname;
            private String companyhide;
            private String achievement;
            private String reterence;
            private String project_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getFromyear() {
                return fromyear;
            }

            public void setFromyear(String fromyear) {
                this.fromyear = fromyear;
            }

            public String getFrommonth() {
                return frommonth;
            }

            public void setFrommonth(String frommonth) {
                this.frommonth = frommonth;
            }

            public String getToyear() {
                return toyear;
            }

            public void setToyear(String toyear) {
                this.toyear = toyear;
            }

            public String getTomonth() {
                return tomonth;
            }

            public void setTomonth(String tomonth) {
                this.tomonth = tomonth;
            }

            public String getProjectname() {
                return projectname;
            }

            public void setProjectname(String projectname) {
                this.projectname = projectname;
            }

            public String getProjectdesc() {
                return projectdesc;
            }

            public void setProjectdesc(String projectdesc) {
                this.projectdesc = projectdesc;
            }

            public String getResponsibility() {
                return responsibility;
            }

            public void setResponsibility(String responsibility) {
                this.responsibility = responsibility;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getCompanyhide() {
                return companyhide;
            }

            public void setCompanyhide(String companyhide) {
                this.companyhide = companyhide;
            }

            public String getAchievement() {
                return achievement;
            }

            public void setAchievement(String achievement) {
                this.achievement = achievement;
            }

            public String getReterence() {
                return reterence;
            }

            public void setReterence(String reterence) {
                this.reterence = reterence;
            }

            public String getProject_id() {
                return project_id;
            }

            public void setProject_id(String project_id) {
                this.project_id = project_id;
            }

            @Override
            public String toString() {
                return "ProjectListBean{" +
                        "user_id='" + user_id + '\'' +
                        ", fromyear='" + fromyear + '\'' +
                        ", frommonth='" + frommonth + '\'' +
                        ", toyear='" + toyear + '\'' +
                        ", tomonth='" + tomonth + '\'' +
                        ", projectname='" + projectname + '\'' +
                        ", projectdesc='" + projectdesc + '\'' +
                        ", responsibility='" + responsibility + '\'' +
                        ", position='" + position + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", companyname='" + companyname + '\'' +
                        ", companyhide='" + companyhide + '\'' +
                        ", achievement='" + achievement + '\'' +
                        ", reterence='" + reterence + '\'' +
                        ", project_id='" + project_id + '\'' +
                        '}';
            }
        }

        public static class PlantListBean {
            /**
             * user_id : 9491509
             * fromyear : 2013
             * frommonth : 11
             * toyear : 2015
             * tomonth : 12
             * institution : 锦江大酒店就
             * place : 1200
             * course : 计算机技术
             * certification : 计算机考试
             * traindetail : 嗯就是计算机三级
             * resume_id : 1
             * resume_language : zh
             * certi_number :
             * plant_id : 1938709
             */

            private String user_id;
            private String fromyear;
            private String frommonth;
            private String toyear;
            private String tomonth;
            private String institution;
            private String place;
            private String course;
            private String certification;
            private String traindetail;
            private String resume_id;
            private String resume_language;
            private String certi_number;
            private String plant_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getFromyear() {
                return fromyear;
            }

            public void setFromyear(String fromyear) {
                this.fromyear = fromyear;
            }

            public String getFrommonth() {
                return frommonth;
            }

            public void setFrommonth(String frommonth) {
                this.frommonth = frommonth;
            }

            public String getToyear() {
                return toyear;
            }

            public void setToyear(String toyear) {
                this.toyear = toyear;
            }

            public String getTomonth() {
                return tomonth;
            }

            public void setTomonth(String tomonth) {
                this.tomonth = tomonth;
            }

            public String getInstitution() {
                return institution;
            }

            public void setInstitution(String institution) {
                this.institution = institution;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getCourse() {
                return course;
            }

            public void setCourse(String course) {
                this.course = course;
            }

            public String getCertification() {
                return certification;
            }

            public void setCertification(String certification) {
                this.certification = certification;
            }

            public String getTraindetail() {
                return traindetail;
            }

            public void setTraindetail(String traindetail) {
                this.traindetail = traindetail;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getCerti_number() {
                return certi_number;
            }

            public void setCerti_number(String certi_number) {
                this.certi_number = certi_number;
            }

            public String getPlant_id() {
                return plant_id;
            }

            public void setPlant_id(String plant_id) {
                this.plant_id = plant_id;
            }

            @Override
            public String toString() {
                return "PlantListBean{" +
                        "user_id='" + user_id + '\'' +
                        ", fromyear='" + fromyear + '\'' +
                        ", frommonth='" + frommonth + '\'' +
                        ", toyear='" + toyear + '\'' +
                        ", tomonth='" + tomonth + '\'' +
                        ", institution='" + institution + '\'' +
                        ", place='" + place + '\'' +
                        ", course='" + course + '\'' +
                        ", certification='" + certification + '\'' +
                        ", traindetail='" + traindetail + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", certi_number='" + certi_number + '\'' +
                        ", plant_id='" + plant_id + '\'' +
                        '}';
            }
        }

        public static class SkillListBean {
            /**
             * user_id : 9491509
             * usetime : 964
             * ability : 5
             * skilltitle : 贾九转金身决
             * resume_id : 1
             * resume_language : zh
             * skill_id : 3152630
             */

            private String user_id;
            private String usetime;
            private String ability;
            private String skilltitle;
            private String resume_id;
            private String resume_language;
            private String skill_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUsetime() {
                return usetime;
            }

            public void setUsetime(String usetime) {
                this.usetime = usetime;
            }

            public String getAbility() {
                return ability;
            }

            public void setAbility(String ability) {
                this.ability = ability;
            }

            public String getSkilltitle() {
                return skilltitle;
            }

            public void setSkilltitle(String skilltitle) {
                this.skilltitle = skilltitle;
            }

            public String getResume_id() {
                return resume_id;
            }

            public void setResume_id(String resume_id) {
                this.resume_id = resume_id;
            }

            public String getResume_language() {
                return resume_language;
            }

            public void setResume_language(String resume_language) {
                this.resume_language = resume_language;
            }

            public String getSkill_id() {
                return skill_id;
            }

            public void setSkill_id(String skill_id) {
                this.skill_id = skill_id;
            }

            @Override
            public String toString() {
                return "SkillListBean{" +
                        "user_id='" + user_id + '\'' +
                        ", usetime='" + usetime + '\'' +
                        ", ability='" + ability + '\'' +
                        ", skilltitle='" + skilltitle + '\'' +
                        ", resume_id='" + resume_id + '\'' +
                        ", resume_language='" + resume_language + '\'' +
                        ", skill_id='" + skill_id + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ResumeBean{" +
                "error_code=" + error_code +
                ", resume_info=" + resume_info +
                ", _run_time='" + _run_time + '\'' +
                '}';
    }
}
