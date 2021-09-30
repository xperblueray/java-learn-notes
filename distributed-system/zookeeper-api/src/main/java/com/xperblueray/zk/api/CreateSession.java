package com.xperblueray.zk.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSession implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.0.106:2181", 5000, new CreateSession());

        System.out.println(zooKeeper.getState());
        // 计数工具类  不让main方法结束让线程处于阻塞等待
        countDownLatch.await();
        System.out.println("会话建立成功");
    }

    /**
     * 回调方法  处理来自服务器端的watcher通知
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 当前会话建立完成后，发送 synConnected
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // 解除主线程在countDownLatch上的等待阻塞
            countDownLatch.countDown();
        }

    }
}
