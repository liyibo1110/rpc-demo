package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.server.api.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liyibo
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArguments(args);

        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        Object result = rpcNetTransport.send(request);
        return result;
    }
}
