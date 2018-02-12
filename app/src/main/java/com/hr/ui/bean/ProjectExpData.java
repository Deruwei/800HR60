package com.hr.ui.bean;

/**
 * Created by wdr on 2017/12/29.
 */

public class ProjectExpData {
    private String projectId;
    private String projectName;
    private String projectPosition;
    private String startTime;
    private String endTime;
    private String projectDes;
    private String projectResponsibility;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(String projectPosition) {
        this.projectPosition = projectPosition;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProjectDes() {
        return projectDes;
    }

    public void setProjectDes(String projectDes) {
        this.projectDes = projectDes;
    }

    public String getProjectResponsibility() {
        return projectResponsibility;
    }

    public void setProjectResponsibility(String projectResponsibility) {
        this.projectResponsibility = projectResponsibility;
    }

    @Override
    public String toString() {
        return "ProjectExpData{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectPosition='" + projectPosition + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", projectDes='" + projectDes + '\'' +
                ", projectResponsibility='" + projectResponsibility + '\'' +
                '}';
    }
}
