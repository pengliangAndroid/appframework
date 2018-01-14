package com.wstro.app.common.mvp;

import com.wstro.app.common.base.BaseActivity;

/**
 * @author pengl
 */
public class BaseActPresenter<T extends MvpView> extends BasePresenter<T> {
    protected BaseActivity baseActivity;

    @Override
    public void attachView(T mvpView) {
        super.attachView(mvpView);

        baseActivity = (BaseActivity) mvpView;
    }

    @Override
    public void detachView() {
        super.detachView();

        baseActivity = null;
    }


}
