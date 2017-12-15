package com.wstro.app;

import android.app.Application;

import com.wstro.app.data.DataManager;

import hugo.weaving.DebugLog;

/**
 * ClassName: WApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 *
 * @author pengl
 * @date 2017/10/7
 */
public class WApplication extends Application{

    @DebugLog
    @Override
    public void onCreate() {
        super.onCreate();

        DataManager.get().init(this);



    }
}
