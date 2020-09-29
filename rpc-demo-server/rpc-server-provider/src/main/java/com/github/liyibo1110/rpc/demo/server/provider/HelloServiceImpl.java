package com.github.liyibo1110.rpc.demo.server.provider;

import com.github.liyibo1110.rpc.demo.server.api.IHelloService;
import com.github.liyibo1110.rpc.demo.server.api.User;

/**
 * @author liyibo
 */
public class HelloServiceImpl implements IHelloService {

    public String sayHello(String message) {
        System.out.println("request in sayHello:" + message);
        return "Say Hello: " + message;
    }

    public String saveUser(User user) {
        System.out.println("request in saveUser:" + user);
        return "Success";
    }
}
