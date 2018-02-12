package com.hr.ui.bean;

/**
 * Created by wdr on 2018/2/8.
 */

public class NoticeData {
    private String rushjob_state;
    private String invite_state;
    private String sound_state;
    private String notice_bgntime;
    private String notice_endtime;
    private String phonecode;
    private String baidu_user_id;
    private String baidu_channel_id;
    private String push_way;

    public String getRushjob_state() {
        return rushjob_state;
    }

    public void setRushjob_state(String rushjob_state) {
        this.rushjob_state = rushjob_state;
    }

    public String getInvite_state() {
        return invite_state;
    }

    public void setInvite_state(String invite_state) {
        this.invite_state = invite_state;
    }

    public String getSound_state() {
        return sound_state;
    }

    public void setSound_state(String sound_state) {
        this.sound_state = sound_state;
    }

    public String getNotice_bgntime() {
        return notice_bgntime;
    }

    public void setNotice_bgntime(String notice_bgntime) {
        this.notice_bgntime = notice_bgntime;
    }

    public String getNotice_endtime() {
        return notice_endtime;
    }

    public void setNotice_endtime(String notice_endtime) {
        this.notice_endtime = notice_endtime;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getBaidu_user_id() {
        return baidu_user_id;
    }

    public void setBaidu_user_id(String baidu_user_id) {
        this.baidu_user_id = baidu_user_id;
    }

    public String getBaidu_channel_id() {
        return baidu_channel_id;
    }

    public void setBaidu_channel_id(String baidu_channel_id) {
        this.baidu_channel_id = baidu_channel_id;
    }

    public String getPush_way() {
        return push_way;
    }

    public void setPush_way(String push_way) {
        this.push_way = push_way;
    }

    @Override
    public String toString() {
        return "NoticeData{" +
                "rushjob_state='" + rushjob_state + '\'' +
                ", invite_state='" + invite_state + '\'' +
                ", sound_state='" + sound_state + '\'' +
                ", notice_bgntime='" + notice_bgntime + '\'' +
                ", notice_endtime='" + notice_endtime + '\'' +
                ", phonecode='" + phonecode + '\'' +
                ", baidu_user_id='" + baidu_user_id + '\'' +
                ", baidu_channel_id='" + baidu_channel_id + '\'' +
                ", push_way='" + push_way + '\'' +
                '}';
    }
}
