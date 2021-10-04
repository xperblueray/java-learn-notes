package com.xperblueray.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class UpdateNodeData implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new UpdateNodeData());

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
            // 更新节点内容的方法
            try {
                updateNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }

        }

    }

    private void updateNodeSync() throws InterruptedException, KeeperException {
        /**
         * Params:
         * path – the path of the node
         * data – the data to set
         * version – the expected matching version
         * Returns:
         * the state of the node
         */
        byte[] data = zooKeeper.getData("/test-persistent", false, null);
        System.out.println("修改前的值" + new String(data));
        // 修改数据stat 状态信息对象， version -1表示修改最新版本
        Stat stat = zooKeeper.setData("/test-persistent", "持久节点内容".getBytes(StandardCharsets.UTF_8), -1);
        byte[] data1 = zooKeeper.getData("/test-persistent", false, stat);
        System.out.println("修改后的值" + new String(data1));

        byte[] data2 = zooKeeper.getData("/test-persistent", false, null);
        System.out.println("修改后的值" + new String(data2));
    }
}
