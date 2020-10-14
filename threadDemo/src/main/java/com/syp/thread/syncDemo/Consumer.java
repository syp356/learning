package com.syp.redisson.syncDemo;

import java.util.Queue;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class Consumer implements Runnable{
    Queue<String> queue;

    public Consumer(Queue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (queue){
                while(queue.isEmpty()){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    String msg = queue.poll();
                    System.out.println("消费者消费了"+msg);
                    queue.notify();

            }

        }
    }
}
