package com.syp.netty.tomcatDemo;

import com.syp.netty.tomcatDemo.http.MyRequest;
import com.syp.netty.tomcatDemo.http.MyResponse;
import com.syp.netty.tomcatDemo.http.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
    private Map<String, MyServlet> servletMaping = new HashMap<String, MyServlet>();
    private ServerSocket server;

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

    public void start() {
        init();
        try{
            server = new ServerSocket(port);
            System.out.println("已启动");
            while(true){
                Socket client = server.accept();
                process(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        MyRequest request = new MyRequest(is);
        MyResponse response = new MyResponse(os);

        String url = request.getUrl();
        if(servletMaping.containsKey(url)){
            servletMaping.get(url).service(request,response);
        }else{
            response.write("404 -not fund");
        }

        os.flush();
        os.close();

    }


    public class MyTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            if(msg instanceof HttpRequest){
//                HttpRequest req = (HttpRequest) msg;
//                MyRequest request = new MyRequest(ctx,req);
//                MyResponse response = new MyResponse(ctx,req);
//                String url  = request.getUrl();
//                if(servletMaping.containsKey(url)){
//                    servletMaping.get(url).service(request,response);
//                }else{
//                    response.write("404 -not found");
//                }
//            }
        }
    }

    public static void main(String[] args) {
        new MyTomcat().start();
    }

}
