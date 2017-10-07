package com.wstro.app;

import android.os.Bundle;

import com.wstro.app.common.base.BaseAppToolbarActivity;

public class MainActivity extends BaseAppToolbarActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
