package com.afa.tourism.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hr.ui.bean.SearchHistoryBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_HISTORY_BEAN".
*/
public class SearchHistoryBeanDao extends AbstractDao<SearchHistoryBean, String> {

    public static final String TABLENAME = "SEARCH_HISTORY_BEAN";

    /**
     * Properties of entity SearchHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SearchName = new Property(0, String.class, "searchName", true, "SEARCH_NAME");
        public final static Property JobType = new Property(1, int.class, "jobType", false, "JOB_TYPE");
        public final static Property PlaceId = new Property(2, String.class, "placeId", false, "PLACE_ID");
        public final static Property AddDate = new Property(3, String.class, "addDate", false, "ADD_DATE");
        public final static Property IndustryId = new Property(4, String.class, "industryId", false, "INDUSTRY_ID");
        public final static Property FieldId = new Property(5, String.class, "fieldId", false, "FIELD_ID");
        public final static Property WorkExp = new Property(6, String.class, "workExp", false, "WORK_EXP");
        public final static Property WorkType = new Property(7, String.class, "workType", false, "WORK_TYPE");
        public final static Property JobTime = new Property(8, String.class, "JobTime", false, "JOB_TIME");
        public final static Property Degree = new Property(9, String.class, "degree", false, "DEGREE");
        public final static Property CompanyScale = new Property(10, String.class, "companyScale", false, "COMPANY_SCALE");
        public final static Property Salary_left = new Property(11, String.class, "salary_left", false, "SALARY_LEFT");
        public final static Property Salary_right = new Property(12, String.class, "salary_right", false, "SALARY_RIGHT");
        public final static Property PositionId = new Property(13, String.class, "positionId", false, "POSITION_ID");
        public final static Property CompanyType = new Property(14, String.class, "companyType", false, "COMPANY_TYPE");
    }


    public SearchHistoryBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SearchHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_HISTORY_BEAN\" (" + //
                "\"SEARCH_NAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: searchName
                "\"JOB_TYPE\" INTEGER NOT NULL ," + // 1: jobType
                "\"PLACE_ID\" TEXT," + // 2: placeId
                "\"ADD_DATE\" TEXT," + // 3: addDate
                "\"INDUSTRY_ID\" TEXT," + // 4: industryId
                "\"FIELD_ID\" TEXT," + // 5: fieldId
                "\"WORK_EXP\" TEXT," + // 6: workExp
                "\"WORK_TYPE\" TEXT," + // 7: workType
                "\"JOB_TIME\" TEXT," + // 8: JobTime
                "\"DEGREE\" TEXT," + // 9: degree
                "\"COMPANY_SCALE\" TEXT," + // 10: companyScale
                "\"SALARY_LEFT\" TEXT," + // 11: salary_left
                "\"SALARY_RIGHT\" TEXT," + // 12: salary_right
                "\"POSITION_ID\" TEXT," + // 13: positionId
                "\"COMPANY_TYPE\" TEXT);"); // 14: companyType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
 
        String searchName = entity.getSearchName();
        if (searchName != null) {
            stmt.bindString(1, searchName);
        }
        stmt.bindLong(2, entity.getJobType());
 
        String placeId = entity.getPlaceId();
        if (placeId != null) {
            stmt.bindString(3, placeId);
        }
 
        String addDate = entity.getAddDate();
        if (addDate != null) {
            stmt.bindString(4, addDate);
        }
 
        String industryId = entity.getIndustryId();
        if (industryId != null) {
            stmt.bindString(5, industryId);
        }
 
        String fieldId = entity.getFieldId();
        if (fieldId != null) {
            stmt.bindString(6, fieldId);
        }
 
        String workExp = entity.getWorkExp();
        if (workExp != null) {
            stmt.bindString(7, workExp);
        }
 
        String workType = entity.getWorkType();
        if (workType != null) {
            stmt.bindString(8, workType);
        }
 
        String JobTime = entity.getJobTime();
        if (JobTime != null) {
            stmt.bindString(9, JobTime);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(10, degree);
        }
 
        String companyScale = entity.getCompanyScale();
        if (companyScale != null) {
            stmt.bindString(11, companyScale);
        }
 
        String salary_left = entity.getSalary_left();
        if (salary_left != null) {
            stmt.bindString(12, salary_left);
        }
 
        String salary_right = entity.getSalary_right();
        if (salary_right != null) {
            stmt.bindString(13, salary_right);
        }
 
        String positionId = entity.getPositionId();
        if (positionId != null) {
            stmt.bindString(14, positionId);
        }
 
        String companyType = entity.getCompanyType();
        if (companyType != null) {
            stmt.bindString(15, companyType);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
 
        String searchName = entity.getSearchName();
        if (searchName != null) {
            stmt.bindString(1, searchName);
        }
        stmt.bindLong(2, entity.getJobType());
 
        String placeId = entity.getPlaceId();
        if (placeId != null) {
            stmt.bindString(3, placeId);
        }
 
        String addDate = entity.getAddDate();
        if (addDate != null) {
            stmt.bindString(4, addDate);
        }
 
        String industryId = entity.getIndustryId();
        if (industryId != null) {
            stmt.bindString(5, industryId);
        }
 
        String fieldId = entity.getFieldId();
        if (fieldId != null) {
            stmt.bindString(6, fieldId);
        }
 
        String workExp = entity.getWorkExp();
        if (workExp != null) {
            stmt.bindString(7, workExp);
        }
 
        String workType = entity.getWorkType();
        if (workType != null) {
            stmt.bindString(8, workType);
        }
 
        String JobTime = entity.getJobTime();
        if (JobTime != null) {
            stmt.bindString(9, JobTime);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(10, degree);
        }
 
        String companyScale = entity.getCompanyScale();
        if (companyScale != null) {
            stmt.bindString(11, companyScale);
        }
 
        String salary_left = entity.getSalary_left();
        if (salary_left != null) {
            stmt.bindString(12, salary_left);
        }
 
        String salary_right = entity.getSalary_right();
        if (salary_right != null) {
            stmt.bindString(13, salary_right);
        }
 
        String positionId = entity.getPositionId();
        if (positionId != null) {
            stmt.bindString(14, positionId);
        }
 
        String companyType = entity.getCompanyType();
        if (companyType != null) {
            stmt.bindString(15, companyType);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public SearchHistoryBean readEntity(Cursor cursor, int offset) {
        SearchHistoryBean entity = new SearchHistoryBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // searchName
            cursor.getInt(offset + 1), // jobType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // placeId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // addDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // industryId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // fieldId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // workExp
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // workType
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // JobTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // degree
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // companyScale
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // salary_left
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // salary_right
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // positionId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // companyType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchHistoryBean entity, int offset) {
        entity.setSearchName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setJobType(cursor.getInt(offset + 1));
        entity.setPlaceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIndustryId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFieldId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWorkExp(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWorkType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setJobTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDegree(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCompanyScale(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSalary_left(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSalary_right(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setPositionId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCompanyType(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SearchHistoryBean entity, long rowId) {
        return entity.getSearchName();
    }
    
    @Override
    public String getKey(SearchHistoryBean entity) {
        if(entity != null) {
            return entity.getSearchName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SearchHistoryBean entity) {
        return entity.getSearchName() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
