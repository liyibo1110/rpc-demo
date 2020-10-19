package com.github.liyibo1110.rpc.demo.server.provider;

import com.github.liyibo1110.rpc.demo.server.provider.annotation.RpcService;
import com.github.liyibo1110.rpc.demo.server.provider.registry.RegistryCenter;
import com.github.liyibo1110.rpc.demo.server.provider.registry.RegistryCenterZk;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyibo
 */
public class NioRpcServer implements ApplicationContextAware, InitializingBean {

    private int port;
    private RegistryCenter registryCenter = new RegistryCenterZk();
    private Map<String, Object> handlerMap = new HashMap<>();

    ExecutorService executorService = Executors.newCachedThreadPool();

    public NioRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(!serviceBeanMap.isEmpty()) {
            for(Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();  // 接口类名
                String version = rpcService.version();  // 版本号
                if(!StringUtils.isEmpty(version)) {
                    serviceName = serviceName + "-" + version;
                }
                handlerMap.put(serviceName, serviceBean);
                registryCenter.registry(serviceName, getAddress() + ":" + port);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                        .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ProcessorHandler(handlerMap));
                        }
                    });
            server.bind(port).sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

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

    private String getAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }
}
