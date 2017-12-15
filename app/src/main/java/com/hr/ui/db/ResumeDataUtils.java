package com.hr.ui.db;

import com.afa.tourism.greendao.gen.ResumeDataDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.bean.ThirdLoginBean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class ResumeDataUtils {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param resumeData
     */
    public static void insertResumeDataOrReplace(ResumeData resumeData) {
        HRApplication.getDaoInstant().getResumeDataDao().insertOrReplace(resumeData);
    }

    public static void insertResumeData(ResumeData resumeData) {
        HRApplication.getDaoInstant().getResumeDataDao().insert(resumeData);
    }
    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteThirdPart(long id) {
        HRApplication.getDaoInstant().getThirdLoginBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param resumeData
     */
    public static void updateThirdPart(ResumeData resumeData) {
        HRApplication.getDaoInstant().getResumeDataDao().update(resumeData);
    }

    public static void deleteAll() {
        HRApplication.getDaoInstant().getResumeDataDao().deleteAll();
    }
    /**
     * 查询条件为resumeId的数据
     *
     * @return
     */
    public static List<ResumeData> queryThirdPart(String resumeId) {
        return HRApplication.getDaoInstant().getResumeDataDao().queryBuilder().where(ResumeDataDao.Properties.ResumeId.eq(resumeId)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<ResumeData> queryAll() {
        return HRApplication.getDaoInstant().getResumeDataDao().loadAll();
    }
}
