package com.xperblueray.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * 客户端处理类
 * 1. 发送消息
 * 2. 接受消息
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {
    ChannelHandlerContext ctx;
    String requestMsg;
    String responseMsg;

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    /**
     * 通道连接就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    /**
     * 通道读取就绪事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        responseMsg = msg;
        //唤醒等待的线程
        notify();
    }


    @Override
    public synchronized Object call() throws Exception {
        // 消息发送
        this.ctx.writeAndFlush(requestMsg);
        // 线程等待
        wait();
        return responseMsg;
    }
}
