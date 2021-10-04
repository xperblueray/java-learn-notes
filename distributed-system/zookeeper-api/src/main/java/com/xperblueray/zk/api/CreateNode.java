package com.xperblueray.zk.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class CreateNode implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNode());

        System.out.println(zooKeeper.getState());
        // 计数工具类  不让main方法结束让线程处于阻塞等待
        Thread.sleep(Integer.MAX_VALUE);
//        countDownLatch.await();
        System.out.println("会话建立成功");
        // create node
    }

    /**
     * 创建节点
     */
    private static void createNodeData() throws InterruptedException, KeeperException {
        /**
         * path ：节点创建的路径
         * data[] ：节点创建要保存的数据，是个byte类型的
         *      acl ：节点创建的权限信息(4种类型)
         *      ANYONE_ID_UNSAFE : 表示任何⼈
         *      AUTH_IDS ：此ID仅可⽤于设置ACL。它将被客户机验证的ID替换。
         *      OPEN_ACL_UNSAFE ：这是⼀个完全开放的ACL(常⽤)-->world:anyone
         *      CREATOR_ALL_ACL ：此ACL授予创建者身份验证ID的所有权限
         * createMode ：创建节点的类型(4种类型)
         *      PERSISTENT：持久节点
         *      PERSISTENT_SEQUENTIAL：持久顺序节点
         *      EPHEMERAL：临时节点
         *      EPHEMERAL_SEQUENTIAL：临时顺序节点
         String node = zookeeper.create(path,data,acl,createMode);
         */
        // 永久节点
//        String persistent = zooKeeper.create("/test-persistent", "持久节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String child_persistent = zooKeeper.create("/test-persistent/child01", "持久节点内容的儿子".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 临时节点
//        String ephemeral = zooKeeper.create("/test-ephemeral", "临时节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

//        String persistent_sequential = zooKeeper.create("/test-persistent-sequential", "临时顺序节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

//        System.out.println(persistent);
//        System.out.println(ephemeral);
//        System.out.println(persistent_sequential);

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

            try {
                createNodeData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }

    }
}
