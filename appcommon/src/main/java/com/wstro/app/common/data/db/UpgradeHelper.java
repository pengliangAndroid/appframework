package com.wstro.app.common.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 *  本地数据库的更新帮助类，可以拓展子类
 */
public class UpgradeHelper extends BaseDaoMaster.OpenHelper {

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int schemaVersion) {
        super(context, name, factory,schemaVersion);
    }

    @Override
    protected void createAllTables(Database db, boolean ifNotExists) {
        LoginUserDao.createTable(db,ifNotExists);
    }

    @Override
    protected void dropAllTables(Database db, boolean ifNotExists) {
        LoginUserDao.dropTable(db,ifNotExists);
    }

    /**
     * Here is where the calls to upgrade are executed
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");

        MigrationHelper.getInstance().migrate(this,db,
                LoginUserDao.class);
    }
}
