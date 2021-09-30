package com.xperblueray.rpc.consumer.client;

import com.xperblueray.rpc.consumer.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.*;


/**
 * 客户端
 * 1. 连接Netty服务
 * 2. 提供给调用者主动关闭资源的方法
 * 3. 提供消息发送的方法
 */
public class RpcClient {
    private NioEventLoopGroup group;
    private Channel channel;
    private String ip;
    private Integer port;

    private RpcClientHandler handler = new RpcClientHandler();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    /**
     * 初始化方法
     */
    public void initClient() {
        try {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // String 类型编解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 客户端处理类
                            pipeline.addLast(handler);
                        }
                    });
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if(channel != null) {
                channel.close();
            }
            if(group != null) {
                group.shutdownGracefully();
            }
        }
    }

    /**
     *
     * 提供给调用者主动关闭资源的方法
     */
    public void close() {
        if(channel != null) {
            channel.close();
        }
        if(group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     *
     * 提供消息发送的方法
     * @return
     */
    public Object send(String msg) throws ExecutionException, InterruptedException {
        handler.setRequestMsg(msg);
        Future submit = executorService.submit(handler);
        return submit.get();
    }
}
