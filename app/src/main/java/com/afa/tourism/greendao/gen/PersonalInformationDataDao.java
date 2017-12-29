package com.afa.tourism.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hr.ui.bean.PersonalInformationData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PERSONAL_INFORMATION_DATA".
*/
public class PersonalInformationDataDao extends AbstractDao<PersonalInformationData, String> {

    public static final String TABLENAME = "PERSONAL_INFORMATION_DATA";

    /**
     * Properties of entity PersonalInformationData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", true, "NAME");
        public final static Property Sex = new Property(1, String.class, "sex", false, "SEX");
        public final static Property ImageUrl = new Property(2, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property Birth = new Property(3, String.class, "birth", false, "BIRTH");
        public final static Property LivePlace = new Property(4, String.class, "livePlace", false, "LIVE_PLACE");
        public final static Property WorkTime = new Property(5, String.class, "workTime", false, "WORK_TIME");
        public final static Property PhoneNumber = new Property(6, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property Email = new Property(7, String.class, "Email", false, "EMAIL");
        public final static Property PositionTitle = new Property(8, String.class, "positionTitle", false, "POSITION_TITLE");
    }


    public PersonalInformationDataDao(DaoConfig config) {
        super(config);
    }
    
    public PersonalInformationDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PERSONAL_INFORMATION_DATA\" (" + //
                "\"NAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: name
                "\"SEX\" TEXT," + // 1: sex
                "\"IMAGE_URL\" TEXT," + // 2: imageUrl
                "\"BIRTH\" TEXT," + // 3: birth
                "\"LIVE_PLACE\" TEXT," + // 4: livePlace
                "\"WORK_TIME\" TEXT," + // 5: workTime
                "\"PHONE_NUMBER\" TEXT," + // 6: phoneNumber
                "\"EMAIL\" TEXT," + // 7: Email
                "\"POSITION_TITLE\" TEXT);"); // 8: positionTitle
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PERSONAL_INFORMATION_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PersonalInformationData entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(2, sex);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(3, imageUrl);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(4, birth);
        }
 
        String livePlace = entity.getLivePlace();
        if (livePlace != null) {
            stmt.bindString(5, livePlace);
        }
 
        String workTime = entity.getWorkTime();
        if (workTime != null) {
            stmt.bindString(6, workTime);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(7, phoneNumber);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(8, Email);
        }
 
        String positionTitle = entity.getPositionTitle();
        if (positionTitle != null) {
            stmt.bindString(9, positionTitle);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PersonalInformationData entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(2, sex);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(3, imageUrl);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(4, birth);
        }
 
        String livePlace = entity.getLivePlace();
        if (livePlace != null) {
            stmt.bindString(5, livePlace);
        }
 
        String workTime = entity.getWorkTime();
        if (workTime != null) {
            stmt.bindString(6, workTime);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(7, phoneNumber);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(8, Email);
        }
 
        String positionTitle = entity.getPositionTitle();
        if (positionTitle != null) {
            stmt.bindString(9, positionTitle);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public PersonalInformationData readEntity(Cursor cursor, int offset) {
        PersonalInformationData entity = new PersonalInformationData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imageUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // birth
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // livePlace
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // workTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // phoneNumber
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Email
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // positionTitle
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PersonalInformationData entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSex(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImageUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBirth(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLivePlace(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWorkTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPhoneNumber(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEmail(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPositionTitle(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final String updateKeyAfterInsert(PersonalInformationData entity, long rowId) {
        return entity.getName();
    }
    
    @Override
    public String getKey(PersonalInformationData entity) {
        if(entity != null) {
            return entity.getName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PersonalInformationData entity) {
        return entity.getName() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
