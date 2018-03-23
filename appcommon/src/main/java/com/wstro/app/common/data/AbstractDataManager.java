package com.wstro.app.common.data;

import android.content.Context;
import android.content.res.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wstro.app.common.CommonConstants;
import com.wstro.app.common.utils.SettingUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pengl
 */
public abstract class AbstractDataManager {
    public static final String JSON = "application/json;charset=utf-8";

    private PreferencesHelper preferencesHelper;
    private Gson gson;

    protected abstract PreferencesHelper buildPreferencesHelper(Context context);
    protected abstract void buildHttpHelper(Context context);
    protected abstract void buildDatabaseHelper(Context context);

    public void init(Context context) {
        preferencesHelper = buildPreferencesHelper(context);
        buildDatabaseHelper(context);
        buildHttpHelper(context);

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        CommonConstants.CURRENT_FONT_SCALE = SettingUtil.getCurrentFontSize(context);
        Configuration config = context.getResources().getConfiguration();
        CommonConstants.CURRENT_LOCALE = SettingUtil.getCurrentLocale(context,config.locale.getLanguage());
    }

    public void destroy(){
        preferencesHelper = null;
        gson = null;
    }


    public boolean isFirstRun() {
        return preferencesHelper.isFirstRun();
    }

    public String getLoginVersionName() {
        return preferencesHelper.getLoginVersionName();
    }

    public void setIsFirstRun(boolean isFirstRun) {
        preferencesHelper.setIsFirstRun(isFirstRun);
    }

    public void setLoginVersionName(String versionName) {
        preferencesHelper.setLoginVersionName(versionName);
    }

    public void clearLoginInfo() {
        preferencesHelper.clearLoginInfo();
    }


    public void clearOauthToken() {
        preferencesHelper.clearOauthToken();
    }


    public RequestBody createRequestBody(String json){
        return RequestBody.create(MediaType.parse(JSON), json);
    }

    public Gson getGson() {
        return gson;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

}
