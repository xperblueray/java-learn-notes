package com.xperblueray.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateNode {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("Zookeeper session established.");

        zkClient.createPersistent("/test-zkClient/child01", true);
        System.out.println("Create znode sueccessed.");
    }
}
