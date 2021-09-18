package com.xperblueray.io.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class GetBufferDemo {
    public static void main(String[] args) {
        // 1. 创建一个指定长度的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("0123".getBytes(StandardCharsets.UTF_8));
        System.out.println("position:" + allocate.position());
        System.out.println("limit:" + allocate.limit());
        System.out.println("capacity:" + allocate.capacity());
        System.out.println("remaining:" + allocate.remaining());

        // 切换读模式
        System.out.println("读取数据----------------");
        allocate.flip();
        System.out.println("position:" + allocate.position());
        System.out.println("limit:" + allocate.limit());
        System.out.println("capacity:" + allocate.capacity());
        System.out.println("remaining:" + allocate.remaining());
        for (int i = 0; i < allocate.limit(); i++) {
            System.out.println(allocate.get());
        }

        // 读取完毕后，继续读取会报错，超过limit值
//        System.out.println(allocate.get());
        System.out.println("position:" + allocate.position());
        System.out.println("limit:" + allocate.limit());
        System.out.println("capacity:" + allocate.capacity());
        System.out.println("remaining:" + allocate.remaining());
        // 读取指定索引字节
        System.out.println(allocate.get(1));
        System.out.println("读取多个字节-------------");
        // 重复读取
        allocate.rewind();
        System.out.println("position:" + allocate.position());
        System.out.println("limit:" + allocate.limit());
        System.out.println("capacity:" + allocate.capacity());
        System.out.println("remaining:" + allocate.remaining());
        byte[] bytes = new byte[4];
        allocate.get(bytes);
        System.out.println(new String(bytes));

        // 切换写模式，覆盖之前索引所在位置的值
        System.out.println("写模式-----------------");
        allocate.clear();
        System.out.println("position:" + allocate.position());
        System.out.println("limit:" + allocate.limit());
        System.out.println("capacity:" + allocate.capacity());
        System.out.println("remaining:" + allocate.remaining());
        // 并没有实际清除数组的值
        System.out.println(new String(allocate.array()));

        allocate.put("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(allocate.array()));

    }
}
