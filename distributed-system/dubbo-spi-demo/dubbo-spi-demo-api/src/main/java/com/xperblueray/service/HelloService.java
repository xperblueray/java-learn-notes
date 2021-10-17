package com.xperblueray.service;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface HelloService {
    String sayHello();
}
