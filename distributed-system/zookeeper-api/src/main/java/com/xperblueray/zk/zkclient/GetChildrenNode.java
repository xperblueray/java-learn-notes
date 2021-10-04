package com.xperblueray.zk.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class GetChildrenNode {
    public static void main(String[] args) throws InterruptedException {
        String path = "/test-zkClient";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        List<String> children = zkClient.getChildren(path);
        System.out.println(children);

        zkClient.subscribeChildChanges("/test-zkClient-gg", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + "'s children changed, current children: " + currentChilds);
            }
        });

        zkClient.createPersistent("/test-zkClient-gg");
        Thread.sleep(1000);

        zkClient.createPersistent("/test-zkClient-gg/child01");
        Thread.sleep(1000);
        zkClient.deleteRecursive("/test-zkClient-gg/child01");
        Thread.sleep(Integer.MAX_VALUE);

    }
}
