package com.hr.ui.db;

import com.afa.tourism.greendao.gen.ScanHistoryBeanDao;
import com.afa.tourism.greendao.gen.ThirdLoginBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.bean.ThirdLoginBean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class ScanHistoryUtils {
    public static ScanHistoryUtils instance;
    public static ScanHistoryUtils getInstance(){
        if(instance==null){
            instance=new ScanHistoryUtils();
        }
        return instance;
    }
    public void insertOrReplcae(ScanHistoryBean scanHistoryBean){
        HRApplication.getDaoInstant().getScanHistoryBeanDao().insertOrReplace(scanHistoryBean);
    }
    public void deleteAll(){
        if(queryAll()!=null&&queryAll().size()!=0) {
            HRApplication.getDaoInstant().getScanHistoryBeanDao().deleteAll();
        }
    }
    public List<ScanHistoryBean> query(int page){
        List<ScanHistoryBean> scanHistoryBeanList=HRApplication.getDaoInstant().getScanHistoryBeanDao().queryBuilder().limit(20).offset((page-1)*20).orderDesc(ScanHistoryBeanDao.Properties.Time).list();
        return scanHistoryBeanList;
    }
    public List<ScanHistoryBean> queryAll(){
        List<ScanHistoryBean> scanHistoryBeans=HRApplication.getDaoInstant().getScanHistoryBeanDao().loadAll();
        return scanHistoryBeans;
    }
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param thirdLoginBean
     *//*
    public static void insertOrReplace() {
        HRApplication.getDaoInstant().getHistoryDataDao().insertOrReplace(his);
    }

    *//**
     * 删除数据
     *
     * @param id
     *//*
    public static void deleteThirdPart(long id) {
        HRApplication.getDaoInstant().getThirdLoginBeanDao().deleteByKey(id);
    }

    *//**
     * 更新数据
     *
     * @param thirdLoginBean
     *//*
    public static void updateThirdPart(ThirdLoginBean thirdLoginBean) {
       HRApplication.getDaoInstant().getThirdLoginBeanDao().update(thirdLoginBean);
    }

    *//**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     *//*
    public static List<ThirdLoginBean> queryThirdPart(String type) {
        return HRApplication.getDaoInstant().getThirdLoginBeanDao().queryBuilder().where(ThirdLoginBeanDao.Properties.Type.eq(type)).list();
    }

    *//**
     * 查询全部数据
     *//*
    public static List<ThirdLoginBean> queryAll() {
        return HRApplication.getDaoInstant().getThirdLoginBeanDao().loadAll();
    }*/

}
