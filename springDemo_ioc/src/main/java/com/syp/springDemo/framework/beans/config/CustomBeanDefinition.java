package com.syp.springDemo.framework.beans.config;

/**
 * @Author: SYP
 * @Date: 2020/4/6
 * @Description:
 */
public class CustomBeanDefinition {
    private String beanClassName;
    private String factoryBeanName;

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }
}
