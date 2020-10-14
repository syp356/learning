package com.syp.netty.nettyTomcatDemo.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class MyRequest {
    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public MyRequest(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public String getUrl(){
        return req.uri();
    }

    public String getMethod(){
        return req.method().name();
    }

//    public Map<String>
}
