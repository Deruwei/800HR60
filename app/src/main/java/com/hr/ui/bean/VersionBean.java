package com.hr.ui.bean;

/**
 * Created by wdr on 2018/2/8.
 */

public class VersionBean {

    /**
     * android : {"ver":"5.4.3","time":"2017-07-26","text":"更新内容：\n1、修复简历编辑时间显示bug。\n2、解决已知其他问题，全面优化。","url":"http://www.800hr.com/app/"}
     */

    private AndroidBean android;

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public static class AndroidBean {
        /**
         * ver : 5.4.3
         * time : 2017-07-26
         * text : 更新内容：
         1、修复简历编辑时间显示bug。
         2、解决已知其他问题，全面优化。
         * url : http://www.800hr.com/app/
         */

        private String ver;
        private String time;
        private String text;
        private String url;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
