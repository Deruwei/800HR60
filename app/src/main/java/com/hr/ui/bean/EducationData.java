package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/13.
 */
@Entity
public class EducationData {
    @Id
    private String schoolName;
    private String profession;
    private String degree;
    private String startTime;
    private String endTime;
    @Generated(hash = 1685699355)
    public EducationData(String schoolName, String profession, String degree,
            String startTime, String endTime) {
        this.schoolName = schoolName;
        this.profession = profession;
        this.degree = degree;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Generated(hash = 1912290112)
    public EducationData() {
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getProfession() {
        return this.profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getDegree() {
        return this.degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
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
}
