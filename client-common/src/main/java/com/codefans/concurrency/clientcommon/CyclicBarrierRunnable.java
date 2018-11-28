package com.codefans.concurrency.clientcommon;

import com.codefans.concurrency.clientcommon.constant.CommonContants;
import com.codefans.concurrency.clientcommon.util.PropertyUtils;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 17:14
 */
public class CyclicBarrierRunnable implements Callable<Boolean> {

    private HttpAsynClientRequest httpAsynClientRequest;
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierRunnable(HttpAsynClientRequest httpAsynClientRequest, CyclicBarrier cyclicBarrier) {
        this.httpAsynClientRequest = httpAsynClientRequest;
        this.cyclicBarrier = cyclicBarrier;
    }


    @Override
    public Boolean call() {

        try {

//            cyclicBarrier.await(3000, TimeUnit.MILLISECONDS);
            cyclicBarrier.await();

            System.out.println("CyclicBarrierRunnable task running, time:" + new Date());
//        String uri = "http://localhost:8083/template/callback/version/appkey?xmlParam=fdfjdlfd333444";
            String uri = PropertyUtils.getProperty(CommonContants.REQUEST_URL_KEY);
            this.httpAsynClientRequest.get(uri);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }
}
