package com.wstro.app.common.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * @author pengl
 */
public class ClearTextWatcher implements TextWatcher {
    View view;

    public ClearTextWatcher(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}