package com.codefans.concurrency.clientjava;

import com.codefans.concurrency.clientcommon.HttpAsynClientRequest;
import com.codefans.concurrency.clientcommon.ServerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-23 14:15
 */
public class ClientJavaMain {

    private boolean start = true;

    public static void main(String[] args) {


        ClientJavaMain clientJavaMain = new ClientJavaMain();
        clientJavaMain.startup();

    }

    public void startup() {

        try {
            /**
             * 创建固定容量大小的缓冲池
             */

//        int maxTaskCount = Integer.MAX_VALUE;
            int maxTaskCount = 10;

            int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
            int maximumPoolSize = 100;
            long keepAliveTime = 5L;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(maxTaskCount);
            ThreadFactory namedThreadFactory = new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    String threadName = "clientThread_" + index.getAndIncrement();
                    return new Thread(threadName);
                }
            };
            RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    System.out.println("task [" + r.toString() + "] reject");
                }
            };

            ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue,
                namedThreadFactory,
                rejectedExecutionHandler
            );

            HttpAsynClientRequest httpAsynClientRequest = new HttpAsynClientRequest();

            List<Future> futureList = new ArrayList<Future>();

            int runningTaskCount = 0;
            while(start) {
                runningTaskCount++;
                System.out.println("task No." + runningTaskCount);
    //            executorService.submit(new ServerTask(httpAsynClientRequest));
                Future future = executorService.submit(new ServerTask(httpAsynClientRequest));
                futureList.add(future);

                if(runningTaskCount == maxTaskCount) {
                    start = false;
                    System.out.println("stop ......");
                }

            }

            for(int i = 0; i < futureList.size(); i ++) {
                Future future = futureList.get(i);
                System.out.println(future.get());
            }






        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
