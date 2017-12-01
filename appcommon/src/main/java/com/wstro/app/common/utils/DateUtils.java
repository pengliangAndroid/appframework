/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.wstro.app.common.utils;

import android.annotation.SuppressLint;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Description：DateUtils
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static String[] WEEK = new String[]{"天", "一", "二", "三", "四", "五", "六"};

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;


    /**
     * String 转换 Date
     *
     * @param str    str
     * @param format format
     * @return Date
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    /**
     * String 转 String
     *
     * @param str          str
     * @param format       format
     * @param secondFormat secondFormat
     * @return String
     */
    public static String string2String(String str, String format, String secondFormat) {
        try {
            return date2String(new SimpleDateFormat(format).parse(str).getTime(), secondFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }


    /**
     * Date（long） 转换 String
     *
     * @param time   time
     * @param format format
     * @return String
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }


    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date date
     * @return long
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取目标时间和当前时间之间的差距
     *
     * @param date date
     * @return String
     */
    public static String getTimestampString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();
        if (splitTime < (30 * ONE_DAY)) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚";
            }
            if (splitTime < ONE_HOUR) {
                return String.format("%d分钟前", splitTime / ONE_MINUTE);
            }

            if (splitTime < ONE_DAY) {
                return String.format("%d小时前", splitTime / ONE_HOUR);
            }

            return String.format("%d天前", splitTime / ONE_DAY);
        }
        String result;
        result = "M月d日 HH:mm";
        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
    }


    /**
     * 24小时制 转换 12小时制
     *
     * @param time time
     * @return String
     */
    public static String time24To12(String time) {
        String str[] = time.split(":");
        int h = Integer.valueOf(str[0]);
        int m = Integer.valueOf(str[1]);
        String sx;
        if (h < 1) {
            h = 12;
            sx = "上午";
        } else if (h < 12) {
            sx = "上午";
        } else if (h < 13) {
            sx = "下午";
        } else {
            sx = "下午";
            h -= 12;
        }
        return String.format("%d:%02d%s", h, m, sx);
    }


    /**
     * Date 转换 HH
     *
     * @param date date
     * @return String
     */
    public static String date2HH(Date date) {
        return new SimpleDateFormat("HH").format(date);
    }


    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }


    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }


    /**
     * Date 转换 MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2MMdd(Date date) {
        return new SimpleDateFormat("MM.dd").format(date);
    }


    /**
     * Date 转换 yyyy.MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }


    /**
     * Date 转换 MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2MMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }


    /**
     * Date 转换 yyyy年MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("yyyy年MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }


    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return  -1;
        }
    }
    /**
     * 返回当前月份1号位于周几
     * @param year
     * 		年份
     * @param month
     * 		月份，传入系统获取的，不需要正常的
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


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
            String date = timeConvert(new Date(ld), "Asia/Shanghai");
            df.setTimeZone(TimeZone.getDefault());
            long currentTime = df.parse(date).getTime();
            return currentTime;

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }



    /**
     * 得到前面的修饰符
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();

        String preDate = null;
        if (splitTime < (3 * ONE_DAY)) {
            if (splitTime > 2 * ONE_DAY) {
                preDate = date2Week(date) + " ";
            } else if(splitTime > ONE_DAY){
                preDate = "昨天 ";
            } else{
                preDate = "";
            }

            return preDate + date2HHmm(date);
        }else{
            preDate = date2MMdd(date) ;
            return preDate;
        }
    }

    public static String getHHMMDateString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();

        String preDate = null;
        if (splitTime < (3 * ONE_DAY)) {

            if(!isSameDay(date,curDate)){
                preDate = date2Week(date) + " ";
            }else{
                preDate = "";
            }
           /* if (splitTime > 2 * ONE_DAY) {
                preDate = date2Week(date) + " ";
            } else if(splitTime > ONE_DAY){
                preDate = "昨天 ";
            } else{
                preDate = "";
            }*/
            return preDate + date2HHmm(date);
        }else{
            if(!isSameYear(date,curDate)){
                preDate = date2yyyyMMdd(date);
            }else{
                preDate = date2MMdd(date) ;
            }

            return preDate + " " + date2HHmm(date);
        }
    }

    private static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        /*boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);*/
        boolean isSameDate = cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    private static boolean isSameYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);

        return isSameYear;
    }

    public static String getDateString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateString(dateFormat.parse(dateString));
    }



    public static String date2Week(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("星期").format(date) + WEEK[dayOfWeek - 1];
    }


}