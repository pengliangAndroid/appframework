package com.wstro.app.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.wstro.app.common.BuildConfig;
import com.wstro.app.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianxiaoai on 2016/7/7.
 */
public class PermissionUtils {
    private static final String TAG = PermissionUtils.class.getSimpleName();

    private static boolean debug = BuildConfig.DEBUG;

    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;

    public static final int CODE_MULTI_PERMISSION = 100;

    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    public interface PermissionGrantListener {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);
    }


    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean isDebug) {
        PermissionUtils.debug = isDebug;
    }


    /**
     * 请求单个权限
     * @param activity
     * @param requestCode 权限请求码，例如：你使用照相权限，参数为PermissionUtils.CODE_CAMERA
     * @param grantListener
     */
    public static void requestPermission(final Activity activity, final int requestCode, PermissionGrantListener grantListener) {
        if (activity == null || grantListener == null) {
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            log("requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以在这个地方，低于23就什么都不做，
        // 个人建议try{}catch(){}单独处理，提示用户开启权限。
        //if (Build.VERSION.SDK_INT < 23) {
        //     return;
        //}

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            grantListener.onPermissionDenied(requestCode);
            log("checkSelfPermission RuntimeException:" + e.getMessage());
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //不显示说明框
           if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                //shouldShowRationale(activity, requestCode, requestPermission);
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }

        } else {
            grantListener.onPermissionGranted(requestCode);
        }
    }


    /**
     * 一次申请多个没有授权的权限
     * @param activity
     * @param requestCodes 权限请求码数组，例如：你使用照相权限和写入SDCard，参数为new int[]{PermissionUtils.CODE_CAMERA,PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE
     * @param grantListener
     */
    public static void requestMultiPermissions(final Activity activity,final int[] requestCodes, PermissionGrantListener grantListener) {
        if (activity == null || grantListener == null) {
            return;
        }

        if (requestCodes == null || requestCodes.length > requestPermissions.length) {
            log("requestMultiPermissions illegal requestCodes:");
            return;
        }

        final List<String> permissionsList = getNoGrantedPermissions(activity,requestCodes, false);
        final List<String> rationalePermissionsList = getNoGrantedPermissions(activity,requestCodes,true);

        if (permissionsList == null || rationalePermissionsList == null) {
            grantListener.onPermissionDenied(CODE_MULTI_PERMISSION);
            return;
        }

        log("requestMultiPermissions permissionsList:" + permissionsList.size() + ",rationalePermissionsList:" + rationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
        } else if (rationalePermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, rationalePermissionsList.toArray(new String[rationalePermissionsList.size()]),
                    CODE_MULTI_PERMISSION);

            /*showMessageOKCancel(activity, activity.getResources().getString(R.string.permission_denied_hint),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, rationalePermissionsList.toArray(new String[rationalePermissionsList.size()]),
                                    CODE_MULTI_PERMISSION);
                        }
                    });*/
        } else {
            grantListener.onPermissionGranted(CODE_MULTI_PERMISSION);
        }

    }

    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrantListener grantListener) {
        if (activity == null || grantListener == null) {
            return;
        }

        //Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            log("permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            //perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            log("all permission success");
            grantListener.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            grantListener.onPermissionDenied(CODE_MULTI_PERMISSION);
        }
    }


    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        String permissionsHint = activity.getResources().getString(R.string.permission_denied_hint);
        showMessageOKCancel(activity, permissionsHint, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.permission_ok, okListener)
                .setNegativeButton(R.string.permission_cancel, null)
                .create()
                .show();
    }

    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                @NonNull int[] grantResults, PermissionGrantListener grantListener) {
        if (activity == null || grantListener == null) {
            return;
        }

        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, grantListener);
            return;
        }

        log("onRequestPermissionsResult requestCode:" + requestCode + ",permissions:" + permissions.toString()
                + ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            log("onRequestPermissionsResult PERMISSION_GRANTED");
            grantListener.onPermissionGranted(requestCode);

        } else {
            //用户拒绝授权时，勾选了不再提醒,这个时候应该弹出提示要用户手动去权限设置页面打开
            log("onRequestPermissionsResult always PERMISSION NOT GRANTED");
            grantListener.onPermissionDenied(requestCode);

            //openPermissionSettingActivity(activity);
        }
    }

    public static void openPermissionSettingActivity(final Activity activity) {
        String permissionsHint = activity.getResources().getString(R.string.permission_setting_hint);
        showMessageOKCancel(activity, permissionsHint, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }


    private static ArrayList<String> getNoGrantedPermissions(Activity activity,int[] requestCodes, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < requestCodes.length; i++) {
            String requestPermission = requestPermissions[requestCodes[i]];

            int checkSelfPermission;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                log("RuntimeException:" + e.getMessage());
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {
                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                }

            }
        }

        return permissions;
    }

    /**
     * 打印授权过程信息
     * @param msg
     */
    private static void log(String msg){
        if(isDebug())
            Log.d(TAG,msg);
    }


    private static Activity getActivity(Object object){
        if(object instanceof Fragment){
            return ((Fragment)object).getActivity();
        } else if(object instanceof Activity){
            return (Activity) object;
        }
        return null;
    }
}