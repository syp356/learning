package com.syp.netty.chart.server.handler;

import com.syp.netty.chart.server.processor.MsgProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    private MsgProcessor processor = new MsgProcessor();

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        processor.sendMsg(ctx.channel(), msg.text());
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Socket Client: 与客户端断开连接:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

}
