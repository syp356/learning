package com.syp.pattern.proxy;

import sun.misc.ProxyGenerator;

/**
 * @Author: SYP
 * @Date: 2020/4/1 -2
 * @Description:
 * 代理模式：
 * 为其他对象提供一种代理，以控制对这个对象的访问
 * 代理对象在客户端和目标对象之间起到中介作用
 *
 * 保护目标对象 增强目标对象
 *
 * 静态代理：
 * 显示声明被代理对象
 * 动态代理：
 * jdk自带的代理:通过反射去实现对应的接口，用户需要实现一个接口，对于用户而言，依赖性更强
 *生成逻辑简单，使用反射，执行效率低
 * cglib:代理类继承被代理的类，重写被代理类的方法
 * 不能有final修饰的方法，因为final修饰的方法不能被重写
 *
 * spring中的代理选择原则
 * 当bean有接口时：jdk
 *
 */
public class Proxy {
    public static void main(){
//        ProxyGenerator.generateProxyClass("$Proxy0",new Class[]);
    }
}

