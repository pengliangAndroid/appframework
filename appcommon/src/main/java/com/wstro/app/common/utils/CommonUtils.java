package com.wstro.app.common.utils;

import android.text.TextUtils;

import java.util.Collection;


public class CommonUtils {

    /**
     * 是否空集合
     * @param collection
     * @return
     */
    public static boolean isEmptyArray(Collection collection){
        return collection == null || collection.size() == 0;
    }

    /**
     * 是否空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str) || str.equals("null");
    }

    /**
     * 高亮部分文字
     * @param html "<font color='#FF0000'>%s</font>"
     * @param name
     * @param text
     * @return
     */
    public static String highlightText(String html,String name,String text){
        if(TextUtils.isEmpty(text)){
            return name;
        }

        if(name.contains(text)){
            String replaceHtml = html.replace("%s", text);
            name = name.replaceAll(text, replaceHtml);
        }

        return name;
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


    /**
     * 字符串格式化
     * @param str
     * @return
     */
    public static String format(String str){
        if(isEmpty(str)){
            return "";
        }

        return str;
    }


}
