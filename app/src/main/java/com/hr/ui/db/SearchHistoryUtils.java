package com.hr.ui.db;

import com.afa.tourism.greendao.gen.ResumeDataDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.HistoryBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.ResumeData;

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
    public static void insertJobSearchDataOrReplace(HistoryBean historyBean) {
        HRApplication.getDaoInstant().getHistoryBeanDao().insertOrReplace(historyBean);
    }

    public static void insertJobSearchData(HistoryBean historyBean) {
        HRApplication.getDaoInstant().getHistoryBeanDao().insert(historyBean);
    }


    public static void deleteAll() {
        HRApplication.getDaoInstant().getHistoryBeanDao().deleteAll();
    }

    /**
     * 查询全部数据
     */
    public static List<HistoryBean> queryAll() {
        return HRApplication.getDaoInstant().getHistoryBeanDao().loadAll();
    }
}
