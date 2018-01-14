/*
package com.wstro.app.common.data.db;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

*/
/**
 * {@inheritDoc}
 *
 * @see AbstractDaoSession
 *//*

public class BaseDaoSession extends AbstractDaoSession {

    private final DaoConfig loginUserDaoConfig;

    private final LoginUserDao loginUserDao;

    public BaseDaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);


        loginUserDaoConfig = daoConfigMap.get(LoginUserDao.class).clone();
        loginUserDaoConfig.initIdentityScope(type);

        loginUserDao = new LoginUserDao(loginUserDaoConfig, this);

        registerDao(LoginUser.class, loginUserDao);
    }

    public void clear() {
        loginUserDaoConfig.clearIdentityScope();
    }

    public LoginUserDao getLoginUserDao() {
        return loginUserDao;
    }

}
*/
