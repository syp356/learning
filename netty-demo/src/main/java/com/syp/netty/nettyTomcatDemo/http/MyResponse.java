package com.syp.netty.nettyTomcatDemo.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class MyResponse {
    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public MyResponse(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public void write(String out) throws UnsupportedEncodingException {
       try {
           if (out == null || out.length() == 0) {
               return;
           }

           FullHttpResponse response = new DefaultFullHttpResponse(
                   HttpVersion.HTTP_1_0,
                   HttpResponseStatus.OK,
                   Unpooled.wrappedBuffer(out.getBytes("UTF-8"))
           );

           response.headers().set("Content-Type", "text/html");
//       if(HttpUtil.isKeepAlive(response)){
//               response.headers().set(CONNECTION,HttpHeaderValues.KEEP_ALIVE);
//       }
           ctx.write(response);
       }finally {
           ctx.flush();
           ctx.close();
       }
    }
}
