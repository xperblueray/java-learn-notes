package com.xperblueray.zk.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class GetData {
    public static void main(String[] args) throws InterruptedException {
        String path = "/test-zkClient-Ep";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");

        boolean exists = zkClient.exists(path);

        if (!exists) {
            zkClient.createEphemeral(path, "123");
        }

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(path + "节点内容更新，更新后的值为" + data);

            }

            @Override
            public void handleDataDeleted(String s) {
                System.out.println(s + "节点被删除");
            }
        });

        // 获取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);

        // 更新
        zkClient.writeData(path, "4567");
        Thread.sleep(1000);

        // 删除
        zkClient.delete(path);
        Thread.sleep(1000);
    }
}
