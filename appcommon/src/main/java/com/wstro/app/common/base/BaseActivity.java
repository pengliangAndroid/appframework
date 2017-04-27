package com.wstro.app.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wstro.app.common.utils.StatusBarCompat;

import butterknife.ButterKnife;

/**
 * Created by pengl on 2016/9/6.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static int statusBarColor = -1;

    protected ProgressDialog progressDialog;

    protected boolean isStatusCompat = true;

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());

        context = this;

        BaseActivityManager.getInstance().addActivity(this);

        ButterKnife.bind(this);

        compatStatusBar();

        this.initToolbar(savedInstanceState);
        this.initViewsAndEvents(savedInstanceState);
        this.initData();
    }

    private void compatStatusBar(){
        if (isStatusCompat) {
            if(statusBarColor == -1) {
                int resourceId = getResources().getIdentifier("colorPrimary", "color", getPackageName());
                statusBarColor = getResources().getColor(resourceId);
            }

            //LogUtil.d(this.getClass().getSimpleName() + ":" + statusBarColor);
            StatusBarCompat.compat(this, statusBarColor);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        BaseActivityManager.getInstance().removeActivity(this);
        context = null;
    }


    public void setStatusCompat(boolean statusCompat) {
        isStatusCompat = statusCompat;
    }

    protected abstract int getLayoutId();

    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected <V extends View> V findView(int id) {
        return (V) this.findViewById(id);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        progressDialog = ProgressDialog.show(getContext(),"",message);
        progressDialog.setCancelable(cancelable);
    }

    public void stopProgressDialog() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(),message,Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public Context getContext() {
        return this;
    }
}
