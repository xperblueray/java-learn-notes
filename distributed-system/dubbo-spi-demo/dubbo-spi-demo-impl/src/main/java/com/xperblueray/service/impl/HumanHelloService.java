package com.xperblueray.service.impl;


import com.xperblueray.service.HelloService;

public class HumanHelloService implements HelloService {
    @Override
    public String sayHello() {
        return "hello 你好";
    }
}
