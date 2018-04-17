package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2018/3/15.
 */
@Entity
public class SearchHistoryBean {
    @Id
    private String searchName;
    private int jobType;
    private String placeId;
    private String addDate;
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
    @Generated(hash = 1437532221)
    public SearchHistoryBean(String searchName, int jobType, String placeId,
            String addDate, String industryId, String fieldId, String workExp,
            String workType, String JobTime, String degree, String companyScale,
            String salary_left, String salary_right, String positionId,
            String companyType) {
        this.searchName = searchName;
        this.jobType = jobType;
        this.placeId = placeId;
        this.addDate = addDate;
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
    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }
    public String getSearchName() {
        return this.searchName;
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    public int getJobType() {
        return this.jobType;
    }
    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
    public String getPlaceId() {
        return this.placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getAddDate() {
        return this.addDate;
    }
    public void setAddDate(String addDate) {
        this.addDate = addDate;
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
}
