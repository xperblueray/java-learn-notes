package com.xperblueray.rpc.consumer.proxy;

import com.alibaba.fastjson.JSON;
import com.xperblueray.rpc.common.RpcRequest;
import com.xperblueray.rpc.common.RpcResponse;
import com.xperblueray.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 创建代理类-创建代理对象
 * 1.封装request请求对象
 * 2.创建RpcClient对象
 * 3.发送消息
 * 4.返回结果
 */
public class RpcClientProxy {
    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 1.封装request请求对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setParameterType(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        // 2.创建RpcClient对象
                        RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);

                        try {
                            // 3.发送消息
                            Object responseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                            RpcResponse response = JSON.parseObject(responseMsg.toString(), RpcResponse.class);
                            if (response.getError() != null) {
                                throw new RuntimeException(response.getError());
                            }
                            // 4.返回结果
                            return JSON.parseObject(response.getResult().toString(), method.getReturnType());
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            rpcClient.close();
                        }

                    }
                });
    }
}
