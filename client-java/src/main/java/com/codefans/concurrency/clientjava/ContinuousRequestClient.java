package com.codefans.concurrency.clientjava;

import com.codefans.concurrency.clientcommon.HttpAsynClientRequest;

import java.util.Date;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 16:57
 */
public class ContinuousRequestClient {

    private boolean running = true;

    public static void main(String[] args) {
        ContinuousRequestClient requestClient = new ContinuousRequestClient();
        requestClient.startup();
    }

    public void startup() {

        HttpAsynClientRequest httpAsynClientRequest = new HttpAsynClientRequest();

        while(running) {

            System.out.println("server task running, time:" + new Date());
            String uri = "http://localhost:8083/template/callback/version/appkey?xmlParam=fdfjdlfd333444";
            httpAsynClientRequest.get(uri);

        }

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }






}
