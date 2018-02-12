package com.hr.ui.bean;

import java.util.List;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeProfessionSkillBean {

    /**
     * error_code : 0
     * skill_list : [{"user_id":"9495365","usetime":"12","ability":"4","skilltitle":"fff","resume_id":"1","resume_language":"zh","skill_id":"3165029"}]
     * _run_time : 0.0056099891662598
     */

    private int error_code;
    private String _run_time;
    private List<SkillListBean> skill_list;

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

    public List<SkillListBean> getSkill_list() {
        return skill_list;
    }

    public void setSkill_list(List<SkillListBean> skill_list) {
        this.skill_list = skill_list;
    }

    public static class SkillListBean {
        /**
         * user_id : 9495365
         * usetime : 12
         * ability : 4
         * skilltitle : fff
         * resume_id : 1
         * resume_language : zh
         * skill_id : 3165029
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

    @Override
    public String toString() {
        return "ResumeProfessionSkillBean{" +
                "error_code=" + error_code +
                ", _run_time='" + _run_time + '\'' +
                ", skill_list=" + skill_list +
                '}';
    }
}
