package com.wstro.app.data.db;


import android.content.Context;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;


public class DataBaseHelper {

    private static final String DB_NAME = "data.db";

    private DaoMaster daoMaster;

    private UpgradeHelper upgradeHelper;

    private DaoSession daoSession;

    public DataBaseHelper(Context context){
        upgradeHelper = new UpgradeHelper(context,DB_NAME,null);

        this.daoMaster = new DaoMaster(upgradeHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public UpgradeHelper getUpgradeHelper() {
        return upgradeHelper;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public LoginUserDao getLoginUserDao(){
        return daoSession.getLoginUserDao();
    }

    public <T> void save(T obj, AbstractDao dao) {
        dao.save(obj);
    }

    public <T> void saveList(List<T> list, AbstractDao dao) {
        dao.saveInTx(list);
    }

    public <T> void update(T obj, AbstractDao dao) {
        dao.update(obj);
    }

    public <T> List<T> queryAll(AbstractDao dao) {
        return dao.loadAll();
    }

    public void destroy(){
        daoSession = null;
        upgradeHelper = null;
        daoMaster = null;
    }

}
