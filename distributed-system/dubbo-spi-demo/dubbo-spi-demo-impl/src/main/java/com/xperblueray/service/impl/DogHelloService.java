package com.xperblueray.service.impl;


import com.xperblueray.service.HelloService;

public class DogHelloService implements HelloService {
    @Override
    public String sayHello() {
        return "hello 汪汪";
    }
}
