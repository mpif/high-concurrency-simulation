package com.codefans.concurrency.clientcommon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: mpif
 * @Date: 2018-06-06 23:16
 */

public class DateUtils {

    private static final String PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_YYYYMMDDHHMMSS_SSS = "yyyy-MM-dd HH:mm:ss,SSS";
    private static final String PATTERN_YYYYMMDD = "yyyy-MM-dd";

    private static final ThreadLocal<DateFormat> yyyyMMddHHmmssDateFormat= new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        }
    };
    private static final ThreadLocal<DateFormat> yyyyMMddHHmmssSSSDateFormat= new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS_SSS);
        }
    };
    private static final ThreadLocal<DateFormat> yyyyMMddDateFormat= new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYYMMDD);
        }
    };

    public static String formatYYYYMMDDHHMMSS(Date date) {
        DateFormat df = yyyyMMddHHmmssDateFormat.get();
        return df.format(date);
    }

    public static String formatYYYYMMDDHHMMSS_SSS(Date date) {
        DateFormat df = yyyyMMddHHmmssSSSDateFormat.get();
        return df.format(date);
    }

    public static String formatYYYYMMDDHHMMSS_SSS(long timestamp) {
        return formatYYYYMMDDHHMMSS_SSS(new Date(timestamp));
    }

    public static String formatYYYYMMDDHHMMSS(long timestamp) {
        return formatYYYYMMDDHHMMSS(new Date(timestamp));
    }



    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    public static Date parseYYYYMMDDHHMMSS(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        return sdf.parse(dateStr);
    }

    /**
     * 计算endDate-beginDate间隔的天数
     * @param endDate
     * @param beginDate
     * @return
     */
    public static long minus(String endDate, String beginDate) throws ParseException {
        return minus(parseYYYYMMDDHHMMSS(endDate), parseYYYYMMDDHHMMSS(beginDate));
    }

    /**
     * 计算endDate-beginDate间隔的天数
     * @param endDate
     * @param beginDate
     * @return
     */
    public static long minus(Date endDate, Date beginDate) {
        return (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
    }

}
