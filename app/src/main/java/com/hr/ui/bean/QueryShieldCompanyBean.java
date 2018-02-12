package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/17.
 */

public class QueryShieldCompanyBean {

    /**
     * error_code : 0
     * ente_list : [{"enterprise_id":"E5X","enterprise_name":"北京纵横寰宇中医药网络技术有限公司","industry":"23"},{"enterprise_id":"w68A","enterprise_name":"北京维康力医药科技有限公司","industry":"23"},{"enterprise_id":"fXZX","enterprise_name":"北京康派特医药科技开发有限公司","industry":"14"},{"enterprise_id":"zWPrG","enterprise_name":"上海中医药大学附属曙光医院","industry":"14"},{"enterprise_id":"zWkrO","enterprise_name":"北京天成健医药科技有限公司","industry":"14"},{"enterprise_id":"zWJrM","enterprise_name":"北京百汇鹰阁医药技术咨询有限公司","industry":"14"},{"enterprise_id":"zKrrL","enterprise_name":"海虹企业（控股）股份有限公司医药电子商务事业部","industry":"23"},{"enterprise_id":"zKkrp","enterprise_name":"北京市医药器械学校","industry":"15"},{"enterprise_id":"zK4rf","enterprise_name":"北京费森尤斯卡比医药有限公司","industry":"22"},{"enterprise_id":"zKirj","enterprise_name":"北京费森尤斯卡比医药有限公司","industry":"14"},{"enterprise_id":"z6mrW","enterprise_name":"北京希而欧生物医药开发有限公司","industry":"23"},{"enterprise_id":"zkbrm","enterprise_name":"北京科翔柏雅医药科技有限公司","industry":"14"},{"enterprise_id":"zklrT","enterprise_name":"陕西省中医药研究院汉唐制药有限公司","industry":"14"},{"enterprise_id":"zkyrT","enterprise_name":"北京奥达康医药科技有限责任公司","industry":"14"},{"enterprise_id":"zVFrD","enterprise_name":"广东京豪医药科技开发有限公司","industry":"14"},{"enterprise_id":"zVLr2","enterprise_name":"南京医药公司","industry":"16"},{"enterprise_id":"zp0rA","enterprise_name":"天津(北京)山佳医药科技有限公司","industry":"14"},{"enterprise_id":"zb3r8","enterprise_name":"《中国医药指南》杂志","industry":"13"},{"enterprise_id":"zmnrW","enterprise_name":"中国永裕新兴医药有限公司","industry":"16"},{"enterprise_id":"zMAr4","enterprise_name":"北京银象医药集团有限公司","industry":"14"}]
     * totals : 1000
     * _run_time : 0.025816202163696
     */

    private int error_code;
    private String totals;
    private String _run_time;
    private List<EnteListBean> ente_list;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public String get_run_time() {
        return _run_time;
    }

    public void set_run_time(String _run_time) {
        this._run_time = _run_time;
    }

    public List<EnteListBean> getEnte_list() {
        return ente_list;
    }

    public void setEnte_list(List<EnteListBean> ente_list) {
        this.ente_list = ente_list;
    }

    public static class EnteListBean {
        /**
         * enterprise_id : E5X
         * enterprise_name : 北京纵横寰宇中医药网络技术有限公司
         * industry : 23
         */

        private String enterprise_id;
        private String enterprise_name;
        private String industry;

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

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        @Override
        public String toString() {
            return "EnteListBean{" +
                    "enterprise_id='" + enterprise_id + '\'' +
                    ", enterprise_name='" + enterprise_name + '\'' +
                    ", industry='" + industry + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QueryShieldCompanyBean{" +
                "error_code=" + error_code +
                ", totals='" + totals + '\'' +
                ", _run_time='" + _run_time + '\'' +
                ", ente_list=" + ente_list +
                '}';
    }
}
