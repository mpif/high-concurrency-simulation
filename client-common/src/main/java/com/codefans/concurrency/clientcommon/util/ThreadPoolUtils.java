package com.codefans.concurrency.clientcommon.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 17:31
 */
public class ThreadPoolUtils {

    public static ExecutorService getExecutorService(final String threadPoolName) {

        int maxTaskCount = Integer.MAX_VALUE;

        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maximumPoolSize = corePoolSize * 2;
        long keepAliveTime = 3L;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(maxTaskCount);
        ThreadFactory namedThreadFactory = new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                String threadName = threadPoolName + index.getAndIncrement();
                //之前new线程Thread的时候没传Runnable对象r, 如下面这条语句所示, 导致任务提交线程池后不执行
//                    return new Thread(threadName);
                return new Thread(r, threadName);
            }
        };
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(threadPoolName + ", task [" + r.toString() + "] reject");
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue,
                namedThreadFactory,
                rejectedExecutionHandler
        );
        return executorService;
    }

}
