package com.github.liyibo1110.rpc.demo.client.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyibo
 */
public class ServiceDiscoveryZk implements ServiceDiscovery {

    private List<String> serviceRepos = new ArrayList<>();

    private CuratorFramework cf = null;
    {
        cf = CuratorFrameworkFactory.builder().connectString(ZkConfig.CONNECTION_ADDRESS)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("registry")
                .build();
        cf.start();
    }

    @Override
    public String discovery(String serviceName) {

        String path = "/" + serviceName;
        try {
            serviceRepos = cf.getChildren().forPath(path);
            registryWatch(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 负载均衡
        LoadBalanceStrategy strategy = new RandomLoadBalanceStrategy();
        return strategy.selectRepo(serviceRepos);
    }

    /**
     * 注册监听节点变化（模拟上下线）
     * @param path
     * @throws Exception
     */
    private void registryWatch(final String path) throws Exception {

        PathChildrenCache nodeCache = new PathChildrenCache(cf, path, true);
        PathChildrenCacheListener listener = (cf1, pathChildrenCacheEvent) -> {
            serviceRepos = cf1.getChildren().forPath(path);
        };
        nodeCache.getListenable().addListener(listener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
