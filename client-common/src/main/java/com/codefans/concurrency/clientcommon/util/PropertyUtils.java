package com.codefans.concurrency.clientcommon.util;

import java.io.*;
import java.util.Properties;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 17:18
 */
public class PropertyUtils {

    private static Properties properties;

    static {
        try {

            InputStream in = PropertyUtils.class.getClass().getResourceAsStream("/config.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");

            properties = new Properties();
            properties.load(inputStreamReader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }






}
