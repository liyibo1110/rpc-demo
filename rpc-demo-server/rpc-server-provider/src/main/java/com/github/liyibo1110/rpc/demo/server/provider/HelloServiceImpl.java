package com.github.liyibo1110.rpc.demo.server.provider;

import com.github.liyibo1110.rpc.demo.server.api.IHelloService;
import com.github.liyibo1110.rpc.demo.server.api.User;
import com.github.liyibo1110.rpc.demo.server.provider.annotation.RpcService;

/**
 * @author liyibo
 */
@RpcService(value = IHelloService.class, version = "v1.0")
public class HelloServiceImpl implements IHelloService {

    public String sayHello(String message) {
        System.out.println("[v1.0] request in sayHello:" + message);
        return "[v1.0] Say Hello: " + message;
    }

    public String saveUser(User user) {
        System.out.println("[v1.0] request in saveUser:" + user);
        return "[v1.0] Success";
    }
}
