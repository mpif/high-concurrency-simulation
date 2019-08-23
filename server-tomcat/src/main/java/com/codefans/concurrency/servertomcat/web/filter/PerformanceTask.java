package com.codefans.concurrency.servertomcat.web.filter;

import com.codefans.concurrency.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: codefans
 * @date: 2019-08-22 15:50
 */
public class PerformanceTask implements Runnable {

    private static Logger log = LoggerFactory.getLogger(PerformanceTask.class);

    private static volatile long timestamp;
    private static volatile String currentTime;

    private static volatile String uri;

    public static volatile ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> uriTimeMap = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, Integer> timeCountMap = new ConcurrentHashMap<>();

    private static Lock lock = new ReentrantLock();

    public PerformanceTask(String uri, long timestamp) {
        this.uri = uri;
        this.timestamp = timestamp;
    }

    @Override
    public void run() {

        lock.lock();

        try {

            currentTime = DateUtils.formatYYYYMMDDHHMMSS(timestamp);

            if(uriTimeMap.containsKey(uri)) {

                ConcurrentLinkedQueue<String> timeQueue = uriTimeMap.get(uri);
                if(timeQueue.contains(currentTime)) {
                    Integer count = timeCountMap.get(uri + currentTime);
                    count = count == null ? 0 : count;
                    timeCountMap.put(uri + currentTime, count + 1);
//                    log.info("update count:" + count+1);
                } else {
                    if (timeQueue.size() >= 60) {
                        print();
                        String oldTime = timeQueue.poll();
                        timeCountMap.remove(uri + oldTime);
//                        log.info("remove time:" + oldTime);
                    }
                    timeQueue.offer(currentTime);
                    timeCountMap.put(uri + currentTime, 1);
                }

            } else {

//                log.info("add new uri, add new count");
                ConcurrentLinkedQueue<String> timeQueue = new ConcurrentLinkedQueue<String>();
                timeQueue.offer(currentTime);
                uriTimeMap.put(uri, timeQueue);

                timeCountMap.put(uri+currentTime, 1);

            }

        } finally {
            lock.unlock();
        }

    }

    public static void print() {

        Iterator<String> iter = uriTimeMap.keySet().iterator();
        String key = "";
        ConcurrentLinkedQueue<String> timeList = null;

        while(iter.hasNext()) {
            key = iter.next();
            timeList = uriTimeMap.get(key);
            for(String time : timeList) {
//                System.out.println("uri:" + key + ", time:" + time + ", count:" + timeCountMap.get(key+time));
                log.info("uri:" + key + ", time:" + time + ", count:" + timeCountMap.get(key+time));
            }
        }

    }

}
