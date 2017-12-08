package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by wdr on 2017/12/7.
 */
@Entity
public class ThirdPartBean {
    @Id
    private String type;
    private String name;
    private String nikeName;
    private String uId;
    private String birthDay;
    private String photo;
    private String gender;
    private String sUId;
    public String getSUId() {
        return this.sUId;
    }
    public void setSUId(String sUId) {
        this.sUId = sUId;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getBirthDay() {
        return this.birthDay;
    }
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    public String getUId() {
        return this.uId;
    }
    public void setUId(String uId) {
        this.uId = uId;
    }
    public String getNikeName() {
        return this.nikeName;
    }
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Generated(hash = 930487056)
    public ThirdPartBean(String type, String name, String nikeName, String uId,
            String birthDay, String photo, String gender, String sUId) {
        this.type = type;
        this.name = name;
        this.nikeName = nikeName;
        this.uId = uId;
        this.birthDay = birthDay;
        this.photo = photo;
        this.gender = gender;
        this.sUId = sUId;
    }
    @Generated(hash = 1980064110)
    public ThirdPartBean() {
    }
}
