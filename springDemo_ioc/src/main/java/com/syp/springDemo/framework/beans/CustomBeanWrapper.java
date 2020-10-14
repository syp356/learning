package com.syp.springDemo.framework.beans;

/**
 * @Author: SYP
 * @Date: 2020/5/23
 * @Description:
 */
public class CustomBeanWrapper {
    private Object wrapperInstance;
    private Class<?> wrappedClass;

    public CustomBeanWrapper(Object instance) {
        this.wrapperInstance = instance;
        this.wrappedClass = instance.getClass();
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }
}
