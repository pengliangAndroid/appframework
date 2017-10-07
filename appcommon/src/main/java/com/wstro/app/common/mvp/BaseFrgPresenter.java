package com.wstro.app.common.mvp;

import com.wstro.app.common.base.BaseActivity;
import com.wstro.app.common.base.BaseFragment;

/**
 * ClassName: BaseActPresenter
 * Function:
 * Date:     2017/9/7 0007 17:07
 *
 * @author Administrator
 * @see
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
