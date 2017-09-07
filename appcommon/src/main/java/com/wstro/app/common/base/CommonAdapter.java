package com.wstro.app.common.base;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public abstract class CommonAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {
    public CommonAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, T item) {
        convertViewItem(baseViewHolder,item);
    }

    public abstract void convertViewItem(BaseViewHolder baseViewHolder, T item);
}
