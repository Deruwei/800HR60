package com.hr.ui.db;

import com.afa.tourism.greendao.gen.ThirdLoginBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ThirdLoginBean;

import java.util.List;

/**
 * Created by wdr on 2017/12/11.
 */

public class ThirdPartDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param thirdLoginBean
     */
    public static void insertThirPart(ThirdLoginBean thirdLoginBean) {
        HRApplication.getDaoInstant().getThirdLoginBeanDao().insertOrReplace(thirdLoginBean);
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
     * @param thirdLoginBean
     */
    public static void updateThirdPart(ThirdLoginBean thirdLoginBean) {
       HRApplication.getDaoInstant().getThirdLoginBeanDao().update(thirdLoginBean);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */
    public static List<ThirdLoginBean> queryThirdPart(String type) {
        return HRApplication.getDaoInstant().getThirdLoginBeanDao().queryBuilder().where(ThirdLoginBeanDao.Properties.Type.eq(type)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<ThirdLoginBean> queryAll() {
        return HRApplication.getDaoInstant().getThirdLoginBeanDao().loadAll();
    }

}
