package com.github.liyibo1110.rpc.demo.client.discovery;

import java.util.List;

/**
 * @author liyibo
 */
public interface LoadBalanceStrategy {

    String selectRepo(final List<String> repos);
}
