package com.hr.ui.bean;

import org.greenrobot.eventbus.EventBus;

public class EventBoolean {
    private int type;
    private boolean check;
    public EventBoolean(int type,boolean check){
        this.type=type;
        this.check=check;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
