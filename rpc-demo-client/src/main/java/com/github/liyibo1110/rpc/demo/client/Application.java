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

        IHelloService service = client.clientProxy(IHelloService.class, "v2.0");
        String result = service.sayHello("hello!");
        System.out.println(result);
    }
}
