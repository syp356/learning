package com.syp.netty.chart.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class IMDecoder extends ByteToMessageDecoder{

    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s-\\s(.*))?");
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final int length = in.readableBytes();
        final byte[] array = new byte[length];
        //把网络传输过来的内容变成一个完整的字符串
        String content = new String(array,in.readerIndex(),length);

        //把字符串变成一个IMMessage对象
        if(null == content || "".equals(content.trim())){
            if(!IMP.isIMP(content)){
                ctx.channel().pipeline().remove(this);
                return;
            }
        }

        in.getBytes(in.readerIndex(),array,0,length);
        out.add(new MessagePack().read(array,IMMessage.class));
        in.clear();
    }

    public IMMessage decode(String msg){
        if(null == msg || "".equals(msg.trim())){
            return null;
        }
        Matcher matcher = pattern.matcher(msg);
        String header = "";
        String content = "";
        if(matcher.matches()){
            header = matcher.group(1);
            content = matcher.group(3);
        }

        String [] headers = header.split("\\]\\[");
        long time = 0;
        time = Long.parseLong(headers[1]);
        String nickname = headers[2];
        nickname = nickname.length() <10 ? nickname :nickname.substring(0,9);
        if(msg.startsWith("["+IMP.LOGIN.getName()+"]")){
            return new IMMessage(headers[0],headers[3],time,nickname);
        }else if(msg.startsWith("["+IMP.CHAT.getName()+"]")){
            return new IMMessage(headers[0],time,nickname,content);
        }else if(msg.startsWith("["+IMP.FLOWER.getName()+"]")){
            return new IMMessage(headers[0],headers[3],time,nickname);
        }else{
            return null;
        }
    }
}
