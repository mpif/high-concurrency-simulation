package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.util.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-26 17:40
 */
public class CyclicBarrierTask {

    private int barrierNum;
    private boolean running = true;

    public CyclicBarrierTask() {

    }

    public void startup() {

        barrierNum = Runtime.getRuntime().availableProcessors();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(barrierNum);

        ExecutorService executorService = ThreadPoolUtils.getExecutorService("CyclicBarrierTask");

        HttpAsynClientRequest httpAsynClientRequest = new HttpAsynClientRequest();

        List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(barrierNum);

        int runningTaskCount = 0;
        Future<Boolean> resultFuture = null;

        while(running) {
            runningTaskCount++;
            resultFuture = executorService.submit(new CyclicBarrierRunnable(httpAsynClientRequest, cyclicBarrier));
            futureList.add(resultFuture);

            if(runningTaskCount == barrierNum) {
                try {
                    boolean isSuccess = true;
                    Future<Boolean> future = null;
                    for(int i = 0; i < futureList.size(); i ++) {
                        future = futureList.get(i);
                        try {
                            isSuccess = isSuccess && future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(isSuccess) {
                        futureList.clear();
                        cyclicBarrier.reset();
                        runningTaskCount = 0;
                    }

                    int randomInt = getRandomSleepMillTimes();
                    System.out.println("sleep:" + randomInt);
                    Thread.sleep(randomInt);

//                    running = false;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }


    }

    /**
     * 得到0到9的随机数
     * @return
     */
    public int getRandomSleepMillTimes(){
        int max=5000;
        int min=1000;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    public int getBarrierNum() {
        return barrierNum;
    }

    public void setBarrierNum(int barrierNum) {
        this.barrierNum = barrierNum;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
