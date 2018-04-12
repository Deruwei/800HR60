package com.hr.ui.bean;

public class EventBean {
    private String tag;
    private CityBean cityBean;
    public EventBean(){

    }
    public EventBean(CityBean cityBean){

    }
    public EventBean(String tag,CityBean cityBean){
        this.tag=tag;
        this.cityBean=cityBean;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CityBean getCityBean() {
        return cityBean;
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }
}
