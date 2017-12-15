package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/14.
 */

public class ArrayInfoBean {

    /**
     * city : {"ver":"1.12","lasttime":"2017-11-30 18:40:00"}
     * job : {"ver":"1.52","lasttime":"2017-12-08 18:15:00"}
     * lingyu : {"ver":"1.6","lasttime":"2017-05-27 08:30:00"}
     */

    private CityBean city;
    private JobBean job;
    private LingyuBean lingyu;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public JobBean getJob() {
        return job;
    }

    public void setJob(JobBean job) {
        this.job = job;
    }

    public LingyuBean getLingyu() {
        return lingyu;
    }

    public void setLingyu(LingyuBean lingyu) {
        this.lingyu = lingyu;
    }

    public static class CityBean {
        /**
         * ver : 1.12
         * lasttime : 2017-11-30 18:40:00
         */

        private String ver;
        private String lasttime;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }
    }

    public static class JobBean {
        /**
         * ver : 1.52
         * lasttime : 2017-12-08 18:15:00
         */

        private String ver;
        private String lasttime;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }
    }

    public static class LingyuBean {
        /**
         * ver : 1.6
         * lasttime : 2017-05-27 08:30:00
         */

        private String ver;
        private String lasttime;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }
    }
}
