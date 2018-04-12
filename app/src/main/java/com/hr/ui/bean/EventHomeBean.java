package com.hr.ui.bean;

import org.greenrobot.eventbus.EventBus;

public class EventHomeBean {
    private int type;  //0.代表主页职位投递状态更新   1.代表更换简历头像 其他页面头像图标改变  2.消息页投递反馈已读状态改变  3.消息页中邀请面试已读状态改变
    private int position;
    public EventHomeBean(){}
    public EventHomeBean(int type,int position){
        this.type=type;
        this.position=position;
    }
    public EventHomeBean(int type){
        this.type=type;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
