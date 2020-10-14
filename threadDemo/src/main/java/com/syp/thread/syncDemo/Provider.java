package com.syp.redisson.syncDemo;

import java.util.Queue;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class Provider implements  Runnable{
    Queue<String> queue;
    public Provider(Queue queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (queue){
                while(queue.size() == 10){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    String msg = "msg"+i;
                    queue.add(msg);
                    System.out.println("生产者生产了"+msg);
                    queue.notify();

            }
        }
    }
}
