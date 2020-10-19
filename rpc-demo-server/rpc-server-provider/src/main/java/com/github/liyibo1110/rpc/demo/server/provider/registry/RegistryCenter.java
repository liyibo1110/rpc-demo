package com.github.liyibo1110.rpc.demo.server.provider.registry;

/**
 * @author liyibo
 */
public interface RegistryCenter {

    /**
     * 注册服务名称和地址
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName, String serviceAddress);
}
