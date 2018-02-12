package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2018/1/9.
 */
@Entity
public class HistoryBean {
    private int jobType;
    @Id
    private String searchName;
    private String placeId;
    private String industryId;
    private String fieldId;
    private String workExp;
    private String workType;
    private String JobTime;
    private String degree;
    private String companyScale;
    private String salary_left;
    private String salary_right;
    private String positionId;
    private String companyType;
    @Generated(hash = 1803852463)
    public HistoryBean(int jobType, String searchName, String placeId,
            String industryId, String fieldId, String workExp, String workType,
            String JobTime, String degree, String companyScale, String salary_left,
            String salary_right, String positionId, String companyType) {
        this.jobType = jobType;
        this.searchName = searchName;
        this.placeId = placeId;
        this.industryId = industryId;
        this.fieldId = fieldId;
        this.workExp = workExp;
        this.workType = workType;
        this.JobTime = JobTime;
        this.degree = degree;
        this.companyScale = companyScale;
        this.salary_left = salary_left;
        this.salary_right = salary_right;
        this.positionId = positionId;
        this.companyType = companyType;
    }
    @Generated(hash = 48590348)
    public HistoryBean() {
    }
    public int getJobType() {
        return this.jobType;
    }
    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
    public String getSearchName() {
        return this.searchName;
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    public String getPlaceId() {
        return this.placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getIndustryId() {
        return this.industryId;
    }
    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }
    public String getFieldId() {
        return this.fieldId;
    }
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
    public String getWorkExp() {
        return this.workExp;
    }
    public void setWorkExp(String workExp) {
        this.workExp = workExp;
    }
    public String getWorkType() {
        return this.workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public String getJobTime() {
        return this.JobTime;
    }
    public void setJobTime(String JobTime) {
        this.JobTime = JobTime;
    }
    public String getDegree() {
        return this.degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getCompanyScale() {
        return this.companyScale;
    }
    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }
    public String getSalary_left() {
        return this.salary_left;
    }
    public void setSalary_left(String salary_left) {
        this.salary_left = salary_left;
    }
    public String getSalary_right() {
        return this.salary_right;
    }
    public void setSalary_right(String salary_right) {
        this.salary_right = salary_right;
    }
    public String getPositionId() {
        return this.positionId;
    }
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    public String getCompanyType() {
        return this.companyType;
    }
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "jobType=" + jobType +
                ", searchName='" + searchName + '\'' +
                ", placeId='" + placeId + '\'' +
                ", industryId='" + industryId + '\'' +
                ", fieldId='" + fieldId + '\'' +
                ", workExp='" + workExp + '\'' +
                ", workType='" + workType + '\'' +
                ", JobTime='" + JobTime + '\'' +
                ", degree='" + degree + '\'' +
                ", companyScale='" + companyScale + '\'' +
                ", salary_left='" + salary_left + '\'' +
                ", salary_right='" + salary_right + '\'' +
                ", positionId='" + positionId + '\'' +
                ", companyType='" + companyType + '\'' +
                '}';
    }
}
