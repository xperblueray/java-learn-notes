package com.xperblueray.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.xperblueray.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DmmConsumer {
    private static Logger log = LoggerFactory.getLogger(DmmConsumer.class);
    private static long startMillis;
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        CommonService commonService = context.getBean(CommonService.class);
        AtomicInteger integer = new AtomicInteger(0);

        RateLimiter rateLimiter = RateLimiter.create(35);
//        RateLimiter rateLimiter = RateLimiter.create(5);

        ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            if (rateLimiter.tryAcquire()) {
                integer.getAndIncrement();
                executor.execute(() -> {
                    try {
                        log.info("第{}次调用methodA,线程为{}", integer, Thread.currentThread().getName());

                        commonService.methodA();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                executor.execute(() -> {
                    try {
                        log.info("第{}次调用methodB,线程为{}", integer, Thread.currentThread().getName());

                        commonService.methodB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                executor.execute(() -> {
                    try {
                        log.info("第{}次调用methodC,线程为{}", integer, Thread.currentThread().getName());

                        commonService.methodC();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }


    }

}
