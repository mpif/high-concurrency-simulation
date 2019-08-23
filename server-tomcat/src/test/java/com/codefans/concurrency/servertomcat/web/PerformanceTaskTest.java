package com.codefans.concurrency.servertomcat.web;

import com.codefans.concurrency.servertomcat.web.filter.PerformanceTask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: codefans
 * @date: 2019-08-23 14:03
 */
public class PerformanceTaskTest {

    @Test
    public void performanceTest() {


        int nThread = Runtime.getRuntime().availableProcessors();
        ThreadFactory threadFactory = new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "qps_task_index_" + index.getAndIncrement());
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(nThread, threadFactory);

        int taskNum = 10000;
        String uri = "/user/getDetail";
        List<Future> futureList = new ArrayList<Future>();

        for(int i = 0; i < taskNum; i ++) {
            Future future = executorService.submit(new PerformanceTask(uri, System.currentTimeMillis()));
            futureList.add(future);
        }


        try {
            for(Future future : futureList) {
                future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        PerformanceTask.print();

    }



}
