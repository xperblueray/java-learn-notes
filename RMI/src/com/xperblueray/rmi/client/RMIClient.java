package com.xperblueray.rmi.client;

import com.xperblueray.rmi.pojo.User;
import com.xperblueray.rmi.service.IUserService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws NotBoundException, RemoteException {
        // 1. 获取Registry实例
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9999);
        // 2. 通过Registry实例查找远程对象
        IUserService userService = (IUserService) registry.lookup("userService");
        User userById = userService.getUserById(2);
        System.out.println(userById);
    }
}
