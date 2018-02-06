package com.afa.tourism.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hr.ui.bean.ScanHistoryBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCAN_HISTORY_BEAN".
*/
public class ScanHistoryBeanDao extends AbstractDao<ScanHistoryBean, String> {

    public static final String TABLENAME = "SCAN_HISTORY_BEAN";

    /**
     * Properties of entity ScanHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property JobId = new Property(0, String.class, "jobId", true, "JOB_ID");
        public final static Property JobName = new Property(1, String.class, "jobName", false, "JOB_NAME");
        public final static Property Place = new Property(2, String.class, "place", false, "PLACE");
        public final static Property Exp = new Property(3, String.class, "exp", false, "EXP");
        public final static Property Degree = new Property(4, String.class, "degree", false, "DEGREE");
        public final static Property CompanyName = new Property(5, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Salary = new Property(6, String.class, "salary", false, "SALARY");
        public final static Property Time = new Property(7, String.class, "time", false, "TIME");
        public final static Property Is_expect = new Property(8, String.class, "is_expect", false, "IS_EXPECT");
    }


    public ScanHistoryBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ScanHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCAN_HISTORY_BEAN\" (" + //
                "\"JOB_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: jobId
                "\"JOB_NAME\" TEXT," + // 1: jobName
                "\"PLACE\" TEXT," + // 2: place
                "\"EXP\" TEXT," + // 3: exp
                "\"DEGREE\" TEXT," + // 4: degree
                "\"COMPANY_NAME\" TEXT," + // 5: companyName
                "\"SALARY\" TEXT," + // 6: salary
                "\"TIME\" TEXT," + // 7: time
                "\"IS_EXPECT\" TEXT);"); // 8: is_expect
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCAN_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ScanHistoryBean entity) {
        stmt.clearBindings();
 
        String jobId = entity.getJobId();
        if (jobId != null) {
            stmt.bindString(1, jobId);
        }
 
        String jobName = entity.getJobName();
        if (jobName != null) {
            stmt.bindString(2, jobName);
        }
 
        String place = entity.getPlace();
        if (place != null) {
            stmt.bindString(3, place);
        }
 
        String exp = entity.getExp();
        if (exp != null) {
            stmt.bindString(4, exp);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(5, degree);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(6, companyName);
        }
 
        String salary = entity.getSalary();
        if (salary != null) {
            stmt.bindString(7, salary);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(8, time);
        }
 
        String is_expect = entity.getIs_expect();
        if (is_expect != null) {
            stmt.bindString(9, is_expect);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ScanHistoryBean entity) {
        stmt.clearBindings();
 
        String jobId = entity.getJobId();
        if (jobId != null) {
            stmt.bindString(1, jobId);
        }
 
        String jobName = entity.getJobName();
        if (jobName != null) {
            stmt.bindString(2, jobName);
        }
 
        String place = entity.getPlace();
        if (place != null) {
            stmt.bindString(3, place);
        }
 
        String exp = entity.getExp();
        if (exp != null) {
            stmt.bindString(4, exp);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(5, degree);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(6, companyName);
        }
 
        String salary = entity.getSalary();
        if (salary != null) {
            stmt.bindString(7, salary);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(8, time);
        }
 
        String is_expect = entity.getIs_expect();
        if (is_expect != null) {
            stmt.bindString(9, is_expect);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ScanHistoryBean readEntity(Cursor cursor, int offset) {
        ScanHistoryBean entity = new ScanHistoryBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // jobId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // jobName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // place
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // exp
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // degree
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // companyName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // salary
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // time
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // is_expect
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ScanHistoryBean entity, int offset) {
        entity.setJobId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setJobName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPlace(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setExp(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDegree(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCompanyName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSalary(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIs_expect(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ScanHistoryBean entity, long rowId) {
        return entity.getJobId();
    }
    
    @Override
    public String getKey(ScanHistoryBean entity) {
        if(entity != null) {
            return entity.getJobId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ScanHistoryBean entity) {
        return entity.getJobId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
