package com.xperblueray.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class DeleteNode {
    public static void main(String[] args) {
        String path = "/test-zkClient/child01";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        zkClient.delete(path);
        System.out.println("Delete znode successed.");
    }
}
