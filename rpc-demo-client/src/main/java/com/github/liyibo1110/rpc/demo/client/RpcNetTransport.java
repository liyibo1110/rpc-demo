package com.github.liyibo1110.rpc.demo.client;

import com.github.liyibo1110.rpc.demo.server.api.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author liyibo
 */
public class RpcNetTransport extends SimpleChannelInboundHandler<Object> {

    private String serviceAddress;
    private Object result;

    public RpcNetTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public Object send(RpcRequest request) {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                .addLast(new ObjectEncoder())
                                .addLast(RpcNetTransport.this);
                    }
                }).option(ChannelOption.TCP_NODELAY, true);
        // 发送请求
        try {
            String[] addresses = serviceAddress.split(":");
            ChannelFuture future = client.connect(addresses[0], Integer.parseInt(addresses[1])).sync();
            future.channel().writeAndFlush(request).sync();
            if(request != null) {
                future.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }

    /*public Object send(RpcRequest request) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            result = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }*/
}
