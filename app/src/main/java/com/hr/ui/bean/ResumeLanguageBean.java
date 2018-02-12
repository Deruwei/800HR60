package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeLanguageBean {

    /**
     * error_code : 0
     * language_list : [{"langname":"12","user_id":"9495365","read_level":"3","speak_level":"3","grade_exam":[]}]
     * _run_time : 0.0036740303039551
     */

    private int error_code;
    private String _run_time;
    private List<LanguageListBean> language_list;

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

    public List<LanguageListBean> getLanguage_list() {
        return language_list;
    }

    public void setLanguage_list(List<LanguageListBean> language_list) {
        this.language_list = language_list;
    }

    public static class LanguageListBean {
        /**
         * langname : 12
         * user_id : 9495365
         * read_level : 3
         * speak_level : 3
         * grade_exam : []
         */

        private String langname;
        private String user_id;
        private String read_level;
        private String speak_level;
        private List<?> grade_exam;

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

        public List<?> getGrade_exam() {
            return grade_exam;
        }

        public void setGrade_exam(List<?> grade_exam) {
            this.grade_exam = grade_exam;
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

    @Override
    public String toString() {
        return "ResumeLanguageBean{" +
                "error_code=" + error_code +
                ", _run_time='" + _run_time + '\'' +
                ", language_list=" + language_list +
                '}';
    }
}
