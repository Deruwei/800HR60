package com.hr.ui.db;

import android.content.Context;
import android.util.Log;

import com.afa.tourism.greendao.gen.DaoSession;
import com.afa.tourism.greendao.gen.ThirdPartBeanDao;
import com.afa.tourism.greendao.gen.UserDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ThirdPartBean;
import com.hr.ui.bean.User;

import java.util.List;

/**
 * Created by wdr on 2017/11/20.
 */

public class DBThirdPartService {
    private static final String TAG=DBThirdPartService.class.getSimpleName();
    private static DBThirdPartService instance;
    private static Context contextHr;
    private DaoSession daoSession;
    private ThirdPartBeanDao thirdPartBeanDao;
    public static DBThirdPartService getInstance(Context context){
        if(instance==null){
            instance = new DBThirdPartService();
                if(context==null){
                    contextHr= context.getApplicationContext();
                }
            instance.daoSession=HRApplication.getDaoSession(context);
            instance.thirdPartBeanDao=instance.daoSession.getThirdPartBeanDao();
        }
        return instance;
    }
    //  public long saveNote(User user){
       // return userDao.insertOrReplace(user);
//}

    /**
     * 更新或者插入
     * @param thirdPartBean
     * @return
     */
    public long saveData(ThirdPartBean thirdPartBean){
        return thirdPartBeanDao.insertOrReplace(thirdPartBean);
    }

    /**
     * 根据整个类进行删除
     * @param thirdPartBean
     */
    public void deleteData(ThirdPartBean thirdPartBean){
        thirdPartBeanDao.delete(thirdPartBean);
    }

    /**
     * 根据整个类进行修改
     * @param thirdPartBean
     */
    public void updateData(ThirdPartBean thirdPartBean){
        thirdPartBeanDao.update(thirdPartBean);
    }
    public void  deleteDataByType(String type){
        thirdPartBeanDao.deleteByKey(type);
    }
    public List<ThirdPartBean> queryDataByType(){
        List<ThirdPartBean> thirdPartBean=thirdPartBeanDao.loadAll();
        return thirdPartBean;
    }
}
