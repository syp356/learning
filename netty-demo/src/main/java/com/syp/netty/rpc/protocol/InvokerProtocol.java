package com.syp.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class InvokerProtocol implements Serializable{
    private String className;
    private String methodName;
    private Class<?> [] params;
    private Object[] vlaues;

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

    public Class<?>[] getParams() {
        return params;
    }

    public void setParams(Class<?>[] params) {
        this.params = params;
    }

    public Object[] getVlaues() {
        return vlaues;
    }

    public void setVlaues(Object[] vlaues) {
        this.vlaues = vlaues;
    }
}
