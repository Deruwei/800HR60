package com.hr.ui.bean;

public class EventString {
    private String msg;
    private String tag;
    private String type;
    public EventString(){}
    public EventString(String msg){
        this.msg=msg;
    }
    public EventString(String msg,String tag){
        this.msg=msg;
        this.tag=tag;
    }
    public EventString(String msg,String tag,String type){
        this.msg=msg;
        this.tag=tag;
        this.type=type;
    }
    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
