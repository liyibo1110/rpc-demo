package com.github.liyibo1110.rpc.demo.server.api;

/**
 * @author liyibo
 */
public interface IHelloService {

    String sayHello(String message);

    /**
     * 保存用户
     * @param user
     * @return
     */
    String saveUser(User user);
}
