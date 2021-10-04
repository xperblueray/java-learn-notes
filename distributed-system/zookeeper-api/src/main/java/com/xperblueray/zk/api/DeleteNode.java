package com.xperblueray.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DeleteNode implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNode());

        System.out.println(zooKeeper.getState());
        // 计数工具类  不让main方法结束让线程处于阻塞等待
        Thread.sleep(Integer.MAX_VALUE);
//        countDownLatch.await();
        System.out.println("会话建立成功");
        // create node
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
//            countDownLatch.countDown();
            // 删除节点
            try {
                deleteNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }

        }

    }

    private void deleteNodeSync() throws InterruptedException, KeeperException {
        /**
         *
         *         zooKeeper.exists()
         *         zooKeeper.delete();
         */
        Stat exists = zooKeeper.exists("/test-persistent/child06", false);
        System.out.println(exists == null ? "该节点不存在" : "该节点存在");
        if(exists != null) {
            zooKeeper.delete("/test-persistent/child06", -1);
        }

        Stat data = zooKeeper.exists("/test-persistent/child06", false);
        System.out.println(data == null ? "该节点不存在" : "该节点存在");
    }
}
