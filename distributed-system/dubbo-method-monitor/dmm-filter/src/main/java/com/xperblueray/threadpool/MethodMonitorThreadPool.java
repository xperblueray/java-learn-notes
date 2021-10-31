package com.xperblueray.threadpool;

import com.xperblueray.util.ZkUtils;
import org.apache.dubbo.common.threadpool.support.fixed.FixedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MethodMonitorThreadPool extends FixedThreadPool implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(MethodMonitorThreadPool.class);

    // 定义队列
    private final LinkedBlockingQueue<List<Long>> TIME_MILLIS = new LinkedBlockingQueue<>();
    public MethodMonitorThreadPool() {
        // 每5s打印一次最近一分钟每个方法的TP90、TP99的耗时情况
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(this, 1, 5, TimeUnit.SECONDS);
    }
    @Override
    public void run() {

        log.info("每5s执行一次");
        List<String> methods = ZkUtils.listMethod();
        for (String method : methods) {
            // 获取所有耗时
            ZkUtils.autoRemove(method);
            long[] millis = ZkUtils.listLogTime(method);

            // 计算TP90,TP99
            // 计算TP90，TP99
            int indexTP90 = (int) (0.9f * millis.length - 1);
            int indexTP99 = (int) (0.99f * millis.length - 1);
            log.info("{}方法执行{}次，TP90为{}，TP99为{}", method, millis.length, millis[indexTP90], millis[indexTP99]);
        }



    }
}
