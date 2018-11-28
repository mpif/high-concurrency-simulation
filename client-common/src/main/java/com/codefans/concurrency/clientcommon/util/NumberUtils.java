package com.codefans.concurrency.clientcommon.util;

import java.util.Random;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-28 10:31
 */
public class NumberUtils {

    /**
     * 得到1000到5000的随机数
     * @return
     */
    public static int getRandomSleepMillTimes(){
        int max=5000;
        int min=1000;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

}
