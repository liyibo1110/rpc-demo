package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.client.discovery.ServiceDiscovery;
import com.github.liyibo1110.rpc.demo.server.api.RpcRequest;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liyibo
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private ServiceDiscovery serviceDiscovery;
    private String version;

    public RemoteInvocationHandler(ServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArguments(args);
        request.setVersion(version);
        String serviceName = request.getClassName();
        if(!StringUtils.isEmpty(serviceName)) {
            serviceName = serviceName + "-" + version;
        }
        String serviceAddress = serviceDiscovery.discovery(serviceName);
        RpcNetTransport rpcNetTransport = new RpcNetTransport(serviceAddress);
        Object result = rpcNetTransport.send(request);
        return result;
    }
}
