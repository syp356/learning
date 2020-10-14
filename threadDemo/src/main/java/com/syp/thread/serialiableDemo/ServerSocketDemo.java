package com.syp.redisson.serialiableDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class ServerSocketDemo {
    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            server = new ServerSocket(8888);
            Socket client = server.accept();
            InputStream in = client.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            String info;
//            while((info = br.readLine()) != null){
                System.out.println("服务端收到消息"+ir.read(new char[1024]));
//            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
