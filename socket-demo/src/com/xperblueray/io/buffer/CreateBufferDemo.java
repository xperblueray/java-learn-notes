package com.xperblueray.io.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CreateBufferDemo {
    public static void main(String[] args) {
        // 1. 创建一个指定长度的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuffer.get());
        }
        // 2. 创建一个有内容的缓冲区
        ByteBuffer wrap = ByteBuffer.wrap("xblue".getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < 5; i++) {
            System.out.println(wrap.get());
        }
    }
}
