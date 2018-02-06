package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2018/1/17.
 */
@Entity
public class ScanHistoryBean {
    @Id
    public String jobId;
    private String jobName;
    private String place;
    private String exp;
    private String degree;
    private String companyName;
    private String salary;
    private String time;
    private String is_expect;
    @Generated(hash = 1449960708)
    public ScanHistoryBean(String jobId, String jobName, String place, String exp,
            String degree, String companyName, String salary, String time,
            String is_expect) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.place = place;
        this.exp = exp;
        this.degree = degree;
        this.companyName = companyName;
        this.salary = salary;
        this.time = time;
        this.is_expect = is_expect;
    }
    @Generated(hash = 1339095601)
    public ScanHistoryBean() {
    }
    public String getJobId() {
        return this.jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public String getJobName() {
        return this.jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getPlace() {
        return this.place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getExp() {
        return this.exp;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public String getDegree() {
        return this.degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getIs_expect() {
        return this.is_expect;
    }
    public void setIs_expect(String is_expect) {
        this.is_expect = is_expect;
    }
}
