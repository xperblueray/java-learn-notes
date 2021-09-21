package com.xperblueray.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 消息解码器
 *
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List list) throws Exception {
        System.out.println("正在进行消息解码");
        list.add(msg.toString(CharsetUtil.UTF_8)); // 传递到下一个handler
    }
}
