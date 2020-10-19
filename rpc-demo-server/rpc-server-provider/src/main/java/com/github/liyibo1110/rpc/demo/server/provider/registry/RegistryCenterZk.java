package com.github.liyibo1110.rpc.demo.server.provider.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author liyibo
 */
public class RegistryCenterZk implements RegistryCenter {


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
    public void registry(String serviceName, String serviceAddress) {

        String servicePath = "/" + serviceName;
        try {
            if(cf.checkExists().forPath(servicePath) == null) {
                cf.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(serviceName);
            }
            String addressPath = servicePath + "/" + serviceAddress;
            cf.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
