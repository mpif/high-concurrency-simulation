package com.codefans.concurrency.serverspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: mpif
 * @date: 2019-08-28 17:25
 */
@SpringBootApplication
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        } catch (Throwable e) {
            log.error("startup exception," + e.getMessage());
        }
    }
}
