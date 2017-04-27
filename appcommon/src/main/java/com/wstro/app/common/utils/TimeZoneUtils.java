package com.wstro.app.common.utils;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pengl on 2016/1/12.
 */
public class TimeZoneUtils {

    /**
     * 获取本地默认时区id
     * @return string 本地时区id
     */
    public static String getLocalTimeId()
    {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        String sourceId = defaultTimeZone.getID();
        return sourceId;
    }

    /**
     * @param date  当前时区的日期
     * @param targetId 要转换成目标时区id
     * @return 返回时分字符串
     */
    public static String timeConvert(Date date,
                                     String targetId)
    {
        //时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //根据入参原时区id，获取对应的timezone对象
        TimeZone sourceTimeZone = TimeZone.getTimeZone(getLocalTimeId());
        //设置SimpleDateFormat时区为原时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成原时区对应的date对象
        df.setTimeZone(sourceTimeZone);

        //开始转化时区：根据目标时区id设置目标TimeZone
        TimeZone targetTimeZone = TimeZone.getTimeZone(targetId);
        //设置SimpleDateFormat时区为目标时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成目标时区对应的date对象
        df.setTimeZone(targetTimeZone);
        //得到目标时间字符串
        String targetTime = df.format(date);
        return targetTime;
    }

    /**
     * 获取当前东八区的网络时间值，单位为ms，得到此时当前系统时区时间（根据此时东八区的时间），
     * 即而得到本系统当前时间和网络时间的差值
     * @return 返回当前系统时区的差值
     */
    public static long getNetTimeMillis() {
        URL url;
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect(); // 发出连接
            long ld = uc.getDate(); // 取得网站日期时间

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //得到东八区的当前时间
            String date = TimeZoneUtils.timeConvert(new Date(ld), "Asia/Shanghai");
            df.setTimeZone(TimeZone.getDefault());
            long currentTime = df.parse(date).getTime();
            return currentTime;

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
