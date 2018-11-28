package com.codefans.concurrency.clientjava;

import com.codefans.concurrency.clientcommon.ScheduleCyclicBarrierTask;
import java.util.concurrent.*;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 16:59
 */
public class ScheduleThreadPoolClient {

    public static void main(String[] args) {
        ScheduleThreadPoolClient scheduleThreadPoolClient = new ScheduleThreadPoolClient();
        scheduleThreadPoolClient.startup();
    }

    public void startup() {

        int threadNums = Runtime.getRuntime().availableProcessors();
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(threadNums);
        threadPoolExecutor.scheduleAtFixedRate(new ScheduleCyclicBarrierTask(), 0,1, TimeUnit.SECONDS);

    }

}
