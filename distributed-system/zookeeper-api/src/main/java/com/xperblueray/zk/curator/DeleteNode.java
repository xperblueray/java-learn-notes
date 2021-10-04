package com.xperblueray.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DeleteNode {
    public static void main(String[] args) throws Exception {
        String path = "/test-curator";
        // 删除子一个子节点
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("base")
                .build();
        client.start();
        System.out.println("Zookeeper session established.");
        // 删除一个子节点
//        client.delete().forPath(path);
        // 删除节点并递归删除其子节点
//        client.delete().deletingChildrenIfNeeded().forPath(path);
        // 指定版本进行删除
//        client.delete().withVersion(1).forPath(path);

        // 强制保证删除一个节点
//        client.delete().guaranteed().forPath(path);

        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
        System.out.println("Delete znode " + path + " successed.");
    }

}
