package com.codefans.concurrency.servercommon.service;

import com.codefans.concurrency.common.util.DateUtils;
import org.junit.Test;

/**
 * @author: codefans
 * @date: 2019-08-22 15:21
 */
public class TimeTest {

    @Test
    public void test() {

        long time = System.currentTimeMillis();
        System.out.println(time);
        System.out.println(time/1000);

        int num = 1000;
        long timestamp = 0L;
        for(int i = 0; i < num; i ++) {
            timestamp = System.currentTimeMillis();
            System.out.println(DateUtils.formatYYYYMMDDHHMMSS_SSS(timestamp));
        }

    }


}
