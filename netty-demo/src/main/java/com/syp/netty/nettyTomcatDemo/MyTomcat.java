package com.syp.netty.nettyTomcatDemo;

import com.syp.netty.nettyTomcatDemo.http.MyRequest;
import com.syp.netty.nettyTomcatDemo.http.MyResponse;
import com.syp.netty.nettyTomcatDemo.http.MyServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class MyTomcat {
    //servlet
    //request
    //response
    //配置好启动端口，默认8080
    //配置web.xml 自己的写的servlet继承自httpservlet
    //servlet-name  servlet-class url-pattern
    //读取配置 url-pattern 和servlet 建立一个映射关系
    //Http请求 发送的数据就是字符串 符合http协议
    //从协议内容中拿到url 把相应的servlet用反射进行实例化
    // 调用实例化对象的service方法，执行具体的逻辑 doget或者dopost
    //request / response 就是InputStream 和outputStream的封装
    private int port = 8080;
    private Map<String,MyServlet> servletMaping = new HashMap<>();

    private Properties webXml = new Properties();
    private void init(){
        try{
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis =  new FileInputStream(WEB_INF+"web.properties");

            webXml.load(fis);
            for(Object k :webXml.keySet()){
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");;
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName+".className");
                    MyServlet obj = (MyServlet) Class.forName(className).newInstance();
                    servletMaping.put(url,obj);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        init();
        //Netty封装了NIO, Reactor模型
        //boss 线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup,workerGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //子线程胡处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //客户端初始化处理
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline().addLast(new HttpResponseEncoder());
                            client.pipeline().addLast(new HttpRequestDecoder());
                            client.pipeline().addLast(new MyTomcatHandler());
                        }
                    })
                    //针对主线程的配置，分配线程最大数量128
                    .option(ChannelOption.SO_BACKLOG,128)
                    //针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture f= server.bind(port).sync();
            System.out.println("my tomcat 已启动");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public class MyTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof HttpRequest){
                HttpRequest req = (HttpRequest) msg;
                MyRequest request = new MyRequest(ctx,req);
                MyResponse response = new MyResponse(ctx,req);
                String url  = request.getUrl();
                if(servletMaping.containsKey(url)){
                    servletMaping.get(url).service(request,response);
                }else{
                    response.write("404 -not found");
                }
            }
        }
    }

    public static void main(String[] args) {
        new MyTomcat().start();
    }

}
