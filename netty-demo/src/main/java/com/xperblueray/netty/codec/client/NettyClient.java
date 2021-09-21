package com.xperblueray.netty.codec.client;

import com.xperblueray.netty.codec.MessageCodec;
import com.xperblueray.netty.codec.MessageDecoder;
import com.xperblueray.netty.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 2. 创建客户端启动助手
        Bootstrap bootstrap = new Bootstrap();
        // 3. 设置线程组
        bootstrap.group(group)
                .channel(NioSocketChannel.class) // 4. 设置客户端通道实现为NIO
                .handler(new ChannelInitializer<SocketChannel>() { // 5. 创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 添加解码器
//                        socketChannel.pipeline().addLast("messageDecoder", new MessageDecoder());
                        // 添加编码器
//                        socketChannel.pipeline().addLast("messageEncoder", new MessageEncoder());
                        // 添加编解码器
                        socketChannel.pipeline().addLast(new MessageCodec());
                        // 6. 向pipeline中添加自定义业务处理handler
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        
        // 7. 启动客户端,等待连接服务端,同时将异步改为同步
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();

        System.out.println("客户端连接成功");
        // 8. 关闭通道和关闭连接池
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
