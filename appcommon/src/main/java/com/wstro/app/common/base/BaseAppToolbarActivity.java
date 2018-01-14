package com.wstro.app.common.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wstro.app.common.R;


/**
 * @author pengl
 */
public abstract class BaseAppToolbarActivity extends BaseActivity {
    protected TextView rightText;
    protected TextView leftText;
    protected TextView titleText;
    protected TextView leftImageText;
    protected ImageView rightImage;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rightText = (TextView) findViewById(R.id.toolbar_right_text);
        leftText = (TextView) findViewById(R.id.toolbar_left_text);
        titleText = (TextView) findViewById(R.id.toolbar_title);
        leftImageText = (TextView) findViewById(R.id.toolbar_left_img_text);
        rightImage = (ImageView) findViewById(R.id.toolbar_right_image);
        leftImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.initToolbarHelper();
    }


    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        if (this.toolbar == null) return;

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(5.0f);
        }*/
    }

    protected void setNotFitsSystemWindows(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setFitsSystemWindows(false);
    }

    protected void showBackText(){
        leftText.setVisibility(View.VISIBLE);
    }

    protected void showBackImageText(){
        leftImageText.setVisibility(View.VISIBLE);
    }

    protected void hideBackText(){
        leftText.setVisibility(View.GONE);
        leftImageText.setVisibility(View.GONE);
    }

}
