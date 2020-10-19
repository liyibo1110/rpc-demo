package com.github.liyibo1110.rpc.demo.client.discovery;

import java.util.List;
import java.util.Random;

/**
 * @author liyibo
 */
public class RandomLoadBalanceStrategy extends AbstractLoadBalanceStrategy {

    @Override
    protected String doSelect(final List<String> repos) {
        return repos.get(new Random().nextInt(repos.size()));
    }
}
