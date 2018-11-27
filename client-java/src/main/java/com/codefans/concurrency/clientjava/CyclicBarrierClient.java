package com.codefans.concurrency.clientjava;

import com.codefans.concurrency.clientcommon.CyclicBarrierTask;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 16:58
 */
public class CyclicBarrierClient {

    public static void main(String[] args) {
        CyclicBarrierClient cyclicBarrierClient = new CyclicBarrierClient();
        cyclicBarrierClient.startup();
    }

    public void startup() {

        new CyclicBarrierTask().startup();

    }

}
