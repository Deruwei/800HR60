package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/5.
 */

public class RecommendJobBean {


    /**
     * error_code : 0
     * jobs_list : [{"job_id":"YPRbV","job_name":"建筑设计师","monthly_pay":"6000","monthly_pay_to":"12000","workyear":"三年以上","issue_date":"2017-12-28","enterprise_name":"天津中怡建筑规划设计有限公司","work_type":"全职","workplace":"天津-南开区","stuff_munber":"100 - 499人","synopsis":"岗位职责:<br />\n1、施工图纸设计<br />\n2、工程设计全过程以及现场技术服务；<br />\n3、按流程签署技术文件及变更洽商。<br />\n任职资格：<br />\n1、建筑学或相关专业本科以上学历；<br />\n2、5年左右建筑工程行业设计或规划工作经验；<br />\n3、具有较强的大型工程项目组织经验；<br />\n4、熟悉项目设计流程，具有良好的沟通及表达能力，责任心强，有敬业精神；<br />\n5、具有项目场地设计（总图设计）、规划设计、建筑设计等方面的能力；<br />\n6、熟知城市规划、建筑设计、构造学、给排水、暖道、电气、企业管理、相关法律等知识；<br />\n7、对相关专业（景观、结构、设备）设计有较深的认识和协调能力，具备较丰富的现场施工配合经验；<br />\n8、熟练掌握办公自动化、CAD制图软件和相关软件操作；","number":"若干","is_show_pay_interview":"0","poster_state":"0","topjob_type":"1","show_language":"","work_area":"1304","department":"天津中怡建筑规划设计有限公司","email":"","job_number":"4981158","subordinate_num":"0","superior":"","other_benefits":"","recruit_students":"0","job_slogan":"","address":"3671633@address","applied_nums":"31","applied_time":"0","favourite_time":"0","posterimg":"","ente_property":[],"enterprise_id":"rfkht","vip_ente_url":"","func":"240102","salary":"6000 - 12000","year_salary":"","study":"本科","company_type":"私营/民营企业","lingyu_name":"建筑设计,园林景观,造价","industry":"11","industry_name":"建筑","release_date":"1514131200","expiration_date":"1538323199","nautica":"117.163443,39.120473","age":"","is_favourite":0,"is_expire":0,"is_apply":0}]
     * navpage_info : {"current_page":"1","total_pages":4,"record_nums":65,"page_nums":"20"}
     */

    private String error_code;
    private NavpageInfoBean navpage_info;
    private List<JobsListBean> jobs_list;

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

    public List<JobsListBean> getJobs_list() {
        return jobs_list;
    }

    public void setJobs_list(List<JobsListBean> jobs_list) {
        this.jobs_list = jobs_list;
    }

    public static class NavpageInfoBean {
        /**
         * current_page : 1
         * total_pages : 4
         * record_nums : 65
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

    public static class JobsListBean {
        /**
         * job_id : YPRbV
         * job_name : 建筑设计师
         * monthly_pay : 6000
         * monthly_pay_to : 12000
         * workyear : 三年以上
         * issue_date : 2017-12-28
         * enterprise_name : 天津中怡建筑规划设计有限公司
         * work_type : 全职
         * workplace : 天津-南开区
         * stuff_munber : 100 - 499人
         * synopsis : 岗位职责:<br />
         1、施工图纸设计<br />
         2、工程设计全过程以及现场技术服务；<br />
         3、按流程签署技术文件及变更洽商。<br />
         任职资格：<br />
         1、建筑学或相关专业本科以上学历；<br />
         2、5年左右建筑工程行业设计或规划工作经验；<br />
         3、具有较强的大型工程项目组织经验；<br />
         4、熟悉项目设计流程，具有良好的沟通及表达能力，责任心强，有敬业精神；<br />
         5、具有项目场地设计（总图设计）、规划设计、建筑设计等方面的能力；<br />
         6、熟知城市规划、建筑设计、构造学、给排水、暖道、电气、企业管理、相关法律等知识；<br />
         7、对相关专业（景观、结构、设备）设计有较深的认识和协调能力，具备较丰富的现场施工配合经验；<br />
         8、熟练掌握办公自动化、CAD制图软件和相关软件操作；
         * number : 若干
         * is_show_pay_interview : 0
         * poster_state : 0
         * topjob_type : 1
         * show_language :
         * work_area : 1304
         * department : 天津中怡建筑规划设计有限公司
         * email :
         * job_number : 4981158
         * subordinate_num : 0
         * superior :
         * other_benefits :
         * recruit_students : 0
         * job_slogan :
         * address : 3671633@address
         * applied_nums : 31
         * applied_time : 0
         * favourite_time : 0
         * posterimg :
         * ente_property : []
         * enterprise_id : rfkht
         * vip_ente_url :
         * func : 240102
         * salary : 6000 - 12000
         * year_salary :
         * study : 本科
         * company_type : 私营/民营企业
         * lingyu_name : 建筑设计,园林景观,造价
         * industry : 11
         * industry_name : 建筑
         * release_date : 1514131200
         * expiration_date : 1538323199
         * nautica : 117.163443,39.120473
         * age :
         * is_favourite : 0
         * is_expire : 0
         * is_apply : 0
         */

        private String job_id;
        private String job_name;
        private String monthly_pay;
        private String monthly_pay_to;
        private String workyear;
        private String issue_date;
        private String enterprise_name;
        private String work_type;
        private String workplace;
        private String stuff_munber;
        private String synopsis;
        private String number;
        private String is_show_pay_interview;
        private String poster_state;
        private String topjob_type;
        private String show_language;
        private String work_area;
        private String department;
        private String email;
        private String ent_logo;
        private String job_number;
        private String subordinate_num;
        private String superior;
        private String other_benefits;
        private String recruit_students;
        private String job_slogan;
        private String address;
        private String applied_nums;
        private String applied_time;
        private String favourite_time;
        private String posterimg;
        private String enterprise_id;
        private String vip_ente_url;
        private String func;
        private String salary;
        private String year_salary;
        private String study;
        private String company_type;
        private String lingyu_name;
        private String industry;
        private String industry_name;
        private String release_date;
        private String expiration_date;
        private String nautica;
        private String age;
        private int is_favourite;
        private int is_expire;
        private int is_apply;
        private int is_urgent;
        private int top_id;
        private int is_shoufa;
        private boolean isTop;
        private boolean isCheck;


        public int getIs_shoufa() {
            return is_shoufa;
        }

        public void setIs_shoufa(int is_shoufa) {
            this.is_shoufa = is_shoufa;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isTop() {
            return isTop;
        }

        public void setTop(boolean top) {
            isTop = top;
        }

        public int getIs_urgent() {
            return is_urgent;
        }

        public void setIs_urgent(int is_urgent) {
            this.is_urgent = is_urgent;
        }

        public int getTop_id() {
            return top_id;
        }

        public void setTop_id(int top_id) {
            this.top_id = top_id;
        }

        private List<?> ente_property;

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

        public String getMonthly_pay() {
            return monthly_pay;
        }

        public void setMonthly_pay(String monthly_pay) {
            this.monthly_pay = monthly_pay;
        }

        public String getMonthly_pay_to() {
            return monthly_pay_to;
        }

        public String getEnt_logo() {
            return ent_logo;
        }

        public void setEnt_logo(String ent_logo) {
            this.ent_logo = ent_logo;
        }

        public void setMonthly_pay_to(String monthly_pay_to) {
            this.monthly_pay_to = monthly_pay_to;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getIssue_date() {
            return issue_date;
        }

        public void setIssue_date(String issue_date) {
            this.issue_date = issue_date;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        public String getWork_type() {
            return work_type;
        }

        public void setWork_type(String work_type) {
            this.work_type = work_type;
        }

        public String getWorkplace() {
            return workplace;
        }

        public void setWorkplace(String workplace) {
            this.workplace = workplace;
        }

        public String getStuff_munber() {
            return stuff_munber;
        }

        public void setStuff_munber(String stuff_munber) {
            this.stuff_munber = stuff_munber;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getIs_show_pay_interview() {
            return is_show_pay_interview;
        }

        public void setIs_show_pay_interview(String is_show_pay_interview) {
            this.is_show_pay_interview = is_show_pay_interview;
        }

        public String getPoster_state() {
            return poster_state;
        }

        public void setPoster_state(String poster_state) {
            this.poster_state = poster_state;
        }

        public String getTopjob_type() {
            return topjob_type;
        }

        public void setTopjob_type(String topjob_type) {
            this.topjob_type = topjob_type;
        }

        public String getShow_language() {
            return show_language;
        }

        public void setShow_language(String show_language) {
            this.show_language = show_language;
        }

        public String getWork_area() {
            return work_area;
        }

        public void setWork_area(String work_area) {
            this.work_area = work_area;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
        }

        public String getSubordinate_num() {
            return subordinate_num;
        }

        public void setSubordinate_num(String subordinate_num) {
            this.subordinate_num = subordinate_num;
        }

        public String getSuperior() {
            return superior;
        }

        public void setSuperior(String superior) {
            this.superior = superior;
        }

        public String getOther_benefits() {
            return other_benefits;
        }

        public void setOther_benefits(String other_benefits) {
            this.other_benefits = other_benefits;
        }

        public String getRecruit_students() {
            return recruit_students;
        }

        public void setRecruit_students(String recruit_students) {
            this.recruit_students = recruit_students;
        }

        public String getJob_slogan() {
            return job_slogan;
        }

        public void setJob_slogan(String job_slogan) {
            this.job_slogan = job_slogan;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getApplied_nums() {
            return applied_nums;
        }

        public void setApplied_nums(String applied_nums) {
            this.applied_nums = applied_nums;
        }

        public String getApplied_time() {
            return applied_time;
        }

        public void setApplied_time(String applied_time) {
            this.applied_time = applied_time;
        }

        public String getFavourite_time() {
            return favourite_time;
        }

        public void setFavourite_time(String favourite_time) {
            this.favourite_time = favourite_time;
        }

        public String getPosterimg() {
            return posterimg;
        }

        public void setPosterimg(String posterimg) {
            this.posterimg = posterimg;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getVip_ente_url() {
            return vip_ente_url;
        }

        public void setVip_ente_url(String vip_ente_url) {
            this.vip_ente_url = vip_ente_url;
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

        public String getYear_salary() {
            return year_salary;
        }

        public void setYear_salary(String year_salary) {
            this.year_salary = year_salary;
        }

        public String getStudy() {
            return study;
        }

        public void setStudy(String study) {
            this.study = study;
        }

        public String getCompany_type() {
            return company_type;
        }

        public void setCompany_type(String company_type) {
            this.company_type = company_type;
        }

        public String getLingyu_name() {
            return lingyu_name;
        }

        public void setLingyu_name(String lingyu_name) {
            this.lingyu_name = lingyu_name;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getIndustry_name() {
            return industry_name;
        }

        public void setIndustry_name(String industry_name) {
            this.industry_name = industry_name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getExpiration_date() {
            return expiration_date;
        }

        public void setExpiration_date(String expiration_date) {
            this.expiration_date = expiration_date;
        }

        public String getNautica() {
            return nautica;
        }

        public void setNautica(String nautica) {
            this.nautica = nautica;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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

        public List<?> getEnte_property() {
            return ente_property;
        }

        public void setEnte_property(List<?> ente_property) {
            this.ente_property = ente_property;
        }

        @Override
        public String toString() {
            return "JobsListBean{" +
                    "job_id='" + job_id + '\'' +
                    ", job_name='" + job_name + '\'' +
                    ", monthly_pay='" + monthly_pay + '\'' +
                    ", monthly_pay_to='" + monthly_pay_to + '\'' +
                    ", workyear='" + workyear + '\'' +
                    ", issue_date='" + issue_date + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", work_type='" + work_type + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", stuff_munber='" + stuff_munber + '\'' +
                    ", synopsis='" + synopsis + '\'' +
                    ", number='" + number + '\'' +
                    ", is_show_pay_interview='" + is_show_pay_interview + '\'' +
                    ", poster_state='" + poster_state + '\'' +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", show_language='" + show_language + '\'' +
                    ", work_area='" + work_area + '\'' +
                    ", department='" + department + '\'' +
                    ", email='" + email + '\'' +
                    ", ent_logo='" + ent_logo + '\'' +
                    ", job_number='" + job_number + '\'' +
                    ", subordinate_num='" + subordinate_num + '\'' +
                    ", superior='" + superior + '\'' +
                    ", other_benefits='" + other_benefits + '\'' +
                    ", recruit_students='" + recruit_students + '\'' +
                    ", job_slogan='" + job_slogan + '\'' +
                    ", address='" + address + '\'' +
                    ", applied_nums='" + applied_nums + '\'' +
                    ", applied_time='" + applied_time + '\'' +
                    ", favourite_time='" + favourite_time + '\'' +
                    ", posterimg='" + posterimg + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", vip_ente_url='" + vip_ente_url + '\'' +
                    ", func='" + func + '\'' +
                    ", salary='" + salary + '\'' +
                    ", year_salary='" + year_salary + '\'' +
                    ", study='" + study + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", lingyu_name='" + lingyu_name + '\'' +
                    ", industry='" + industry + '\'' +
                    ", industry_name='" + industry_name + '\'' +
                    ", release_date='" + release_date + '\'' +
                    ", expiration_date='" + expiration_date + '\'' +
                    ", nautica='" + nautica + '\'' +
                    ", age='" + age + '\'' +
                    ", is_favourite=" + is_favourite +
                    ", is_expire=" + is_expire +
                    ", is_apply=" + is_apply +
                    ", is_urgent=" + is_urgent +
                    ", top_id=" + top_id +
                    ", is_shoufa=" + is_shoufa +
                    ", isTop=" + isTop +
                    ", isCheck=" + isCheck +
                    ", ente_property=" + ente_property +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RecommendJobBean{" +
                "error_code='" + error_code + '\'' +
                ", navpage_info=" + navpage_info +
                ", jobs_list=" + jobs_list +
                '}';
    }
}
