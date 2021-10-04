package com.xperblueray.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetNodeData implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetNodeData());

        System.out.println(zooKeeper.getState());
        // 计数工具类  不让main方法结束让线程处于阻塞等待
        Thread.sleep(Integer.MAX_VALUE);
//        countDownLatch.await();
        System.out.println("会话建立成功");
        // create node
    }

    public static void getChildren() throws InterruptedException, KeeperException {
        /*
     path:路径
     watch:是否要启动监听，当⼦节点列表发⽣变化，会触发监听
     zooKeeper.getChildren(path, watch);
     */
        List<String> children = zooKeeper.getChildren("/test-persistent", true);
        System.out.println(Arrays.toString(children.toArray()));
    }


    /**
     * 回调方法  处理来自服务器端的watcher通知
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/test-persistent", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Arrays.toString(children.toArray()));
        }
        // 当前会话建立完成后，发送 synConnected
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // 解除主线程在countDownLatch上的等待阻塞
//            countDownLatch.countDown();

            try {
                getNodeData();
                getChildren();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 获取某个节点的内容
     */
    private void getNodeData() throws InterruptedException, KeeperException {
        /**
         * path : 获取数据的路径
         * watch : 是否开启监听
         * stat : 节点状态信息
         * null: 表示获取最新版本的数据
         * zk.getData(path, watch, stat);
         */
        byte[] data = zooKeeper.getData("/test-persistent", false, null);
        System.out.println(new String(data));
    }
}
