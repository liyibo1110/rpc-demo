package com.github.liyibo1110.rpc.demo.server.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author liyibo
 */
@Configuration
@ComponentScan(basePackages = "com.github.liyibo1110.rpc.demo.server.provider")
public class SpringConfig {

    @Bean(name="rpcServer")
    public NioRpcServer getRpcServer() {
        return new NioRpcServer(8080);
    }
}
