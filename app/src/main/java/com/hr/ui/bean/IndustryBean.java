package com.hr.ui.bean;

/**
 * Created by wdr on 2017/11/29.
 */

public class IndustryBean {
    private int id;
    private int industryName;
    private int industryUrlId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndustryName() {
        return industryName;
    }

    public void setIndustryName(int industryName) {
        this.industryName = industryName;
    }

    public int getIndustryUrlId() {
        return industryUrlId;
    }

    public void setIndustryUrlId(int industryUrlId) {
        this.industryUrlId = industryUrlId;
    }

    @Override
    public String toString() {
        return "IndustryBean{" +
                "id=" + id +
                ", industryName=" + industryName +
                ", industryUrlId=" + industryUrlId +
                '}';
    }
}
