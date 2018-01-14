package com.wstro.app.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载Fragment
 * 不会在Fragment创建时就就加载数据，1而是只有用户可见时去加载onLoad()，从而不影响App性能
 * @author pengl
 */
public abstract class BaseLazyFragment extends BaseFragment {
    //是否用户可见
    protected boolean isVisible = false;

    //是否加载过数据
    protected boolean isLoadData = false;

    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()){
            isVisible = true;
            //只有用户可见时进行加载
            lazyLoad();
        }else{
            isVisible = false;
            onInVisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }


    protected abstract void loadData();

    public void onInVisible(){}

    protected void lazyLoad(){
        if (!isPrepared || !isVisible || isLoadData) {
            return;
        }

        loadData();
        isLoadData = true;
    }

}
