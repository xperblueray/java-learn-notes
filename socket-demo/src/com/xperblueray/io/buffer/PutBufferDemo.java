package com.xperblueray.io.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PutBufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.remaining());

//        // 修改当前索引位置
//        byteBuffer.position(1);
//        // 修改最多能操作到哪个位置
//        byteBuffer.limit(9);
//        System.out.println(byteBuffer.position());
//        System.out.println(byteBuffer.limit());
//        System.out.println(byteBuffer.capacity());
//        System.out.println(byteBuffer.remaining());

        // 添加一个字节
        byteBuffer.put((byte) 97);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.remaining());

        // 添加一个字节数组
        byteBuffer.put("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.remaining());

        byteBuffer.put("012345".getBytes(StandardCharsets.UTF_8));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.remaining());
        System.out.println(byteBuffer.hasRemaining());

        // 添加超过缓冲区的长度会报错
//        byteBuffer.put((byte) 1);

        // 缓存区满后，调整position可以重复写，这样会覆盖之气那存入索引的对应的值
        byteBuffer.position(0);
        byteBuffer.put("012345".getBytes(StandardCharsets.UTF_8));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.remaining());
        System.out.println(byteBuffer.hasRemaining());


    }
}
