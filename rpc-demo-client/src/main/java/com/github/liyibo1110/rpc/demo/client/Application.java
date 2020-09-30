package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.server.api.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liyibo
 */
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient client = context.getBean(RpcProxyClient.class);

        IHelloService service = client.clientProxy(IHelloService.class, "127.0.0.1", 8080);
        String result = service.sayHello("hello!");
        System.out.println(result);
    }
}
