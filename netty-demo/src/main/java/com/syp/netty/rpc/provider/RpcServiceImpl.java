package com.syp.netty.rpc.provider;

import com.syp.netty.rpc.api.IRpcService;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcServiceImpl implements IRpcService {
    public int add(int a, int b) {
        return a+b;
    }

    public int sub(int a, int b) {
        return a-b;
    }

    public int mult(int a, int b) {
        return a*b;
    }

    public int div(int a, int b) {
        return a/b;
    }
}
