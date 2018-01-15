package com.hr.ui.bean;


import java.io.Serializable;

/**
 * Created by wdr on 2018/1/9.
 */
public class JobSearchBean  implements Serializable {
    private int jobType;
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

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getWorkExp() {
        return workExp;
    }

    public void setWorkExp(String workExp) {
        this.workExp = workExp;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getJobTime() {
        return JobTime;
    }

    public void setJobTime(String jobTime) {
        JobTime = jobTime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }

    public String getSalary_left() {
        return salary_left;
    }

    public void setSalary_left(String salary_left) {
        this.salary_left = salary_left;
    }

    public String getSalary_right() {
        return salary_right;
    }

    public void setSalary_right(String salary_right) {
        this.salary_right = salary_right;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Override
    public String toString() {
        return "JobSearchBean{" +
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
                '}';
    }
}
