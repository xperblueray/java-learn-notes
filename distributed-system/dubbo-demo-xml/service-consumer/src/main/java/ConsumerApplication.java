import com.xperblueray.api.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-consumer.xml"});
//        context.start();

        HelloService helloService = (HelloService)context.getBean("helloService"); // 获取远程服务代理
        String hello = helloService.sayHello("world"); // 执行远程方法
        System.out.println("Say Hello result is:" +  hello ); // 显示调用结果
        System.in.read();

    }
}
