package com.xperblueray.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateSession {
    /**
     * 借助zkclient完成会话的创建
     *
     * @param args
     */
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("Zookeeper session established");
    }
}
