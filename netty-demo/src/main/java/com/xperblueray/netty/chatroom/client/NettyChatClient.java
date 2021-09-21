package com.xperblueray.netty.chatroom.client;

import com.xperblueray.netty.demo.client.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class NettyChatClient {
    private String ip;
    private int port;

    public NettyChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup group = null;
        try {
            // 1. 创建线程组
            group = new NioEventLoopGroup();
            // 2. 创建客户端启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3. 设置线程组
            bootstrap.group(group)
                    .channel(NioSocketChannel.class) // 4. 设置客户端通道实现为NIO
                    .handler(new ChannelInitializer<SocketChannel>() { // 5. 创建一个通道初始化对象
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 6. 向pipeline中添加自定义业务处理handler
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new NettyChatClientHandler());
                        }
                    });

            // 7. 启动客户端,等待连接服务端,同时将异步改为同步
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();
            String ip = channel.localAddress().toString().substring(1);
            System.out.println("-----------------" + ip +
                    "----------------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 向服务端发送消息
                channel.writeAndFlush(msg);
            }
            System.out.println("客户端连接成功");
            // 8. 关闭通道和关闭连接池
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyChatClient("127.0.0.1", 9998).run();
    }
}
