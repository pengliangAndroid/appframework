package com.wstro.app.data;

import android.content.Context;

import com.wstro.app.common.data.AbstractDataManager;
import com.wstro.app.common.data.PreferencesHelper;
import com.wstro.app.common.data.RetrofitHelper;
import com.wstro.app.common.data.db.DataBaseHelper;

import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;

/**
 * ClassName: DataManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 *
 * @author pengl
 * @date 2017/10/7
 */

public class DataManager extends AbstractDataManager{
    private static final String DB_NAME = "data.db";

    private static final int DB_VERSION = 1;

    private static final String SP_NAME = "settings";

    private static final String BASE_DOMAIN = "http://192.168.1.2";

    private static DataManager instance = null;

    private RetrofitHelper retrofitHelper;

    private DataManager(){}

    @DebugLog
    @Override
    protected PreferencesHelper buildPreferencesHelper(Context context) {
        return new CustomPreferencesHelper(context,SP_NAME);
    }

    @DebugLog
    @Override
    protected DataBaseHelper buildDataBaseHelper(Context context) {
        return new DataBaseHelper(new CustomUpgradeHelper(context,DB_NAME,null,DB_VERSION));
    }

    @DebugLog
    @Override
    protected RetrofitHelper buildRetrofitHelper(Context context) {
        retrofitHelper = new RetrofitHelper(context,BASE_DOMAIN);
        return retrofitHelper;
    }

    public OkHttpClient getOKHttpClient(){
        if(retrofitHelper == null)
            return null;

        return retrofitHelper.getOkHttpClient();
    }

    public static DataManager get(){
        if(instance == null){
            synchronized (DataManager.class){
                if(instance == null){
                    instance = new DataManager();
                }
            }
        }

        return instance;
    }

    public void destroy(){
        super.destroy();

        if(retrofitHelper != null)
            retrofitHelper.destroy();
        instance = null;
    }



}
