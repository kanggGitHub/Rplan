package com.cetc.plan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author wyb
 */
public class TimeUtil {

    private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    private static final String SDF_SDFS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间转字符串
     *
     * @param date      时间
     * @param formatStr 格式化字符串
     * @return 时间字符串
     */
    public static String format(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_SDFS);
        if (formatStr != null && !"".equals(formatStr)) {
            sdf = new SimpleDateFormat(formatStr);
        }

        return sdf.format(date);
    }

    /**
     * 时间字符串转指定格式字符串
     *
     * @param dateStr      时间字符串
     * @param formatBefore 前格式化字符串
     * @param formatAfter  后格式化字符串
     * @return 时间字符串
     */
    public static String formatToStr(String dateStr, String formatBefore, String formatAfter) {
        return format(parse(dateStr, formatBefore), formatAfter);
    }

    /**
     * 字符串转时间
     *
     * @param dateStr   时间字符串
     * @param formatStr 转换字符串
     * @return 时间对象
     */
    public static Date parse(String dateStr, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_SDFS);
        if (formatStr != null && !"".equals(formatStr)) {
            sdf = new SimpleDateFormat(formatStr);
        }

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(dateStr + "  ParseException", e);
        }

        return date;
    }

    /**
     * 对时间字符串进行数据加毫秒处理，并返回处理后的时间字符串
     *
     * @param dateStr   时间字符串
     * @param addValue  添加的毫秒值
     * @param formatStr 转换字符串
     * @return 时间对象
     */
    public static String timeStrAddValue(String dateStr, int addValue, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_SDFS);
        if (formatStr != null && !"".equals(formatStr)) {
            sdf = new SimpleDateFormat(formatStr);
        }
        String result = null;
        try {
            Date date = sdf.parse(dateStr);
            long newTime = date.getTime() + addValue;
            result = sdf.format(new Date(newTime));
        } catch (ParseException e) {
            logger.error(dateStr + "  ParseException", e);
        }

        return result;
    }

    /**
     * 时间字符串大小比较
     *
     * @param timeStr1  时间字符串1
     * @param timeStr2  时间字符串2
     * @param formatStr 格式化字符串
     * @return 结果值
     */
    public static int compare(String timeStr1, String timeStr2, String formatStr) {
        Date t1 = parse(timeStr1, formatStr);
        Date t2 = parse(timeStr2, formatStr);

        if (t1.before(t2)) {
            return -1;
        } else if (t1.after(t2)) {
            return 1;
        }
        return 0;
    }

    public static List<String[]> splitTimeByDay(String kssj, String jssj, String formatStr) {
        List<String[]> result = new ArrayList<>();
        String formatDay = "yyyy-MM-dd";

        Calendar min = Calendar.getInstance();
        min.setTime(parse(kssj.substring(0, 10), formatDay));

        Calendar max = Calendar.getInstance();
        max.setTime(parse(jssj.substring(0, 10), formatDay));
        Calendar curr = (Calendar) min.clone();
        while (curr.before(max) || curr.equals(max)) {
            //时间开始
            String ks;
            if (curr.equals(min)) {
                ks = kssj;
            } else {
                ks = format(curr.getTime(), formatDay) + " 00:00:00";
            }

            //时间结束
            String js;
            if (curr.equals(max)) {
                js = jssj;
            } else {
                js = format(curr.getTime(), formatDay) + " 23:59:59";
            }
            result.add(new String[]{ks, js});

            curr.add(Calendar.DATE, 1);
        }

        return result;
    }


    public static void main(String[] args) {

        int compare = compare("2018-01-01 12:12:13", "2018-01-01 12:12:12", null);
        System.out.println(compare);

        List<String[]> list = timeSplite("2019-01-01 08:00:00", "2019-01-10 11:00:00", 3);
        for (String[] arr : list) {
            System.out.println("kssj:" + arr[0] + ", jssj:" + arr[1]);
        }
    }

    public static List<String[]> timeSplite(String kssjStr, String jssjStr, int jg) {
        List<String[]> result = new ArrayList<>();
        Date kssj = parse(kssjStr, null);
        Date jssj = parse(jssjStr, null);
        long jgTime = 1000L * 60 * 60 * 24 * jg;

        Date curr = parse(format(kssj, "yyyy-MM-dd 00:00:00"), null);
        curr.setTime(curr.getTime() + jgTime);

        if (jssj.getTime() > curr.getTime()) {
            //添加时间
            result.add(new String[]{kssjStr, format(new Date(curr.getTime() - 1), "yyyy-MM-dd 23:59:59")});
            kssj.setTime(curr.getTime());
            curr.setTime(curr.getTime() + jgTime);
        } else {
            result.add(new String[]{kssjStr, jssjStr});
            return result;
        }

        while (jssj.getTime() > curr.getTime()) {
            //添加时间
            result.add(new String[]{format(kssj, "yyyy-MM-dd 00:00:00"), format(new Date(curr.getTime() - 1), "yyyy-MM-dd 23:59:59")});
            kssj.setTime(curr.getTime());
            curr.setTime(curr.getTime() + jgTime);
        }

        //添加时间
        result.add(new String[]{format(kssj, "yyyy-MM-dd 00:00:00"), jssjStr});

        return result;
    }

}
