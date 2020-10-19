package com.github.liyibo1110.rpc.demo.server.provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyibo
 */
public class BioRpcServer implements ApplicationContextAware, InitializingBean {

    private int port;
    private Map<String, Object> handlerMap = new HashMap<>();

    ExecutorService executorService = Executors.newCachedThreadPool();

    public BioRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       /* Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(!serviceBeanMap.isEmpty()) {
            for(Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();  // 接口类名
                String version = rpcService.version();  // 版本号
                if(!StringUtils.isEmpty(version)) {
                    serviceName = serviceName + "-" + version;
                }
                handlerMap.put(serviceName, serviceBean);
            }
        }*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
