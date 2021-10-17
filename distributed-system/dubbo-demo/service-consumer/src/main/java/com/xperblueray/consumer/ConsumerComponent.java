package com.xperblueray.consumer;

import com.xperblueray.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class ConsumerComponent {

    @Reference
    HelloService helloService;

    public String sayHello(String name) {
        return helloService.sayHello(name);
    }
}
