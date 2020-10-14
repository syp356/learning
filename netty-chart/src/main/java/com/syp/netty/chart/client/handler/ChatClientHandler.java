package com.syp.netty.chart.client.handler;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.syp.netty.chart.protocol.IMMessage;
import com.syp.netty.chart.protocol.IMP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<IMMessage>{

    private ChannelHandlerContext ctx;
    private String nickName;

    public ChatClientHandler(String nickName){
        this.nickName = nickName;
    }

    protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws Exception {
        IMMessage m = (IMMessage)msg;
        System.out.println((null == m.getSender() ? "" : (m.getSender() + ":")) + removeHtmlTag(m.getContent()));
    }

    public static String removeHtmlTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        IMMessage message = new IMMessage(IMP.LOGIN.getName(),"Console",System.currentTimeMillis(),this.nickName);
        sendMsg(message);
        System.out.println("成功连接服务器");
        session();
    }

    private void session() {
        new Thread(){

            @Override
            public void run() {
                System.out.println(nickName + ", 你好，请在控制台输入对话聂荣");
                IMMessage message = null;
                Scanner scanner = new Scanner(System.in);
                do{
                    if(scanner.hasNext()){
                        String input = scanner.nextLine();
                        if("exit".equals(input)){
                            message = new IMMessage(IMP.LOGOUT.getName(),"Console",System.currentTimeMillis(),nickName);
                        }else{
                            message = new IMMessage(IMP.CHAT.getName(),System.currentTimeMillis(),nickName,input);
                        }
                    }
                }while(sendMsg(message));
                scanner.close();
            }
        }.start();
    }

    private boolean sendMsg(IMMessage message) {
        ctx.channel().writeAndFlush(message);
        System.out.println("继续输入开始对话");
        return message.getCmd().equals(IMP.LOGOUT) ? false : true;
    }

    /**
     * 发生异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("与服务器断开连接:"+cause.getMessage());
        ctx.close();
    }
}
