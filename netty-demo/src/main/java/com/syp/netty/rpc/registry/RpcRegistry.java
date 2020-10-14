package com.syp.netty.rpc.registry;

import com.syp.netty.rpc.protocol.InvokerProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcRegistry {
    private int port;
    public RpcRegistry(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        //serverSocket  serverSocketChannel
        //Selector 主线程 workeri线程


        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //在netty中，把所有的业务逻辑处理全部归总到了一个队列中
                        //这个队列中包含了各种处理逻辑，对这些处理逻辑在netty中做了封装
                        //封装成了一个pipeline对象 无锁化串行队列
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        //对自定义协议内容及逆行编解码
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast("encoder",new ObjectEncoder());
                        pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(ClassLoader.getSystemClassLoader())));
                        pipeline.addLast(new RegistryHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);

        ChannelFuture future = server.bind(this.port).sync();
        System.out.println("注册中心启动");
        future.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new RpcRegistry(8080).start();
    }
}
