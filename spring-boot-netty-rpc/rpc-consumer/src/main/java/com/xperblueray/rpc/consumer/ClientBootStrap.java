package com.xperblueray.rpc.consumer;

import com.xperblueray.rpc.api.IUserService;
import com.xperblueray.rpc.consumer.client.RpcClient;
import com.xperblueray.rpc.consumer.proxy.RpcClientProxy;
import com.xperblueray.rpc.pojo.User;

public class ClientBootStrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User userById = userService.getUserById(2);
        System.out.println(userById);

    }
}
