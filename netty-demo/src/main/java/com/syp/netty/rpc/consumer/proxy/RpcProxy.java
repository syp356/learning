package com.syp.netty.rpc.consumer.proxy;

import com.syp.netty.rpc.protocol.InvokerProtocol;
import com.syp.netty.rpc.registry.RegistryHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RpcProxy {

    public static<T> T create(Class<?> clazz){
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?> [] intefaces = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(),intefaces,proxy);
        return result;
    }

    private static class MethodProxy implements InvocationHandler{
        private Class<?> clazz;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(Object.class.equals(method.getDeclaringClass())){
                try{
                    return method.invoke(this,args);
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }else{
                return rpcInvoker(proxy,method,args);
            }
            return null;
        }

        private Object rpcInvoker(Object proxy, Method method, Object[] args) throws InterruptedException {
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setParams(method.getParameterTypes());
            msg.setVlaues(args);
            final RpcProxyrHandler consumerHandler = new RpcProxyrHandler();

            EventLoopGroup workerGroup = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();
            try {
                client.group(workerGroup).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();

                                //对自定义协议内容及逆行编解码
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                pipeline.addLast(new LengthFieldPrepender(4));
                                pipeline.addLast("encoder", new ObjectEncoder());
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast(consumerHandler);

                            }
                        });
                ChannelFuture future = client.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                workerGroup.shutdownGracefully();
            }
            return consumerHandler.getResponse();
        }

    }
}
