package com.hr.ui.bean;

/**
 * Created by wdr on 2018/1/2.
 */

public class TrainExpData {
    private String trainId;
    private String trainInstruction;
    private String trainClass;
    private String startTime;
    private String endTime;
    private String trainDes;

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainInstruction() {
        return trainInstruction;
    }

    public void setTrainInstruction(String trainInstruction) {
        this.trainInstruction = trainInstruction;
    }

    public String getTrainClass() {
        return trainClass;
    }

    public void setTrainClass(String trainClass) {
        this.trainClass = trainClass;
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

    public String getTrainDes() {
        return trainDes;
    }

    public void setTrainDes(String trainDes) {
        this.trainDes = trainDes;
    }

    @Override
    public String toString() {
        return "TrainExpData{" +
                "trainId='" + trainId + '\'' +
                ", trainInstruction='" + trainInstruction + '\'' +
                ", trainClass='" + trainClass + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", trainDes='" + trainDes + '\'' +
                '}';
    }
}
