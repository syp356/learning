package com.syp.netty.rpc.api;

/**
 * @Author: SYP
 * @Date: 2020/8/17-21-42
 * @Description:
 */
public interface IRpcService {

    int add(int a, int b);
    int sub(int a, int b);
    int mult(int a, int b);
    int div(int a, int b);
}
