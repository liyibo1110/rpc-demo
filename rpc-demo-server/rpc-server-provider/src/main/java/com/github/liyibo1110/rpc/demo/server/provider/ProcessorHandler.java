package com.github.liyibo1110.rpc.demo.server.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author liyibo
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    public void run() {

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
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] arguments = request.getArguments();
        Class[] types = new Class[arguments.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = arguments[i].getClass();
        }
        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(), types);
        Object result = method.invoke(service, arguments);
        return result;
    }
}
