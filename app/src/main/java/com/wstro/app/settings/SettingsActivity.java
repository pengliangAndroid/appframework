package com.wstro.app.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wstro.app.MainActivity;
import com.wstro.app.R;
import com.wstro.app.common.CommonConstants;
import com.wstro.app.common.base.BaseActivityManager;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.common.utils.DialogUtil;
import com.wstro.app.common.utils.SettingUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseAppToolbarActivity {

    @BindView(R.id.tv_font)
    TextView tvFont;
    @BindView(R.id.tv_language)
    TextView tvLanguage;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivity(starter);
    }


    private float fontScale;
    private String curLanguage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initViewsAndEvents(Bundle bundle) {
        titleText.setText(getString(R.string.settings_title));
    }

    @Override
    protected void initData() {
        fontScale = SettingUtil.getCurrentFontSize(this);
        curLanguage = SettingUtil.getCurrentLocale(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private String[] names = new String[]{"小", "中", "标准", "大", "最大"};
    private String[] lNames = new String[]{"中文", "英语"};

    private void displayDialog() {
        DialogUtil.dialogBuilder(this, "选择", null)
                .setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float scale = 0;
                        switch (which) {
                            case 0:
                                scale = CommonConstants.FONT_SMALL_SCALE;
                                break;
                            case 1:
                                scale = CommonConstants.FONT_SMALL_MIDDLE_SCALE;
                                break;
                            case 2:
                                scale = CommonConstants.FONT_NORMAL_SCALE;
                                break;
                            case 3:
                                scale = CommonConstants.FONT_BIG_MIDDLE_SCALE;
                                break;
                            case 4:
                                scale = CommonConstants.FONT_BIG_SCALE;
                                break;
                        }

                        if (fontScale != scale) {
                            fontScale = scale;

                            titleText.setTextSize(16 * scale);
                            tvFont.setTextSize(15 * scale);

                            changeScale(scale);
                        }
                    }
                })
                .create()
                .show();
    }

    private void displayLanguageDialog() {
        DialogUtil.dialogBuilder(this, "选择", null)
                .setItems(lNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = null;
                        switch (which) {
                            case 0:
                                language = Locale.CHINESE.getLanguage();
                                break;
                            case 1:
                                language = Locale.ENGLISH.getLanguage();
                                break;
                        }

                        if (!language.equals(curLanguage)) {
                            curLanguage = language;

                            //titleText.setText(16*scale);
                            //textView.setTextSize(15*scale);

                            changeLanguage(language);
                        }
                    }
                })
                .create()
                .show();
    }


    private void changeScale(final float scale) {
        DialogUtil.dialogBuilder(context, "提示", "字体修改需要重启应用才能生效")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SettingUtil.setCurrentFontSize(context, scale);
                        CommonConstants.CURRENT_FONT_SCALE = scale;

                        BaseActivityManager.getInstance().clear();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void changeLanguage(final String language) {
        DialogUtil.dialogBuilder(context, "提示", "语言修改需要重启应用才能生效")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SettingUtil.setCurrentLocale(context, language);
                        CommonConstants.CURRENT_LOCALE = language;

                        BaseActivityManager.getInstance().clear();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }



    @OnClick({R.id.ll_font, R.id.ll_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_font:
                displayDialog();
                break;
            case R.id.ll_language:
                displayLanguageDialog();
                break;
        }
    }
}
