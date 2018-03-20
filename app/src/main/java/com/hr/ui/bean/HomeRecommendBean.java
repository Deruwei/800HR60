package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/2/2.
 */

public class HomeRecommendBean {

    /**
     * error_code : 0
     * jobs_list : [{"job_id":"ZTSoV","job_name":"工程管理 （施工员/测量员/项目总工）","issue_date":"1513759171","enterprise_name":"山东中宏路桥建设有限公司","work_area":"2107","synopsis":"1.岗位要求：（1）土木工程、道路与桥梁相关专业，大专以上学历；（2）5年以上施工企业技术岗位工作经历，3年以上路桥施工管理工作经验；（3）有中级以上职称者优先。<br />2.岗位职责：（1）分公司下辖工地检查、指导工程现场技术管理；（2）审核项目施组、施工方案。<br />","monthly_pay":"2000","monthly_pay_to":"2999","department":"山东中宏路桥建设有限公司","number":"10人","email":"","workyear":"三年以上","work_type":"全职","job_number":"","is_show_pay_interview":"1","poster_state":"0","topjob_type":"1","subordinate_num":"0","superior":"","other_benefits":"","recruit_students":"0","job_slogan":"","show_language":"","address":"9107329@address","enterprise_id":"","ent_logo":"","func":"249105,241118,241101,241138,241102","salary":"面议","year_salary":"面议","workplace":"山东-烟台市","study":"不限","lingyu_name":"市政路桥","industry":"11","industry_name":"建筑","release_date":"1499184000","expiration_date":"1519919999","nautica":"","applied_nums":"43","age":"","applied_time":"0","favourite_time":"0","recom_time":"1517541162","match_value":"0.40539521","view_time":"0","satisfying":"1","satisfy_time":"0"}]
     * _run_time : 0.0036349296569824
     */

    private int error_code;
    private String _run_time;
    private List<JobsListBean> jobs_list;

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

    public List<JobsListBean> getJobs_list() {
        return jobs_list;
    }

    public void setJobs_list(List<JobsListBean> jobs_list) {
        this.jobs_list = jobs_list;
    }

    public static class JobsListBean {
        /**
         * job_id : ZTSoV
         * job_name : 工程管理 （施工员/测量员/项目总工）
         * issue_date : 1513759171
         * enterprise_name : 山东中宏路桥建设有限公司
         * work_area : 2107
         * synopsis : 1.岗位要求：（1）土木工程、道路与桥梁相关专业，大专以上学历；（2）5年以上施工企业技术岗位工作经历，3年以上路桥施工管理工作经验；（3）有中级以上职称者优先。<br />2.岗位职责：（1）分公司下辖工地检查、指导工程现场技术管理；（2）审核项目施组、施工方案。<br />
         * monthly_pay : 2000
         * monthly_pay_to : 2999
         * department : 山东中宏路桥建设有限公司
         * number : 10人
         * email :
         * workyear : 三年以上
         * work_type : 全职
         * job_number :
         * is_show_pay_interview : 1
         * poster_state : 0
         * topjob_type : 1
         * subordinate_num : 0
         * superior :
         * other_benefits :
         * recruit_students : 0
         * job_slogan :
         * show_language :
         * address : 9107329@address
         * enterprise_id :
         * ent_logo :
         * func : 249105,241118,241101,241138,241102
         * salary : 面议
         * year_salary : 面议
         * workplace : 山东-烟台市
         * study : 不限
         * lingyu_name : 市政路桥
         * industry : 11
         * industry_name : 建筑
         * release_date : 1499184000
         * expiration_date : 1519919999
         * nautica :
         * applied_nums : 43
         * age :
         * applied_time : 0
         * favourite_time : 0
         * recom_time : 1517541162
         * match_value : 0.40539521
         * view_time : 0
         * satisfying : 1
         * satisfy_time : 0
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
        private String workyear;
        private String work_type;
        private String job_number;
        private String company_type;
        private String is_show_pay_interview;
        private String poster_state;
        private String topjob_type;
        private String subordinate_num;
        private String superior;
        private String other_benefits;
        private String recruit_students;
        private String job_slogan;
        private String show_language;
        private String address;
        private String enterprise_id;
        private String ent_logo;
        private String func;
        private String salary;
        private String year_salary;
        private String workplace;
        private String study;
        private String lingyu_name;
        private String industry;
        private String industry_name;
        private String release_date;
        private String expiration_date;
        private String nautica;
        private String applied_nums;
        private String age;
        private String applied_time;
        private String favourite_time;
        private String recom_time;
        private String match_value;
        private String view_time;
        private String satisfying;
        private String satisfy_time;
        private int is_favourite;
        private int is_expire;
        private int is_apply;
        private int is_shoufa;

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

        public int getIs_shoufa() {
            return is_shoufa;
        }

        public void setIs_shoufa(int is_shoufa) {
            this.is_shoufa = is_shoufa;
        }

        public String getCompany_type() {
            return company_type;
        }

        public void setCompany_type(String company_type) {
            this.company_type = company_type;
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

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getWork_type() {
            return work_type;
        }

        public void setWork_type(String work_type) {
            this.work_type = work_type;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
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

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getEnt_logo() {
            return ent_logo;
        }

        public void setEnt_logo(String ent_logo) {
            this.ent_logo = ent_logo;
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

        public String getRecom_time() {
            return recom_time;
        }

        public void setRecom_time(String recom_time) {
            this.recom_time = recom_time;
        }

        public String getMatch_value() {
            return match_value;
        }

        public void setMatch_value(String match_value) {
            this.match_value = match_value;
        }

        public String getView_time() {
            return view_time;
        }

        public void setView_time(String view_time) {
            this.view_time = view_time;
        }

        public String getSatisfying() {
            return satisfying;
        }

        public void setSatisfying(String satisfying) {
            this.satisfying = satisfying;
        }

        public String getSatisfy_time() {
            return satisfy_time;
        }

        public void setSatisfy_time(String satisfy_time) {
            this.satisfy_time = satisfy_time;
        }

        @Override
        public String toString() {
            return "JobsListBean{" +
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
                    ", workyear='" + workyear + '\'' +
                    ", work_type='" + work_type + '\'' +
                    ", job_number='" + job_number + '\'' +
                    ", company_type='" + company_type + '\'' +
                    ", is_show_pay_interview='" + is_show_pay_interview + '\'' +
                    ", poster_state='" + poster_state + '\'' +
                    ", topjob_type='" + topjob_type + '\'' +
                    ", subordinate_num='" + subordinate_num + '\'' +
                    ", superior='" + superior + '\'' +
                    ", other_benefits='" + other_benefits + '\'' +
                    ", recruit_students='" + recruit_students + '\'' +
                    ", job_slogan='" + job_slogan + '\'' +
                    ", show_language='" + show_language + '\'' +
                    ", address='" + address + '\'' +
                    ", enterprise_id='" + enterprise_id + '\'' +
                    ", ent_logo='" + ent_logo + '\'' +
                    ", func='" + func + '\'' +
                    ", salary='" + salary + '\'' +
                    ", year_salary='" + year_salary + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", study='" + study + '\'' +
                    ", lingyu_name='" + lingyu_name + '\'' +
                    ", industry='" + industry + '\'' +
                    ", industry_name='" + industry_name + '\'' +
                    ", release_date='" + release_date + '\'' +
                    ", expiration_date='" + expiration_date + '\'' +
                    ", nautica='" + nautica + '\'' +
                    ", applied_nums='" + applied_nums + '\'' +
                    ", age='" + age + '\'' +
                    ", applied_time='" + applied_time + '\'' +
                    ", favourite_time='" + favourite_time + '\'' +
                    ", recom_time='" + recom_time + '\'' +
                    ", match_value='" + match_value + '\'' +
                    ", view_time='" + view_time + '\'' +
                    ", satisfying='" + satisfying + '\'' +
                    ", satisfy_time='" + satisfy_time + '\'' +
                    ", is_favourite=" + is_favourite +
                    ", is_expire=" + is_expire +
                    ", is_apply=" + is_apply +
                    ", is_shoufa=" + is_shoufa +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeRecommendBean{" +
                "error_code=" + error_code +
                ", _run_time='" + _run_time + '\'' +
                ", jobs_list=" + jobs_list +
                '}';
    }
}
