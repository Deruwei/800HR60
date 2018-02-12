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
    private String jobStyle;
    private String address;
    private String salary;
    private String mode;
    @Generated(hash = 2076546083)
    public JobOrderData(String workType, String industry, String expectArea,
            String expectPosition, String jobStyle, String address, String salary,
            String mode) {
        this.workType = workType;
        this.industry = industry;
        this.expectArea = expectArea;
        this.expectPosition = expectPosition;
        this.jobStyle = jobStyle;
        this.address = address;
        this.salary = salary;
        this.mode = mode;
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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getJobStyle() {
        return this.jobStyle;
    }
    public void setJobStyle(String jobStyle) {
        this.jobStyle = jobStyle;
    }
    public String getMode() {
        return this.mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "JobOrderData{" +
                "workType='" + workType + '\'' +
                ", industry='" + industry + '\'' +
                ", expectArea='" + expectArea + '\'' +
                ", expectPosition='" + expectPosition + '\'' +
                ", jobStyle='" + jobStyle + '\'' +
                ", address='" + address + '\'' +
                ", salary='" + salary + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
