package com.wstro.app.common.utils;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wstro.app.common.R;

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context,String message) {
        if(toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    public void showToast(Context context,int messageId) {
        showToast(context,context.getResources().getString(messageId));
    }


    public static void showCustomToast(Activity context,int msgId) {
        showCustomToast(context,context.getResources().getString(msgId));
    }

    public static void showCustomToast(Activity context,String message) {
        showCustomToast(context,message, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(Activity context, CharSequence text, int duration) {
        if(toast == null) {
            //toast = Toast.makeText(this, text, duration);
            toast = new Toast(context);

            View view = context.getLayoutInflater().inflate(R.layout.toast_custom_view, null);
            TextView textView = (TextView) view
                    .findViewById(R.id.tv_msg);
            textView.setText(text);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setDuration(duration);
        } else {
            TextView textView = (TextView) toast.getView()
                    .findViewById(R.id.tv_msg);
            textView.setText(text);
        }

        toast.show();
    }

    public static void cancelToast() {
        if(toast != null) {
            toast.cancel();
        }
    }
}
