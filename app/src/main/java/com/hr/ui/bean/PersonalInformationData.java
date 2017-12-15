package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/13.
 */
@Entity
public class PersonalInformationData {
    @Id
    private String name;
    private String sex;
    private String birth;
    private String livePlace;
    private String workTime;
    private String phoneNumber;
    private String Email;
    private String positionTitle;
    @Generated(hash = 152496905)
    public PersonalInformationData(String name, String sex, String birth,
            String livePlace, String workTime, String phoneNumber, String Email,
            String positionTitle) {
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.livePlace = livePlace;
        this.workTime = workTime;
        this.phoneNumber = phoneNumber;
        this.Email = Email;
        this.positionTitle = positionTitle;
    }
    @Generated(hash = 1113911961)
    public PersonalInformationData() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirth() {
        return this.birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getLivePlace() {
        return this.livePlace;
    }
    public void setLivePlace(String livePlace) {
        this.livePlace = livePlace;
    }
    public String getWorkTime() {
        return this.workTime;
    }
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    public String getEmail() {
        return this.Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getPositionTitle() {
        return this.positionTitle;
    }
    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
