package com.xperblueray;

import com.xperblueray.service.HelloService;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.Set;

public class DubboSpiMain {
    public static void main(String[] args) {
        ExtensionLoader<HelloService> extensionLoader = ExtensionLoader.getExtensionLoader(HelloService.class);
        // 遍历所有支持的扩展点
        Set<String> extensions = extensionLoader.getSupportedExtensions();
        for (String extension : extensions) {
            String s = extensionLoader.getExtension(extension).sayHello();
            System.out.println(s);
        }
    }
}
