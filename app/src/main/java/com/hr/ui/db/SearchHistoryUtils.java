package com.hr.ui.db;

import com.afa.tourism.greendao.gen.ResumeDataDao;
import com.afa.tourism.greendao.gen.ScanHistoryBeanDao;
import com.afa.tourism.greendao.gen.SearchHistoryBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.HistoryBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.bean.SearchHistoryBean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class SearchHistoryUtils {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param historyBean
     */
    public static void insertJobSearchDataOrReplace(SearchHistoryBean historyBean) {
        if(queryAll().size()>=3){
            List<SearchHistoryBean> list=HRApplication.getDaoInstant().getSearchHistoryBeanDao().queryBuilder().limit(1).orderAsc(SearchHistoryBeanDao.Properties.AddDate).list();
            HRApplication.getDaoInstant().getSearchHistoryBeanDao().delete(list.get(0));
        }
        HRApplication.getDaoInstant().getSearchHistoryBeanDao().insertOrReplace(historyBean);
    }

    public static void insertJobSearchData(SearchHistoryBean historyBean) {
        HRApplication.getDaoInstant().getSearchHistoryBeanDao().insert(historyBean);
    }


    public static void deleteAll() {
        HRApplication.getDaoInstant().getSearchHistoryBeanDao().deleteAll();
    }

    /**
     * 查询全部数据
     */
    public static List<SearchHistoryBean> queryAll() {
        return HRApplication.getDaoInstant().getSearchHistoryBeanDao().queryBuilder().orderDesc(SearchHistoryBeanDao.Properties.AddDate).list();
    }
}
