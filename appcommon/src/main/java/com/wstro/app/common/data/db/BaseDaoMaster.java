/*
package com.wstro.app.common.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

*/
/**
 * Master of DAO (schema version 1): knows all DAOs.
 *//*

public class BaseDaoMaster extends AbstractDaoMaster {

    public BaseDaoMaster(SQLiteDatabase db,int schemaVersion) {
        this(new StandardDatabase(db),schemaVersion);
    }

    public BaseDaoMaster(Database db,int schemaVersion) {
        super(db, schemaVersion);
        registerDaoClass(LoginUserDao.class);
    }

    public BaseDaoSession newSession() {
        return new BaseDaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public BaseDaoSession newSession(IdentityScopeType type) {
        return new BaseDaoSession(db, type, daoConfigMap);
    }

    public static abstract class OpenHelper extends DatabaseOpenHelper {
        protected int schemaVersion = 1;

        public OpenHelper(Context context, String name ,int schemaVersion) {
            super(context, name, schemaVersion);
            this.schemaVersion = schemaVersion;
        }

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int schemaVersion) {
            super(context, name, factory, schemaVersion);
            this.schemaVersion = schemaVersion;
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + schemaVersion);
            createAllTables(db, false);
        }

        protected abstract void createAllTables(Database db, boolean ifNotExists);

        protected abstract void dropAllTables(Database db, boolean ifNotExists);

        public int getSchemaVersion() {
            return schemaVersion;
        }
    }

}
*/
