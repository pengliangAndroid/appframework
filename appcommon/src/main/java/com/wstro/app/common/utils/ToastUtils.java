package com.wstro.app.common.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * ToastUtils
 * 
 *
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context,String text) {
        showToast(context,text,Toast.LENGTH_SHORT);
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showToast(Context context,CharSequence text,int duration){
        if(mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
