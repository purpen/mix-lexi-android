package com.basemodule.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lilin on 2017/7/25.
 */

public class DateUtil {

    public static final String PATTERN = "yyyy-MM-dd";
    public static final String PATTERN_DOT = "yyyy.MM.dd";

    private int interval = 0;

    /**
     * 某日期的前几天或者后几天
     * 正表前几天,负表后几天
     *
     * @return
     */
    public static String getSpecifiedDayBefore(Date date, int interval,String pattern) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - interval);
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    /**
     * 获取某日期的前几月或者后几月
     * 正表示前几月,负表示后几月
     *
     * @param date
     * @param interval
     * @return
     */
    public static String getSpecifiedMonthBefore(Date date, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month - interval);
        return new SimpleDateFormat(PATTERN).format(c.getTime());
    }

    /**
     * 获取某日期的前几月或者后几月
     * 正表示前几月,负表示后几月
     *
     * @param dateStr
     * @param interval
     * @return
     */
    public static String getSpecifiedMonthBefore(String dateStr, int interval) {
        Calendar c = Calendar.getInstance();
        Date date;
        try {
            date = new SimpleDateFormat(PATTERN).parse(dateStr);
            c.setTime(date);
            int month = c.get(Calendar.MONTH);
            c.set(Calendar.MONTH, month - interval);
            return new SimpleDateFormat(PATTERN).format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据时间戳获得日期
     */
    public static String getDateByTimestamp(long timestamp, String pattern) {
        timestamp = timestamp * 1000;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        c.setTime(new Date(timestamp));
        return simpleDateFormat.format(c.getTime());
    }

    /**
     * 根据时间戳获得日期
     */
    public static String getDateByTimestamp(long timestamp) {
        return getDateByTimestamp(timestamp, PATTERN);
    }

    /**
     * 返回中国格式日期
     */
    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN, Locale.SIMPLIFIED_CHINESE);
        return format.format(date);
    }

    public static String getDateString(Date date,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        return format.format(date);
    }

    /**
     * 获取时间间隔
     *
     * @param millisecond
     * @return
     */
    public static String getSpaceTime(Long millisecond) {
        Calendar calendar = Calendar.getInstance();
        Long currentMillisecond = calendar.getTimeInMillis();

        //间隔秒
        Long spaceSecond = (currentMillisecond - millisecond) / 1000;

        //5分钟之内
        if (spaceSecond <= 60 * 5) {
            return "刚刚";
        }
        //一小时之内
        else if (spaceSecond / 60 > 5 && spaceSecond / 60 < 60) {
            return spaceSecond / 60 + "分钟前";
        }
        //一天之内
        else if (spaceSecond / (60 * 60) > 0 && spaceSecond / (60 * 60) < 24) {
            return spaceSecond / (60 * 60) + "小时前";
        } else if (spaceSecond / (60 * 60 * 24) >= 1 && spaceSecond / (60 * 60 * 24) < 2) {
            return "昨天";
        }
        //3天之内
        else if (spaceSecond / (60 * 60 * 24) >= 2 && spaceSecond / (60 * 60 * 24) < 3) {
            return spaceSecond / (60 * 60 * 24) + "天前";
        } else { //超过3天直接显示日期
            return getDateByTimestamp(millisecond/1000);
        }
    }
}
