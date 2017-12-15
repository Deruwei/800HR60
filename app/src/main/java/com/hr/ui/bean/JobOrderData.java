package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/13.
 */
@Entity
public class JobOrderData {
    @Id
    public String workType;
    private String industry;
    private String expectArea;
    private String expectPosition;
    private String salary;
    @Generated(hash = 91804590)
    public JobOrderData(String workType, String industry, String expectArea,
            String expectPosition, String salary) {
        this.workType = workType;
        this.industry = industry;
        this.expectArea = expectArea;
        this.expectPosition = expectPosition;
        this.salary = salary;
    }
    @Generated(hash = 1495632613)
    public JobOrderData() {
    }
    public String getWorkType() {
        return this.workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public String getIndustry() {
        return this.industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getExpectArea() {
        return this.expectArea;
    }
    public void setExpectArea(String expectArea) {
        this.expectArea = expectArea;
    }
    public String getExpectPosition() {
        return this.expectPosition;
    }
    public void setExpectPosition(String expectPosition) {
        this.expectPosition = expectPosition;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
}