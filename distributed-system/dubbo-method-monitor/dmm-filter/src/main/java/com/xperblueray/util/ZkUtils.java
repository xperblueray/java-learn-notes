package com.xperblueray.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ZkUtils {
    private static final String SLASH = "/";
    private static final String TIME = "time";
    private static CuratorFramework client = null;
        static {
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .sessionTimeoutMs(50000)
                    .connectionTimeoutMs(30000)
                    .retryPolicy( new ExponentialBackoffRetry(1000,3))
                    .namespace("time")
                    .build();
            client.start();
        }

    public static void addLogTime(String method, String millis) {
        String path = SLASH + method + SLASH + millis;
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> listMethod() {
        List<String> strings = new LinkedList<>();
        try {
            strings = client.getChildren().forPath(SLASH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings;
    }
    public static long[] listLogTime(String method) {
        String path = SLASH + method;
        try {
            List<String> strings = client.getChildren().forPath(path);
            long[] longs = strings.stream().mapToLong(e -> {
                String[] timeArray = e.split(":");
                return Long.parseLong(timeArray[1]);
            }).sorted().toArray();
            return longs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new long[0];
    }
    public static void autoRemove(String method) {
        String path = SLASH + method;
        try {
            List<String> strings = client.getChildren().forPath(path);
            List<String> timeoutedNodes = strings.stream().filter(e -> {
                String[] timeArray = e.split(":");
                return System.currentTimeMillis() - Long.parseLong(timeArray[0]) > 60000;
            }).map(e -> SLASH + method + SLASH + e).collect(Collectors.toList());
            if (timeoutedNodes.size() > 0) {
                removeNodes(timeoutedNodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeNodes(List<String> nodes) throws Exception {
        for (String node : nodes) {
            removeNode(node);
        }
    }
    public static void removeNode(String node) throws Exception {
            client.delete().forPath(node);
    }

}
