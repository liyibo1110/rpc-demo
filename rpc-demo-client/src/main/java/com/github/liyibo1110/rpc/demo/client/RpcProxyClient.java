package com.github.liyibo1110.rpc.demo.client;

import java.lang.reflect.Proxy;

/**
 * @author liyibo
 */
public class RpcProxyClient {

    public <T> T clientProxy(final Class<T> interfaceClass, final String host, final int port) {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                                        new Class[]{interfaceClass},
                                        new RemoteInvocationHandler());
    }
}
