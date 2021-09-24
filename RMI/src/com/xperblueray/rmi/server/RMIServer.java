package com.xperblueray.rmi.server;

import com.xperblueray.rmi.service.IUserService;
import com.xperblueray.rmi.service.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // 1. 注册registry实例
            Registry registry = LocateRegistry.createRegistry(9999);
            // 2. 创建远程对象
            IUserService userService = new UserServiceImpl();
            // 3. 将远程对象注册到RMI服务器上即（服务端注册表上）
            registry.rebind("userService", userService);
            System.out.println("RMI客户端启动成功");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
