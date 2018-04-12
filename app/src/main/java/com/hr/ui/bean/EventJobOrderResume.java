package com.hr.ui.bean;

import java.util.List;

public class EventJobOrderResume  {
    private int type;  //0选择行业  1选择领域  2 选择职位  3,传递选择行业  4，传递选择领域  5，传递选择职位  6.选择行业next 7.选择领域next  8.选择职位next
    private String industryId;
    private List<String> industryIds;
    private int updateNum;
    private List<CityBean> selectList;
    public EventJobOrderResume(int type,String industryId,List<String> industryIds,int updateNum){
        this.type=type;
        this.industryId=industryId;
        this.updateNum=updateNum;
        this.industryIds=industryIds;
    }
    public EventJobOrderResume(int type,String industryId){
        this.type=type;
        this.industryId=industryId;
    }
    public EventJobOrderResume(int type){
        this.type=type;

    }
    public EventJobOrderResume(int type,String industryId,List<CityBean> selectList){
        this.industryId=industryId;
        this.type=type;
        this.selectList=selectList;
    }
    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public List<String> getIndustryIds() {
        return industryIds;
    }

    public void setIndustryIds(List<String> industryIds) {
        this.industryIds = industryIds;
    }

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CityBean> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<CityBean> selectList) {
        this.selectList = selectList;
    }
}
