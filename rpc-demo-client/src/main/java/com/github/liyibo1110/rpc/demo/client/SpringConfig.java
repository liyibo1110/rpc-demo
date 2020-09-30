package com.github.liyibo1110.rpc.demo.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liyibo
 */
@Configuration
public class SpringConfig {

    @Bean(name="rpcProxyClient")
    public RpcProxyClient getRpcProxyClient() {
        return new RpcProxyClient();
    }
}
