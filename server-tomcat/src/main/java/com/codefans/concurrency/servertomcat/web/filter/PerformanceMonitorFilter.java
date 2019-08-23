package com.codefans.concurrency.servertomcat.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: codefans
 * @date: 2019-08-22 14:55
 */
public class PerformanceMonitorFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(PerformanceMonitorFilter.class);

    private String[] filterUriPrefix = new String[]{
        "/user/addAmount/",
        "/user/minusAmount/",
        ""
    };

    private static int addAmountCount;
    private static int minusAmountCount;

    private ExecutorService executorService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        int nThread = Runtime.getRuntime().availableProcessors();
        ThreadFactory threadFactory = new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "qps_task_index_" + index.getAndIncrement());
            }
        };
        executorService = Executors.newFixedThreadPool(nThread, threadFactory);


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String remoteAddr = req.getRemoteAddr();
        String remoteHost = req.getRemoteHost();

        String uri = req.getRequestURI();
        for(String prefix : filterUriPrefix) {
            if(uri.startsWith(prefix)) {
                if("/user/addAmount/".equalsIgnoreCase(prefix)) {
                    addAmountCount++;
                    log.info("filter uri=[" + uri + "], addAmountCount=[" + addAmountCount + "], remoteAddr=[" + remoteAddr + "], remoteHost=[" + remoteHost + "]");
                } else if("/user/minusAmount/".equalsIgnoreCase(prefix)) {
                    minusAmountCount++;
                    log.info("filter uri=[" + uri + "], minusAmountCount=[" + minusAmountCount + "], remoteAddr=[" + remoteAddr + "], remoteHost=[" + remoteHost + "]");
                }
                executorService.submit(new PerformanceTask(uri, System.currentTimeMillis()));
            }
        }

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        log.info("requestUrl:" + req.getRequestURI() + ",cost:" + (endTime - startTime));


    }

    @Override
    public void destroy() {

    }
}
