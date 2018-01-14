/*
package com.wstro.app.common.data.db;


import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.util.List;

import rx.Observable;

*/
/**
 * Created by pengl on 2016/9/21.
 *//*

public class DataBaseHelper {

    private BaseDaoMaster daoMaster;

    private UpgradeHelper upgradeHelper;

    private BaseDaoSession daoSession;

    public DataBaseHelper(UpgradeHelper upgradeHelper){
        //upgradeHelper = new UpgradeHelper(context,DB_NAME,null,daoMaster);

        this.daoMaster = new BaseDaoMaster(upgradeHelper.getWritableDatabase(),upgradeHelper.getSchemaVersion());
        this.upgradeHelper = upgradeHelper;
        daoSession = daoMaster.newSession();
    }

    public UpgradeHelper getUpgradeHelper() {
        return upgradeHelper;
    }

    public BaseDaoMaster getDaoMaster() {
        return daoMaster;
    }

    public BaseDaoSession getDaoSession() {
        return daoSession;
    }

    public LoginUserDao getLoginUserDao(){
        return daoSession.getLoginUserDao();
    }

    public <T> Observable<T> saveRx(T obj, AbstractDao dao){
        return dao.rx().save(obj);
    }

    public <T> void save(T obj, AbstractDao dao) {
        dao.save(obj);
    }

    public <T> Observable<List<T>> saveListRx(List<T> list, AbstractDao dao) {
        return dao.rx().saveInTx(list);
    }

    public <T> void saveList(List<T> list, AbstractDao dao) {
        dao.saveInTx(list);
    }

    public <T> Observable<T> insert(T obj, AbstractDao dao){
       return dao.rx().insert(obj);
    }

    public <T> Observable<T> delete(T obj, AbstractDao dao){
        return dao.rx().delete(obj);
    }

    public <T> Observable<T> updateRx(T obj, AbstractDao dao){
        return dao.rx().update(obj);
    }

    public <T> void update(T obj, AbstractDao dao) {
        dao.update(obj);
    }

    public <T> Observable<List<T>> queryAllRx(AbstractDao dao) {
        return dao.rx().loadAll();
    }

    public <T> List<T> queryAll(AbstractDao dao) {
        return dao.loadAll();
    }


    public <T> Observable<List<T>> queryByColumn(Property property, Object columnValue, AbstractDao dao) {
        return dao.queryBuilder().where(property.eq(columnValue)).orderAsc().rx().list();
    }

    public void destroy(){
        daoSession = null;
        upgradeHelper = null;
        daoMaster = null;
    }

}
*/
