package com.hr.ui.bean;

public class CheckBean {
    public int num;
    public boolean isCheck;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "CheckBean{" +
                "num=" + num +
                ", isCheck=" + isCheck +
                '}';
    }
}
