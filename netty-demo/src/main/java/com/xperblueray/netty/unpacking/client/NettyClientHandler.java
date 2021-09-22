package com.xperblueray.netty.unpacking.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler implements ChannelInboundHandler {

    /**
     * 通道读就绪事件
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息: " + byteBuf.toString(CharsetUtil.UTF_8));


    }
    /**
     * 通道就绪事件
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        for (int i = 0; i < 10; i++) {
//            channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("你好，这里是netty客户端" + i + "\n",
//                    CharsetUtil.UTF_8));
            channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("你好，这里是netty客户端" + i + "$",
                    CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }


    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

    }
}
