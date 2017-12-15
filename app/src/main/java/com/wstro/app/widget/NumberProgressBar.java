package com.wstro.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * ClassName: NumberProgressBar
 * Function:
 * Date:     2017/12/15 0015 14:58
 *
 * @author pengl
 * @see
 */
public class NumberProgressBar extends FrameLayout {
    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
