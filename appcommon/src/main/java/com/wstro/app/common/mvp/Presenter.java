package com.wstro.app.common.mvp;

/**
 * @author pengl
 */
public interface Presenter<T extends MvpView> {
    void attachView(T view);

    void detachView();
}
