package com.wstro.app.common.data;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Class Note:
 * SharedPreference Helper class,used only by{@link AbstractDataManager} is recommended
 *
 * Preference 的帮助类，推荐只在{@link AbstractDataManager}中使用
 */
public class PreferencesHelper {
    private static final String SETTING = "settings";

    private static final String IS_FIRST_RUN = "isFirstRun";
    private static final String HAS_AD_PAGE = "hasAdLoading";
    private static final String AD_PAGE_LOADING_TIME = "AdLoadingTime";
    private static final String AD_PAGE_ANCELABLE = "AdPageCancelable";
    private static final String AD_PAGE_URL = "AdPageUrl";
    private static final String LOGIN_VERSION_NAME = "loginVersionName";
    //private static final String LOGIN_USER_NAME = "loginUserName";

    private static final String OAUTH_TOKEN = "oauthToken";
    private static final String USER_INFO = "userInfo";

    private static final String LOGIN_INFO = "loginInfo";

    private final SharedPreferences pref;

    public PreferencesHelper(Context context) {
        this(context,SETTING);
    }

    public PreferencesHelper(Context context,String fileName) {
        pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public String getLoginInfo() {
        return pref.getString(LOGIN_INFO,"");
    }

    public void setLoginInfo(String loginInfo) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LOGIN_INFO, loginInfo);
        editor.apply();
    }

    public boolean isFirstRun() {
        return pref.getBoolean(IS_FIRST_RUN, true);
    }

    public boolean hasADPage(){
        return pref.getBoolean(HAS_AD_PAGE, false);
    }

    public void setADPage(boolean hasAdPage){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(HAS_AD_PAGE, hasAdPage);
        editor.apply();
    }

    public void setIsFirstRun(boolean isFirst) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_FIRST_RUN, isFirst);
        editor.apply();
    }

    public String getLoginVersionName() {
        return pref.getString(LOGIN_VERSION_NAME, "");
    }

    public void setLoginVersionName(String versionName) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LOGIN_VERSION_NAME, versionName);
        editor.apply();
    }


    public String getOauthToken() {
        return pref.getString(OAUTH_TOKEN, "");
    }

    public void setOauthToken(String oauthToken) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(OAUTH_TOKEN, oauthToken);
        editor.apply();
    }

    public String getUserInfo() {
        return pref.getString(USER_INFO, "");
    }

    public void setUserInfo(String userInfo) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_INFO, userInfo);
        editor.apply();
    }

    public void clearUserInfo(){
        remove(USER_INFO);
    }

    public void clearLoginInfo(){
        remove(LOGIN_INFO);
    }

    public void clearOauthToken(){
        remove(OAUTH_TOKEN);
    }

    public void remove(String key){
        pref.edit().remove(key).apply();
    }

    public void clear() {
        pref.edit().clear().apply();
    }

}
