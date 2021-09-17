package com.xperblueray.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) throws IOException {
        while (true) {
            // 1. 创建Socket对象
            Socket socket = new Socket("127.0.0.1", 8999);
            // 2. 从连接中取出输出流并发消息
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("请输入");
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
            // 3. 从连接中去除输入流并接收会话
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("老板说" + new String(bytes, 0, read).trim());
            // 4. 关闭
            scanner.close();
        }
    }
}
