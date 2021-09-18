package com.xperblueray.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 创建一个线程池，如果有客户端连接就创建一个线程，与之通信
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一个ServerSocket对象
        ServerSocket serverSocket = new ServerSocket(8999);
        System.out.println("服务器已启动");

        int i = 0;
        while(true) {
            // 监听客户端
            Socket socket = serverSocket.accept();
            System.out.println("有客户端连接, 第" + ++i + "次连接");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                }
            });

        }
    }

    private static void handle(Socket socket) {
        System.out.println("线程ID：" +
                Thread.currentThread().getId() +
                "    线程名称：" + Thread.currentThread().getName());
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("客户端：" + new String(bytes, 0, read));
            // 连接中取出输入流并回话
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("没钱".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
