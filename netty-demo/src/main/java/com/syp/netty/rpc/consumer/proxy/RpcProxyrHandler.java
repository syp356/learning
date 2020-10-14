package com.syp.netty.rpc.consumer.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcProxyrHandler extends ChannelInboundHandlerAdapter {
   private Object response;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("client is exception");
    }

    public Object getResponse() {
        return response;
    }
}
