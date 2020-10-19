package com.github.liyibo1110.rpc.demo.server.provider;

import com.github.liyibo1110.rpc.demo.server.api.RpcRequest;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author liyibo
 */
public class ProcessorHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String, Object> handlerMap;

    public ProcessorHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        Object result = invoke(rpcRequest);
        channelHandlerContext.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String serviceName = request.getClassName();
        String version = request.getVersion();
        if(!StringUtils.isEmpty(version)) {
            serviceName = serviceName + "-" + version;
        }

        Object service = handlerMap.get(serviceName);
        if(service == null) {
            throw new RuntimeException("service not found: " + serviceName);
        }

        Object[] arguments = request.getArguments();
        Method method;
        if(arguments == null) {
            Class clazz = Class.forName(request.getClassName());
            method = clazz.getMethod(request.getMethodName());
        }else {
            Class[] types = new Class[arguments.length];
            for (int i = 0; i < types.length; i++) {
                types[i] = arguments[i].getClass();
            }
            Class clazz = Class.forName(request.getClassName());
            method = clazz.getMethod(request.getMethodName(), types);
        }

        Object result = method.invoke(service, arguments);
        return result;
    }

   /* public void run() {

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest)ois.readObject();
            Object result = invoke(request);

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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
    }*/
}
