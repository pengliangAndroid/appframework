package com.wstro.app.main;

import android.os.Bundle;

import com.wstro.app.R;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.main.presenter.Main3Presenter;
import com.wstro.app.main.view.Main3View;

public class Main3Activity extends BaseAppToolbarActivity implements Main3View {

    Main3Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    protected void initViewsAndEvents(Bundle bundle) {

    }

    @Override
    protected void initData() {
        presenter = new Main3Presenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }

}
