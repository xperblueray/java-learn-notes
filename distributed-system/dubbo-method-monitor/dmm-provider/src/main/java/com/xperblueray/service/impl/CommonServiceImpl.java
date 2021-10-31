package com.xperblueray.service.impl;

import com.xperblueray.service.CommonService;

import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;

public class CommonServiceImpl implements CommonService {
    @Override
    public String methodA() throws Exception {
//        Map<String, String> env = System.getenv();

        int random = ThreadLocalRandom.current().nextInt(100);
        System.out.println("随机等待" + random + "ms");
        Thread.sleep(random);
//        return env.get("JAVA_HOME");
        return "";
    }

    @Override
    public String methodB() throws Exception {
//
//        return "Properties properties = System.getProperties();
        int random = ThreadLocalRandom.current().nextInt(100);
        System.out.println("随机等待" + random + "ms");
        Thread.sleep(random);
//        return properties.get("os.name").toString() + properties.get("os.version").toString();
        return "";
    }

    @Override
    public String methodC() throws Exception {
        InetAddress addr = null;
//        try {
//            addr = InetAddress.getLocalHost();
//            String ip = addr.getHostAddress().toString(); //获取本机ip
//            String hostName = addr.getHostName().toString(); //获取本机计算机名称
            int random = ThreadLocalRandom.current().nextInt(100);
            System.out.println("随机等待" + random + "ms");
            Thread.sleep(random);
            return "";
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            return null;
//        }

    }
}
