package com.wstro.app.common.mvp;

import com.wstro.app.common.base.BaseActivity;
import com.wstro.app.common.base.BaseFragment;

/**
 * @author pengl
 */
public class BaseFrgPresenter<T extends MvpView> extends BasePresenter<T> {
    protected BaseFragment baseFragment;

    @Override
    public void attachView(T mvpView) {
        super.attachView(mvpView);

        baseFragment = (BaseFragment) mvpView;

    }

    @Override
    public void detachView() {
        super.detachView();

        baseFragment = null;
    }

    public BaseActivity getBaseActivity(){
        return baseFragment.getHoldingActivity();
    }

}
