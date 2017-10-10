package com.wstro.app.common.data;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wstro.app.common.CommonConstants;
import com.wstro.app.common.data.db.DataBaseHelper;
import com.wstro.app.common.data.db.LoginUser;
import com.wstro.app.common.utils.SettingUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class AbstractDataManager {
    public static final String JSON = "application/json;charset=utf-8";

    private PreferencesHelper preferencesHelper;
    private DataBaseHelper dataBaseHelper;
    private HttpHelper httpHelper;
    private Gson gson;

    protected abstract PreferencesHelper buildPreferencesHelper(Context context);
    protected abstract DataBaseHelper buildDataBaseHelper(Context context);
    protected abstract HttpHelper buildHttpHelper(Context context);

    public void init(Context context) {
        preferencesHelper = buildPreferencesHelper(context);
        dataBaseHelper = buildDataBaseHelper(context);
        httpHelper = buildHttpHelper(context);

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        CommonConstants.CURRENT_FONT_SCALE = SettingUtil.getCurrentFontSize(context);
        CommonConstants.CURRENT_LOCALE = SettingUtil.getCurrentLocale(context);
    }

    public void destroy(){
        if(httpHelper != null) {
            httpHelper.destroy();
            httpHelper = null;
        }

        if(httpHelper != null) {
            dataBaseHelper.destroy();
            dataBaseHelper = null;
        }

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

    public void saveLoginUser(LoginUser user) {
        dataBaseHelper.save(user, dataBaseHelper.getLoginUserDao());
    }

    public void updateLoginUser(LoginUser user) {
        dataBaseHelper.update(user, dataBaseHelper.getLoginUserDao());
    }

    public LoginUser getLastLoginUser() {
        String token = preferencesHelper.getLoginInfo();
        if (TextUtils.isEmpty(token))
            return null;
        return gson.fromJson(token, LoginUser.class);
    }

    public void saveLastLoginUser(LoginUser loginUser) {
        if (loginUser != null)
            preferencesHelper.setLoginInfo(gson.toJson(loginUser));
    }

    public void clearLoginInfo() {
        preferencesHelper.clearLoginInfo();
    }


    public void clearOauthToken() {
        preferencesHelper.clearOauthToken();
    }

    public List<LoginUser> getLoginUsesList() {
        return dataBaseHelper.queryAll(dataBaseHelper.getLoginUserDao());
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


    public DataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public HttpHelper getHttpHelper() {
        return httpHelper;
    }

}
