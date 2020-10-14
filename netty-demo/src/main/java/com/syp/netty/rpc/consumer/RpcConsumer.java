package com.syp.netty.rpc.consumer;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.syp.netty.rpc.api.IRpcHelloService;
import com.syp.netty.rpc.api.IRpcService;
import com.syp.netty.rpc.consumer.proxy.RpcProxy;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcConsumer {

    public static void main(String[] args) {
        IRpcHelloService helloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(helloService.hello("Tom"));

        IRpcService service = RpcProxy.create(IRpcService.class);
        System.out.println(service.add(2,8));
        System.out.println(service.sub(2,8));
        System.out.println(service.mult(2,8));
        System.out.println(service.div(2,8));
    }
}
