package com.hr.ui.bean;

import java.io.Serializable;

/**
 * Created by wdr on 2017/12/14.
 */

public class CityBean implements Serializable {
    private String id;
    private String name;
    private boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
