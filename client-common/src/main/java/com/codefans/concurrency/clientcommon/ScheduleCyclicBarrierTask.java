package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.util.NumberUtils;
import com.codefans.concurrency.clientcommon.util.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-28 10:46
 */
public class ScheduleCyclicBarrierTask implements Runnable {

    int barrierNum;

    @Override
    public void run() {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        barrierNum = availableProcessors;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(barrierNum);

        ExecutorService executorService = ThreadPoolUtils.getExecutorService("CyclicBarrierTask");

        HttpAsynClientRequest httpAsynClientRequest = new HttpAsynClientRequest();

        List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(barrierNum);

        int runningTaskCount = 0;
        Future<Boolean> resultFuture = null;

        int threadNums = barrierNum * 10;

        for(int j = 1; j <= threadNums; j ++) {

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


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }


}
