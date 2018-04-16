package com.hr.ui.bean;

import java.io.Serializable;

/**
 * Created by wdr on 2018/1/11.
 */

public class PositionBean {

    /**
     * error_code : 0
     * job_info : {"job_id":"Zemo2","job_name":"人事专员","issue_date":"2017-12-29","enterprise_name":"莆田新明宝化学工业有限公司","work_area":"2004","synopsis":"岗位职责：\n1.负责公司人才的招聘（网络招聘和现场招聘），员工入、离职等手续办理；2.搜集简历,对简历进行分类,筛选,安排聘前测试,确定面试名单,通知应聘者前来面试,对应聘者进行初步面试（笔试）,出具综合评价意见；3.负责建立企业人才和储备库,做好简历管理与信息保密工作,跟踪和收集同行人才动态,吸引优秀人才加盟公司；4.负责办理员工、离职、转正等相关工作，建立人事档案，及时更新员工的个人信息和资料；5.协助处理员工劳动合同的签订、续签、解除，岗位异动，薪资调整，考勤等相关工作。6.工作经验一年以上，有同行工作经验优先考虑！7.优秀应届生亦可。","monthly_pay":"3000","monthly_pay_to":"4499","department":"莆田新明宝化学工业有限公司","number":"2人","email":"","phone":"","workyear":"不限","linkman":"","post_rank":"","work_type":"全职","invite_major":"","job_number":"","did":"1","is_show_pay_interview":"0","parent_job_id":"0","poster_state":"0","is_from_net":"0","topjob_type":"1","subordinate_num":"0","superior":"","other_benefits":"","recruit_students":"0","job_slogan":"","show_language":"","address":"","zipcode":"","enterprise_id":"5FySK","lingyu":"291108,291107","ent_logo":"","fax":"","homepage":"","stuff_munber":"50 - 99人","func":"295102","salary":"3000 - 4499","year_salary":"","workplace":"福建-莆田市","study":"本科及以上","company_type":"外商独资","lingyu_name":"树脂,新材料","industry":"29","industry_name":"化工","release_date":"1488470400","expiration_date":"1520006399","language":"","nautica":"","applied_nums":"1","age":"","favourite_time":"","applied_time":"","posterimg":"","is_favourite":0,"is_expire":0,"is_apply":0,"baidu_map_lon":"","baidu_map_lat":""}
     */

    private String error_code;
    private JobInfoBean job_info;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public JobInfoBean getJob_info() {
        return job_info;
    }

    public void setJob_info(JobInfoBean job_info) {
        this.job_info = job_info;
    }

    public static class JobInfoBean  implements Serializable{
        /**
         * job_id : Zemo2
         * job_name : 人事专员
         * issue_date : 2017-12-29
         * enterprise_name : 莆田新明宝化学工业有限公司
         * work_area : 2004
         * synopsis : 岗位职责：
         1.负责公司人才的招聘（网络招聘和现场招聘），员工入、离职等手续办理；2.搜集简历,对简历进行分类,筛选,安排聘前测试,确定面试名单,通知应聘者前来面试,对应聘者进行初步面试（笔试）,出具综合评价意见；3.负责建立企业人才和储备库,做好简历管理与信息保密工作,跟踪和收集同行人才动态,吸引优秀人才加盟公司；4.负责办理员工、离职、转正等相关工作，建立人事档案，及时更新员工的个人信息和资料；5.协助处理员工劳动合同的签订、续签、解除，岗位异动，薪资调整，考勤等相关工作。6.工作经验一年以上，有同行工作经验优先考虑！7.优秀应届生亦可。
         * monthly_pay : 3000
         * monthly_pay_to : 4499
         * department : 莆田新明宝化学工业有限公司
         * number : 2人
         * email :
         * phone :
         * workyear : 不限
         * linkman :
         * post_rank :
         * work_type : 全职
         * invite_major :
         * job_number :
         * did : 1
         * is_show_pay_interview : 0
         * parent_job_id : 0
         * poster_state : 0
         * is_from_net : 0
         * topjob_type : 1
         * subordinate_num : 0
         * superior :
         * other_benefits :
         * recruit_students : 0
         * job_slogan :
         * show_language :
         * address :
         * zipcode :
         * enterprise_id : 5FySK
         * lingyu : 291108,291107
         * ent_logo :
         * fax :
         * homepage :
         * stuff_munber : 50 - 99人
         * func : 295102
         * salary : 3000 - 4499
         * year_salary :
         * workplace : 福建-莆田市
         * study : 本科及以上
         * company_type : 外商独资
         * lingyu_name : 树脂,新材料
         * industry : 29
         * industry_name : 化工
         * release_date : 1488470400
         * expiration_date : 1520006399
         * language :
         * nautica :
         * applied_nums : 1
         * age :
         * favourite_time :
         * applied_time :
         * posterimg :
         * is_favourite : 0
         * is_expire : 0
         * is_apply : 0
         * baidu_map_lon :
         * baidu_map_lat :
         */

        private String job_id;
        private String job_name;
        private String issue_date;
        private String enterprise_name;
        private String work_area;
        private String synopsis;
        private String monthly_pay;
        private String monthly_pay_to;
        private String department;
        private String number;
        private String email;
        private String phone;
        private String workyear;
        private String linkman;
        private String post_rank;
        private String work_type;
        private String invite_major;
        private String job_number;
        private String did;
        private String is_show_pay_interview;
        private String parent_job_id;
        private String poster_state;
        private String is_from_net;
        private String topjob_type;
        private String subordinate_num;
        private String superior;
        private String other_benefits;
        private String recruit_students;
        private String job_slogan;
        private String show_language;
        private String address;
        private String zipcode;
        private String enterprise_id;
        private String lingyu;
        private String ent_logo;
        private String fax;
        private String homepage;
        private String stuff_munber;
        private String func;
        private String salary;
        private String year_salary;
        private String workplace;
        private String study;
        private String company_type;
        private String lingyu_name;
        private String industry;
        private String industry_name;
        private String release_date;
        private String expiration_date;
        private String language;
        private String nautica;
        private String applied_nums;
        private String age;
        private String favourite_time;
        private String applied_time;
        private String posterimg;
        private int is_favourite;
        private int is_expire;
        private int is_apply;
        private String baidu_map_lon;
        private String baidu_map_lat;

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

        public String getWork_area() {
            return work_area;
        }

        public void setWork_area(String work_area) {
            this.work_area = work_area;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
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

        public void setMonthly_pay_to(String monthly_pay_to) {
            this.monthly_pay_to = monthly_pay_to;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getPost_rank() {
            return post_rank;
        }

        public void setPost_rank(String post_rank) {
            this.post_rank = post_rank;
        }

        public String getWork_type() {
            return work_type;
        }

        public void setWork_type(String work_type) {
            this.work_type = work_type;
        }

        public String getInvite_major() {
            return invite_major;
        }

        public void setInvite_major(String invite_major) {
            this.invite_major = invite_major;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getIs_show_pay_interview() {
            return is_show_pay_interview;
        }

        public void setIs_show_pay_interview(String is_show_pay_interview) {
            this.is_show_pay_interview = is_show_pay_interview;
        }

        public String getParent_job_id() {
            return parent_job_id;
        }

        public void setParent_job_id(String parent_job_id) {
            this.parent_job_id = parent_job_id;
        }

        public String getPoster_state() {
            return poster_state;
        }

        public void setPoster_state(String poster_state) {
            this.poster_state = poster_state;
        }

        public String getIs_from_net() {
            return is_from_net;
        }

        public void setIs_from_net(String is_from_net) {
            this.is_from_net = is_from_net;
        }

        public String getTopjob_type() {
            return topjob_type;
        }

        public void setTopjob_type(String topjob_type) {
            this.topjob_type = topjob_type;
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

        public String getShow_language() {
            return show_language;
        }

        public void setShow_language(String show_language) {
            this.show_language = show_language;
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

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getLingyu() {
            return lingyu;
        }

        public void setLingyu(String lingyu) {
            this.lingyu = lingyu;
        }

        public String getEnt_logo() {
            return ent_logo;
        }

        public void setEnt_logo(String ent_logo) {
            this.ent_logo = ent_logo;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public String getStuff_munber() {
            return stuff_munber;
        }

        public void setStuff_munber(String stuff_munber) {
            this.stuff_munber = stuff_munber;
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

        public String getWorkplace() {
            return workplace;
        }

        public void setWorkplace(String workplace) {
            this.workplace = workplace;
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

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getNautica() {
            return nautica;
        }

        public void setNautica(String nautica) {
            this.nautica = nautica;
        }

        public String getApplied_nums() {
            return applied_nums;
        }

        public void setApplied_nums(String applied_nums) {
            this.applied_nums = applied_nums;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getFavourite_time() {
            return favourite_time;
        }

        public void setFavourite_time(String favourite_time) {
            this.favourite_time = favourite_time;
        }

        public String getApplied_time() {
            return applied_time;
        }

        public void setApplied_time(String applied_time) {
            this.applied_time = applied_time;
        }

        public String getPosterimg() {
            return posterimg;
        }

        public void setPosterimg(String posterimg) {
            this.posterimg = posterimg;
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

        public String getBaidu_map_lon() {
            return baidu_map_lon;
        }

        public void setBaidu_map_lon(String baidu_map_lon) {
            this.baidu_map_lon = baidu_map_lon;
        }

        public String getBaidu_map_lat() {
            return baidu_map_lat;
        }

        public void setBaidu_map_lat(String baidu_map_lat) {
            this.baidu_map_lat = baidu_map_lat;
        }

        @Override
        public String toString() {
            return "JobInfoBean{" +
                    "job_id='" + job_id + '\'' +
                    ", job_name='" + job_name + '\'' +
                    ", issue_date='" + issue_date + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", work_area='" + work_area + '\'' +
                    ", synopsis='" + synopsis + '\'' +
                    ", monthly_pay='" + monthly_pay + '\'' +
                    ", monthly_pay_to='" + monthly_pay_to + '\'' +
                    ", department='" + department + '\'' +
                    ", number='" + number + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", workyear='" + workyear + '\'' +
                    ", linkman='" + linkman + '\'' +
                    ", post_rank='" + post_rank + '\'' +
                    ", work_type='" + work_type + '\'' +
                    ", invite_major='" + invite_major + '\'' +
                    ", job_number='" + job_number + '\'' +
                    ", did='" + did + '\'' +
                    ", is_show_pay_interview='" + is_show_pay_interview + '\'' +
                    ", parent_job_id='" + parent_job_id + '\'' +
                    ", poster_state='" + poster_state + '\'' +
                    ", is_from_net='" + is_from_net + '\'' +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", subordinate_num='" + subordinate_num + '\'' +
                    ", superior='" + superior + '\'' +
                    ", other_benefits='" + other_benefits + '\'' +
                    ", recruit_students='" + recruit_students + '\'' +
                    ", job_slogan='" + job_slogan + '\'' +
                    ", show_language='" + show_language + '\'' +
                    ", address='" + address + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", lingyu='" + lingyu + '\'' +
                    ", ent_logo='" + ent_logo + '\'' +
                    ", fax='" + fax + '\'' +
                    ", homepage='" + homepage + '\'' +
                    ", stuff_munber='" + stuff_munber + '\'' +
                    ", func='" + func + '\'' +
                    ", salary='" + salary + '\'' +
                    ", year_salary='" + year_salary + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", study='" + study + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", lingyu_name='" + lingyu_name + '\'' +
                    ", industry='" + industry + '\'' +
                    ", industry_name='" + industry_name + '\'' +
                    ", release_date='" + release_date + '\'' +
                    ", expiration_date='" + expiration_date + '\'' +
                    ", language='" + language + '\'' +
                    ", nautica='" + nautica + '\'' +
                    ", applied_nums='" + applied_nums + '\'' +
                    ", age='" + age + '\'' +
                    ", favourite_time='" + favourite_time + '\'' +
                    ", applied_time='" + applied_time + '\'' +
                    ", posterimg='" + posterimg + '\'' +
                    ", is_favourite=" + is_favourite +
                    ", is_expire=" + is_expire +
                    ", is_apply=" + is_apply +
                    ", baidu_map_lon='" + baidu_map_lon + '\'' +
                    ", baidu_map_lat='" + baidu_map_lat + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "PositionBean{" +
                "error_code='" + error_code + '\'' +
                ", job_info=" + job_info +
                '}';
    }
}
