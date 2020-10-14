package com.syp.netty.rpc.provider;

import com.syp.netty.rpc.api.IRpcHelloService;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcHelloServiceImpl implements IRpcHelloService {
    public String hello(String name) {
        return "hello";
    }
}
