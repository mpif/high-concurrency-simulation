package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.util.NumberUtils;

import java.util.Date;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-28 09:17
 */
public class ContinuousRequestTask implements Runnable {

    private static boolean running;

    private static boolean taskRunning;

    public ContinuousRequestTask() {
    }

    @Override
    public void run() {

        running = true;
        taskRunning = true;

        HttpAsynClientRequest httpAsynClientRequest = new HttpAsynClientRequest();

        while(running) {

            if(taskRunning) {
//                System.out.println("server task running, time:" + new Date());
                String uri = "http://localhost:8083/template/callback/version/appkey?xmlParam=fdfjdlfd333444";
                httpAsynClientRequest.get(uri);
            } else {
                try {
                    int randomInt = NumberUtils.getRandomSleepMillTimes();
                    Thread.sleep(randomInt);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static void startTask() {
        taskRunning = true;
    }

    public static void stopTask() {
        taskRunning = false;
    }

    public static void shutdown() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }







}
