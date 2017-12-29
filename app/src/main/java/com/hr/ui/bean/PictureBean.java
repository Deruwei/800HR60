package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/26.
 */

public class PictureBean {
    private double error_code;
    private String pic_filekey;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public String getPic_filekey() {
        return pic_filekey;
    }

    public void setPic_filekey(String pic_filekey) {
        this.pic_filekey = pic_filekey;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "error_code=" + error_code +
                ", pic_filekey='" + pic_filekey + '\'' +
                '}';
    }
}
