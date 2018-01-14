package com.wstro.app.main;

import android.os.Bundle;

import com.wstro.app.R;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.main.presenter.Main2Presenter;
import com.wstro.app.main.view.Main2View;

public class Main2Activity extends BaseAppToolbarActivity implements Main2View {

    Main2Presenter presenter;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initViewsAndEvents(Bundle bundle) {

    }

    @Override
    protected void initData() {
        presenter = new Main2Presenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }

}
