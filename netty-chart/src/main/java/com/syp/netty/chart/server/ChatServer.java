package com.syp.netty.chart.server;

import com.syp.netty.chart.protocol.IMDecoder;
import com.syp.netty.chart.protocol.IMEncoder;
import com.syp.netty.chart.server.handler.HttpServerHandler;
import com.syp.netty.chart.server.handler.TerminalServerHandler;
import com.syp.netty.chart.server.handler.WebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
@Slf4j
public class ChatServer {
    private int port  = 8080;
    public void start(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IMDecoder())
                                    .addLast(new IMEncoder())
                                    //处理直接发送IMMessage
                                    .addLast(new TerminalServerHandler())
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(64*1024))
                                    .addLast(new ChunkedWriteHandler())
                                    //解析http协议
                                    .addLast(new HttpServerHandler())
                                    .addLast(new WebSocketServerProtocolHandler("/im"))
                                    //处理实时聊天
                                    .addLast(new WebSocketServerHandler());

                        }
                    });
            ChannelFuture f = server.bind(port).sync();
            System.out.println("服务已启动,监听端口" + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void start() {
        start(this.port);
    }

    public static void main(String[] args) throws IOException {
        if(args.length > 0) {
            new ChatServer().start(Integer.valueOf(args[0]));
        }else{
            new ChatServer().start();
        }
    }
}
