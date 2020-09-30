package com.github.liyibo1110.rpc.demo.server.api;

import java.io.Serializable;

/**
 * @author liyibo
 */
public class RpcRequest implements Serializable {

    private String className;
    private String methodName;
    private Object[] arguments;
    private String version;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
