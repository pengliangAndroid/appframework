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
            mToast = null;
        }
    }

    public static void showToast(Context context,CharSequence text,int duration){
        if(mToast == null) {
            mToast = Toast.makeText(context, text, duration);
            //Toast toast = Toast.makeText(getContext(),message,Toast.LENGTH_SHORT);
            //toast.setGravity(Gravity.CENTER,0,0);
            //toast.show();
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
