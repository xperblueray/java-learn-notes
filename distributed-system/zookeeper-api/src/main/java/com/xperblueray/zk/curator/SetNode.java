package com.xperblueray.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;

public class SetNode {
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

        String path = "/xb-curator/child02";
        String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
        // 获取节点数据
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));
        System.out.println("当前的最新版本是"+ stat.getVersion());

        // 更新节点数据
        int version = client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容1".getBytes(StandardCharsets.UTF_8)).getVersion();
        System.out.println("当前的最新版本是"+ version);
        byte[] bytes1 = client.getData().forPath(path);
        System.out.println("修改后的最新内容是" + new String(bytes1));
        System.out.println("Set node for: " + path + " successed, new version: " + version);
        int version1 = client.setData().withVersion(version).forPath(path, "修改内容2".getBytes(StandardCharsets.UTF_8)).getVersion();
        System.out.println("当前的最新版本是"+ version1);
        byte[] bytes2 = client.getData().forPath(path);
        System.out.println("修改后的最新内容是" + new String(bytes2));
        System.out.println("Set node for: " + path + " successed, new version: " + version1);
    }
}
