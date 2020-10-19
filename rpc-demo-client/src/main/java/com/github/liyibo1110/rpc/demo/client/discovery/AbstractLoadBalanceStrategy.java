package com.github.liyibo1110.rpc.demo.client.discovery;

import java.util.List;

/**
 * @author liyibo
 */
public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy {

    @Override
    public String selectRepo(final List<String> repos) {

        if(repos == null || repos.isEmpty()) {
            return null;
        }else if(repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }

    protected abstract String doSelect(final List<String> repos);
}
