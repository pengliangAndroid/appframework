package com.wstro.app.common.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wstro.app.common.R;

/**
 * @author pengl
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;

    //protected AppBarLayout appBarLayout;

    protected ActionBarHelper actionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //appBarLayout = (AppBarLayout) findViewById(R.id.app_layout);

        this.initToolbarHelper();
    }


    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        //if (this.toolbar == null || this.mAppBarLayout == null) return;

        this.setSupportActionBar(this.toolbar);

        this.actionBarHelper = this.createActionBarHelper();
        this.actionBarHelper.init();

        /*if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(5.0f);
        }*/
    }


    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void showBack() {
        if (this.actionBarHelper != null) this.actionBarHelper.setDisplayHomeAsUpEnabled(true);
    }


    /**
     * set the AppBarLayout alpha
     *
     * @param alpha alpha
     */
   /* protected void setAppBarLayoutAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.mAppBarLayout.setAlpha(alpha);
        }
    }*/


    /**
     * set the AppBarLayout visibility
     *
     * @param visibility visibility
     */
   protected void setToolBarLayoutVisibility(boolean visibility) {
        if (visibility) {
            //this.mAppBarLayout.setVisibility(View.VISIBLE);
            this.toolbar.setVisibility(View.VISIBLE);
        } else {
            //this.mAppBarLayout.setVisibility(View.GONE);
            this.toolbar.setVisibility(View.GONE);
        }
    }


    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    public ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }


    public class ActionBarHelper {
        private final ActionBar actionBar;
        public CharSequence drawerTitle;
        public CharSequence title;


        public ActionBarHelper() {
            this.actionBar = getSupportActionBar();
        }


        public void init() {
            if (this.actionBar == null) return;
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(false);
            this.title = drawerTitle = getTitle();
        }


        public void onDrawerClosed() {
            if (this.actionBar == null) return;
            this.actionBar.setTitle(this.title);
        }


        public void onDrawerOpened() {
            if (this.actionBar == null) return;
            this.actionBar.setTitle(this.drawerTitle);
        }


        public void setTitle(CharSequence title) {
            this.title = title;
        }


        public void setDrawerTitle(CharSequence drawerTitle) {
            this.drawerTitle = drawerTitle;
        }


        public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
            if (this.actionBar == null) return;
            this.actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }
}
