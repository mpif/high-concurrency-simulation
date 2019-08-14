package com.codefans.concurrency.clientcommon.util;

/**
 * @author: codefans
 * @date: 2019-08-13 10:38
 */
public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
