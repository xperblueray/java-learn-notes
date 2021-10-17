package com.xperblueray.service;

import com.xperblueray.api.HelloService;

public class HelloServiceMock implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello mock";
    }
}
