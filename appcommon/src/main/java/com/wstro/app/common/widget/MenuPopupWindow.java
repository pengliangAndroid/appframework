package com.wstro.app.common.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * @author pengl
 */
public abstract class MenuPopupWindow extends PopupWindow {

    public MenuPopupWindow(Context context, int layoutId) {
        View view = View.inflate(context, layoutId, null);


        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);

        convertView(view);
    }

    protected abstract void convertView(View view);


}
