package com.xperblueray.rpc.provider.server;

import com.xperblueray.rpc.provider.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 启动类
 * 实现DisposableBean接口，在容器销毁的时候回执行destroy
 */
@Service
public class RpcServer implements DisposableBean {
    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    @Autowired
    private RpcServerHandler handler;

    public void startServer(String ip, Integer port) {
        try {
            // 1. 创建线程组
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            // 2. 创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3. 设置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 因为通过json字符串传递，所以添加String编解码器
                            ch.pipeline().addLast(new StringDecoder());
                            // 编码器
                            ch.pipeline().addLast(new StringEncoder());
                            // TODO : 业务处理类
                            ch.pipeline().addLast(handler);
                        }
                    });
            // 4. 绑定端口
            ChannelFuture sync = serverBootstrap.bind(ip, port).sync();
            System.out.println("服务端启动成功");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }

    }




    @Override
    public void destroy() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
