package com.syp.netty.chart.protocol;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public class IMMessage {
    private String addr;//IP地址
    private String cmd;//命令類型
    private long time; //命令发送时间
    private int online;//是否在线
    private String sender;//消息发送者
    private String receiver;//消息接受者
    private String content; //消息内容
    private String terminal;//终端

    public IMMessage() {
    }

    public IMMessage(String cmd,long time,int online,String content){
        this.cmd = cmd;
        this.time = time;
        this.online = online;
        this.content = content;
        this.terminal = terminal;
    }

    public IMMessage(String cmd,String terminal,long time,String sender){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.terminal = terminal;
    }


    public IMMessage(String cmd,long time,String sender,String content){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.content = content;
        this.terminal = terminal;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return "IMMessage{" +
                "addr='" + addr + '\'' +
                ", cmd='" + cmd + '\'' +
                ", time=" + time +
                ", online=" + online +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", terminal='" + terminal + '\'' +
                '}';
    }
}
