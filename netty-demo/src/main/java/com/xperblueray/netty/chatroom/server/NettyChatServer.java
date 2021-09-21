package com.xperblueray.netty.chatroom.server;

import com.xperblueray.netty.demo.server.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyChatServer {
    private int port;

    public NettyChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {

        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            // 1. 创建bossGroup线程组: 处理网络事件--只处理连接事件
            bossGroup = new NioEventLoopGroup(1);
            // 2. 创建workerGroup线程组: 处理网络事件--读写事件 2*处理器线程数
            workerGroup = new NioEventLoopGroup();
            // 3. 创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 4. 设置bossGroup线程组和workerGroup线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 5. 设置服务端通道实现为NIO
                    .option(ChannelOption.SO_BACKLOG, 128) // 6. 参数设置,等待连接接队列
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE) // 6. 参数设置,通道活跃状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 7. 创建一个通道初始化对象
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 8. 向pipeline中添加自定义业务处理handler
                            // 添加编码器
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new NettyChatServerHandler());
                        }
                    });
            // 9. 启动服务端并绑定端口,同时将异步改为同步
//        ChannelFuture future = serverBootstrap.bind(9999).sync();

            ChannelFuture future = serverBootstrap.bind(port);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()) {
                        System.out.println("端口绑定成功");
                    } else {
                        System.out.println("端口绑定失败");
                    }
                }
            });
            System.out.println("聊天室服务端启动成功");
            // 10. 关闭通道(并不是真正意义上的关闭，而是监听通道关闭的状态)和关闭连接池
            future.channel().closeFuture().sync();

        } finally {
            assert bossGroup != null;
            bossGroup.shutdownGracefully();
            assert workerGroup != null;
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyChatServer(9998).run();
    }
}
