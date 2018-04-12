package com.hr.ui.bean;

import com.hr.ui.ui.main.activity.SelectFunctionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.EventListener;
import java.util.List;

public class EvenList {
    private int type;// 0.代表城市  1.代表职位  2代表领域
    private String industry;
    private List<CityBean> list;
    private int position;
    private List<CityBean> selectFunctionList;
    private List<CityBean> selectPositionList;
    public EvenList(){}
    public EvenList(List<CityBean> list){
        this.list=list;
    }
    public EvenList(int type,List<CityBean> list){
        this.type=type;
        this.list=list;
    }
    //为修改求职意向页面的修改求职意向构建
    public EvenList(int type,int position, String industry,List<CityBean> selectFunctionList,List<CityBean> selectPositionList){
        this.type=type;
        this.position=position;
        this.industry=industry;
        this.selectFunctionList=selectFunctionList;
        this.selectPositionList=selectPositionList;
    }
    //为修改求职意向页面的添加求职意向构建
    public EvenList(int type, String industry,List<CityBean> selectFunctionList,List<CityBean> selectPositionList){
        this.type=type;
        this.industry=industry;
        this.selectFunctionList=selectFunctionList;
        this.selectPositionList=selectPositionList;
    }
    public EvenList(int type,String industry,List<CityBean> list){
        this.type=type;
        this.industry=industry;
        this.list=list;
    }
    public EvenList(int type,int position){
        this.type=type;
        this.position=position;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<CityBean> getList() {
        return list;
    }

    public void setList(List<CityBean> list) {
        this.list = list;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<CityBean> getSelectFunctionList() {
        return selectFunctionList;
    }

    public void setSelectFunctionList(List<CityBean> selectFunctionList) {
        this.selectFunctionList = selectFunctionList;
    }

    public List<CityBean> getSelectPositionList() {
        return selectPositionList;
    }

    public void setSelectPositionList(List<CityBean> selectPositionList) {
        this.selectPositionList = selectPositionList;
    }
}
