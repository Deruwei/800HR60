package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2017/12/27.
 */

public class ResumeOrderInfoBean {

    /**
     * error_code : 0
     * order_info : [{"user_id":"9327227","jobtype":"13","industry":"11","func":"255102,255109,396104","workarea":"1100,1200,1300","zhixi":"","order_salary":"40000","order_salary_noshow":"0","resume_id":"1","resume_language":"zh","jobname":"","lingyu":"111100,111200","order_yearsalary":"0","order_industry":[{"industry":"11","func":"255102,255109,396104","lingyu":"111100,111200"}],"current_workstate":"11"}]
     */

    private int error_code;
    private List<OrderInfoBean> order_info;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<OrderInfoBean> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(List<OrderInfoBean> order_info) {
        this.order_info = order_info;
    }

    public static class OrderInfoBean {
        /**
         * user_id : 9327227
         * jobtype : 13
         * industry : 11
         * func : 255102,255109,396104
         * workarea : 1100,1200,1300
         * zhixi :
         * order_salary : 40000
         * order_salary_noshow : 0
         * resume_id : 1
         * resume_language : zh
         * jobname :
         * lingyu : 111100,111200
         * order_yearsalary : 0
         * order_industry : [{"industry":"11","func":"255102,255109,396104","lingyu":"111100,111200"}]
         * current_workstate : 11
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

        public List<OrderIndustryBean> getOrder_industry() {
            return order_industry;
        }

        public void setOrder_industry(List<OrderIndustryBean> order_industry) {
            this.order_industry = order_industry;
        }

        public static class OrderIndustryBean {
            /**
             * industry : 11
             * func : 255102,255109,396104
             * lingyu : 111100,111200
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
                    ", order_industry=" + order_industry +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResumeOrderInfoBean{" +
                "error_code=" + error_code +
                ", order_info=" + order_info +
                '}';
    }
}
