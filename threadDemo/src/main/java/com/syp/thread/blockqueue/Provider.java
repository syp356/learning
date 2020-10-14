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
public class Provider implements  Runnable{
    LinkedBlockingDeque<String> queue;
    public Provider(LinkedBlockingDeque queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String msg = "msg"+i;
            try {
                queue.put(msg);
                System.out.println("生产者生产了"+msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
