package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.client.discovery.ServiceDiscovery;
import com.github.liyibo1110.rpc.demo.client.discovery.ServiceDiscoveryZk;

import java.lang.reflect.Proxy;

/**
 * @author liyibo
 */
public class RpcProxyClient {

    private ServiceDiscovery serviceDiscovery = new ServiceDiscoveryZk();

    public <T> T clientProxy(final Class<T> interfaceClass, final String version) {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                                        new Class[]{interfaceClass},
                                        new RemoteInvocationHandler(serviceDiscovery, version));
    }
}
