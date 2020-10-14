package com.syp.netty.chart.client;

import com.syp.netty.chart.client.handler.ChatClientHandler;
import com.syp.netty.chart.protocol.IMDecoder;
import com.syp.netty.chart.protocol.IMEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class ChatClient {
    private ChatClientHandler clientHandler;
    private String host;
    private int port;

    public ChatClient(String nickName){
        this.clientHandler = new ChatClientHandler(nickName);
    }

    public void connect(String host,int port){
        this.host = host;
        this.port = port;

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            final Bootstrap client = new Bootstrap();
            client.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IMDecoder())
                                    .addLast(new IMEncoder())
                                    .addLast(clientHandler);
                        }
                    });
            ChannelFuture f = client.connect(this.host,this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws IOException {
        new ChatClient("Tom").connect("127.0.0.1",8080);

        String url = "http://localhost:8080/images/a.png";
        System.out.println(url.toLowerCase().matches(".*\\.(gif|png|jpg)$"));

    }
}
