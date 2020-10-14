package com.syp.redisson.blockqueue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class Consumer implements Runnable{
    LinkedBlockingDeque<String> queue;

    public Consumer(LinkedBlockingDeque<String> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String msg = null;
            try {
                msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费者消费了"+msg);
        }
    }
}
