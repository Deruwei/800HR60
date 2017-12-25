package com.afa.tourism.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hr.ui.bean.EducationData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EDUCATION_DATA".
*/
public class EducationDataDao extends AbstractDao<EducationData, String> {

    public static final String TABLENAME = "EDUCATION_DATA";

    /**
     * Properties of entity EducationData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SchoolName = new Property(0, String.class, "schoolName", true, "SCHOOL_NAME");
        public final static Property Profession = new Property(1, String.class, "profession", false, "PROFESSION");
        public final static Property Degree = new Property(2, String.class, "degree", false, "DEGREE");
        public final static Property StartTime = new Property(3, String.class, "startTime", false, "START_TIME");
        public final static Property EndTime = new Property(4, String.class, "endTime", false, "END_TIME");
    }


    public EducationDataDao(DaoConfig config) {
        super(config);
    }
    
    public EducationDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EDUCATION_DATA\" (" + //
                "\"SCHOOL_NAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: schoolName
                "\"PROFESSION\" TEXT," + // 1: profession
                "\"DEGREE\" TEXT," + // 2: degree
                "\"START_TIME\" TEXT," + // 3: startTime
                "\"END_TIME\" TEXT);"); // 4: endTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EDUCATION_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EducationData entity) {
        stmt.clearBindings();
 
        String schoolName = entity.getSchoolName();
        if (schoolName != null) {
            stmt.bindString(1, schoolName);
        }
 
        String profession = entity.getProfession();
        if (profession != null) {
            stmt.bindString(2, profession);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(3, degree);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(4, startTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(5, endTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EducationData entity) {
        stmt.clearBindings();
 
        String schoolName = entity.getSchoolName();
        if (schoolName != null) {
            stmt.bindString(1, schoolName);
        }
 
        String profession = entity.getProfession();
        if (profession != null) {
            stmt.bindString(2, profession);
        }
 
        String degree = entity.getDegree();
        if (degree != null) {
            stmt.bindString(3, degree);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(4, startTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(5, endTime);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public EducationData readEntity(Cursor cursor, int offset) {
        EducationData entity = new EducationData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // schoolName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // profession
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // degree
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // startTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // endTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EducationData entity, int offset) {
        entity.setSchoolName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setProfession(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDegree(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStartTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEndTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(EducationData entity, long rowId) {
        return entity.getSchoolName();
    }
    
    @Override
    public String getKey(EducationData entity) {
        if(entity != null) {
            return entity.getSchoolName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EducationData entity) {
        return entity.getSchoolName() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
