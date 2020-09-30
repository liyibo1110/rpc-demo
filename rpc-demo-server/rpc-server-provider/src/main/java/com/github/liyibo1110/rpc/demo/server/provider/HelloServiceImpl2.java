package com.github.liyibo1110.rpc.demo.server.provider;

import com.github.liyibo1110.rpc.demo.server.api.IHelloService;
import com.github.liyibo1110.rpc.demo.server.api.User;
import com.github.liyibo1110.rpc.demo.server.provider.annotation.RpcService;

/**
 * @author liyibo
 */
@RpcService(value = IHelloService.class, version = "v2.0")
public class HelloServiceImpl2 implements IHelloService {

    public String sayHello(String message) {
        System.out.println("[v2.0] request in sayHello:" + message);
        return "[v2.0] Say Hello: " + message;
    }

    public String saveUser(User user) {
        System.out.println("[v2.0] request in saveUser:" + user);
        return "[v2.0] Success";
    }
}
