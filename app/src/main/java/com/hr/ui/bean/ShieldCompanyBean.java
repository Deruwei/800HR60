package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/17.
 */

public class ShieldCompanyBean {

    /**
     * error_code : 0
     * eliminate_list : [{"id":"541062","user_id":"9505708","eliminate_id":"135888","edit_time":"1516156328","eliminate_txt":"北京天成健医药科技有限公司","enterprise_ids":"558972","free_enterprise_ids":"","update_time":"1516156328"},{"id":"541063","user_id":"9505708","eliminate_id":"135889","edit_time":"1516156337","eliminate_txt":"北京百汇鹰阁医药技术咨询有限公司","enterprise_ids":"559892","free_enterprise_ids":"","update_time":"1516156337"},{"id":"541064","user_id":"9505708","eliminate_id":"25639","edit_time":"1516156368","eliminate_txt":"上海中医药大学附属曙光医院","enterprise_ids":"557862","free_enterprise_ids":"","update_time":"0"},{"id":"541065","user_id":"9505708","eliminate_id":"135890","edit_time":"1516156401","eliminate_txt":"北京纵横寰宇中医药网络技术有限公司","enterprise_ids":"3103","free_enterprise_ids":"","update_time":"1516156401"},{"id":"541066","user_id":"9505708","eliminate_id":"135891","edit_time":"1516156404","eliminate_txt":"北京维康力医药科技有限公司","enterprise_ids":"32213","free_enterprise_ids":"","update_time":"1516156404"}]
     * _run_time : 0.0045039653778076
     */

    private int error_code;
    private String _run_time;
    private List<EliminateListBean> eliminate_list;

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

    public List<EliminateListBean> getEliminate_list() {
        return eliminate_list;
    }

    public void setEliminate_list(List<EliminateListBean> eliminate_list) {
        this.eliminate_list = eliminate_list;
    }

    public static class EliminateListBean {
        /**
         * id : 541062
         * user_id : 9505708
         * eliminate_id : 135888
         * edit_time : 1516156328
         * eliminate_txt : 北京天成健医药科技有限公司
         * enterprise_ids : 558972
         * free_enterprise_ids :
         * update_time : 1516156328
         */

        private String id;
        private String user_id;
        private String eliminate_id;
        private String edit_time;
        private String eliminate_txt;
        private String enterprise_ids;
        private String free_enterprise_ids;
        private String update_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getEliminate_id() {
            return eliminate_id;
        }

        public void setEliminate_id(String eliminate_id) {
            this.eliminate_id = eliminate_id;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }

        public String getEliminate_txt() {
            return eliminate_txt;
        }

        public void setEliminate_txt(String eliminate_txt) {
            this.eliminate_txt = eliminate_txt;
        }

        public String getEnterprise_ids() {
            return enterprise_ids;
        }

        public void setEnterprise_ids(String enterprise_ids) {
            this.enterprise_ids = enterprise_ids;
        }

        public String getFree_enterprise_ids() {
            return free_enterprise_ids;
        }

        public void setFree_enterprise_ids(String free_enterprise_ids) {
            this.free_enterprise_ids = free_enterprise_ids;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
