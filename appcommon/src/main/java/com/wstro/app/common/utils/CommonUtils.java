package com.wstro.app.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.util.Collection;

/**
 * Created by pengl on 2016/5/20.
 */
public class CommonUtils {
    public static boolean isEmptyArray(Collection collection){
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str) || str.equals("null");
    }

    /**
     * 二位数的格式化，若只有一位，前面添个0
     * @param value
     * @return
     */
    public static String formatOct(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return Integer.toString(value);
    }

    public static String format(String str){
        if(isEmpty(str)){
            return "";
        }

        return str;
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionCode + "";
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFileRootPath(Context context){
        return Environment.getExternalStorageDirectory() + "/Wstro/" + context.getPackageName();
    }

    public static String getCacheFileRootPath(Context context){
        return getFileRootPath(context) +"/cache/";
    }

    public static String getTempFileRootPath(Context context){
        return getFileRootPath(context) +"/tmp/";
    }

    public static String getLogFilePath(Context context){
        return getFileRootPath(context) +"/logs/";
    }

    /**
     * 根據文件的uri得到本地路徑
     * @param fileUrl
     * @param context
     * @return
     */
    public static String getRealPath(Uri fileUrl, Context context){
        String fileName = null;
        Uri filePathUri = fileUrl;
        if(fileUrl!= null){
            if (fileUrl.getScheme().toString().compareTo("content")==0)           //content://开头的uri
            {
                Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                if (cursor != null && cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    fileName = cursor.getString(column_index);          //取出文件路径

                    cursor.close();
                }
            }else if(fileUrl.getScheme().compareTo("file")==0)         //file:///开头的uri
            {
                fileName = filePathUri.toString();
                fileName = filePathUri.toString().replace("file://", "");

            }
        }
        return fileName;
    }

}
