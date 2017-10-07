package com.wstro.app.common.mvp;

import com.wstro.app.common.base.BaseActivity;

/**
 * ClassName: BaseActPresenter
 * Function:
 * Date:     2017/9/7 0007 17:07
 *
 * @author Administrator
 * @see
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
