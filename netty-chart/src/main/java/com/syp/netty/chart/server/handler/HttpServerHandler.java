package com.syp.netty.chart.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    private URL baseURL = HttpServerHandler.class.getResource("");
    private final String webRoot = "webroot";

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.getUri();
        RandomAccessFile file = null;
        try{
            String page = uri.equals("/") ? "chat.html" : uri;
            file = new RandomAccessFile(getResource(page),"r");
        }catch (Exception e){
            e.printStackTrace();
            ctx.fireChannelRead(request.retain());
            return;
        }

        HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
        String contextType = "text/html;";
        if(uri.endsWith(".css")){
            contextType = "text/css;";
        }else if(uri.endsWith(".js")){
            contextType = "text/javascript;";
        }else if(uri.toLowerCase().matches(".*\\.(png|jpg|gif)$")){
            String ext = uri.substring(uri.lastIndexOf("."));
            contextType = "image/"+ext;
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,contextType+"charset=utf-8;");

        boolean keeplive = HttpHeaders.isKeepAlive(request);
        if(keeplive){
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
            response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }

        ctx.write(response);
        ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!keeplive){
            future.addListener(ChannelFutureListener.CLOSE);
        }
        file.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel client = ctx.channel();
        System.out.println("Client:"+client.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    private File getResource(String fileName) throws Exception {
        String basePath = baseURL.toURI().toString();
        int start = basePath.indexOf("classes/");
        basePath = (basePath.substring(0,start)+"/"+"classes/").replaceAll("/+","/");
        String path = basePath + webRoot + "/"+fileName;
        System.out.println("BaseURL:"+basePath);
        path = !path.contains("file:") ? path : path.substring(5);
        path = path.replaceAll("//","/");
        return new File(path);
    }
}


