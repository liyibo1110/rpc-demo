package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.server.api.RpcRequest;

/**
 * @author liyibo
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest request) {
        return null;
    }
}
