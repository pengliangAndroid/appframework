package com.wstro.app;

import android.os.Bundle;
import android.widget.Button;

import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseAppToolbarActivity {


    @BindView(R.id.btn_settings)
    Button btnSettings;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        titleText.setText(getString(R.string.app_name_title));

        showCustomProgressDialog("xxx");

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @OnClick(R.id.btn_settings)
    public void onViewClicked() {
        SettingsActivity.start(this);
    }
}
