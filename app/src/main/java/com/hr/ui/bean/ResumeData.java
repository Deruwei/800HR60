package com.hr.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wdr on 2017/12/11.
 */
@Entity
public class ResumeData {
    @Id
    private String resumeId;
    private String title;
    private String complete;
    private int imageId;


    @Generated(hash = 1238736963)
    public ResumeData(String resumeId, String title, String complete, int imageId) {
        this.resumeId = resumeId;
        this.title = title;
        this.complete = complete;
        this.imageId = imageId;
    }


    @Generated(hash = 1068625934)
    public ResumeData() {
    }


    public String getResumeId() {
        return this.resumeId;
    }


    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getComplete() {
        return this.complete;
    }


    public void setComplete(String complete) {
        this.complete = complete;
    }


    public int getImageId() {
        return this.imageId;
    }


    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ResumeData{" +
                "resumeId='" + resumeId + '\'' +
                ", title='" + title + '\'' +
                ", complete='" + complete + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
