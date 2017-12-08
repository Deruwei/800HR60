package com.hr.ui.db;

import android.content.Context;
import android.util.Log;

import com.afa.tourism.greendao.gen.DaoSession;
import com.afa.tourism.greendao.gen.UserDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.User;

import java.util.List;

/**
 * Created by wdr on 2017/11/20.
 */

public class DBUserService {
    private static final String TAG=DBUserService.class.getSimpleName();
    private static DBUserService instance;
    private static Context contextHr;
    private DaoSession daoSession;
    private UserDao userDao;
    public static DBUserService getInstance(Context context){
        if(instance==null){
            instance = new DBUserService();
                if(context==null){
                    contextHr= context.getApplicationContext();
                }
            instance.daoSession=HRApplication.getDaoSession(context);
            instance.userDao=instance.daoSession.getUserDao();
        }
        return instance;
    }
    public User loadNote(long id) {
        return userDao.load(id);
    }

    public List<User> loadAllNote(){
        return userDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<User> queryNote(String where, String... params){
        return userDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param user
     * @return insert or update note id
     */
    public long saveNote(User user){
        return userDao.insertOrReplace(user);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        userDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User note = list.get(i);
                    userDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote(){
       userDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        userDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(User user){
        userDao.delete(user);
    }
    public void updateNote(User user){
        userDao.update(user);

    }
}
