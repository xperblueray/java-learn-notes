package com.xperblueray.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

public class CreateNode {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("base")
                .build();
        client.start();
        System.out.println("Zookeeper session established.");
        // 添加节点
//        String path = "/test-curator/child01";
        String path = "/xb-curator/child01";

        // 创建一个初始内容为空的节点
//        client.create().forPath(path);
        // 创建一个包含内容的节点
//        client.create().forPath(path, "内容".getBytes(StandardCharsets.UTF_8));
        // 如果父节点不存在自动创建
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, "init".getBytes(StandardCharsets.UTF_8));
        Thread.sleep(1000);
        System.out.println("Create znode " + path + " successed.");
    }
}
