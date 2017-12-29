package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/13.
 */
@Entity
public class WorkExpData {
    @Id
    private String company;
    private String experienceId;
    private String position;
    private String workPlace;
    private String grossPay;
    private String startTime;
    private String endTime;
    private String responsibilityDescription;
    @Generated(hash = 772593882)
    public WorkExpData(String company, String experienceId, String position,
            String workPlace, String grossPay, String startTime, String endTime,
            String responsibilityDescription) {
        this.company = company;
        this.experienceId = experienceId;
        this.position = position;
        this.workPlace = workPlace;
        this.grossPay = grossPay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.responsibilityDescription = responsibilityDescription;
    }
    @Generated(hash = 1924154366)
    public WorkExpData() {
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getWorkPlace() {
        return this.workPlace;
    }
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
    public String getGrossPay() {
        return this.grossPay;
    }
    public void setGrossPay(String grossPay) {
        this.grossPay = grossPay;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getResponsibilityDescription() {
        return this.responsibilityDescription;
    }
    public void setResponsibilityDescription(String responsibilityDescription) {
        this.responsibilityDescription = responsibilityDescription;
    }
    public String getExperienceId() {
        return this.experienceId;
    }
    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }
}
