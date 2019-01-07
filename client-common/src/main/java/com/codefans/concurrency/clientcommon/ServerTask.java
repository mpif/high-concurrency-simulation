package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.constant.CommonContants;
import com.codefans.concurrency.clientcommon.util.PropertyUtils;

import java.util.Date;

/**
 * @author: codefans
 * @date: 2018-11-26 18:43
 */
public class ServerTask implements Runnable {

    private HttpAsynClientRequest httpAsynClientRequest;

    public ServerTask(HttpAsynClientRequest httpAsynClientRequest) {
        this.httpAsynClientRequest = httpAsynClientRequest;
    }

    @Override
    public void run() {

        System.out.println("server task running, time:" + new Date());
//        String uri = "http://localhost:8083/user/minusAmount/version/appkey/1/1?xmlParam=fdfjdlfd333444";
        String uri = PropertyUtils.getProperty(CommonContants.REQUEST_URL_KEY);
        this.httpAsynClientRequest.get(uri);

    }
}
