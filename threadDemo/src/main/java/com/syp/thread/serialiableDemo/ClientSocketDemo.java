package com.syp.redisson.serialiableDemo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class ClientSocketDemo {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost",8888);
            OutputStream out = client.getOutputStream();
            PrintWriter pw =new PrintWriter(out);
            pw.write("客户端发送了消息");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
