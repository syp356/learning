package com.syp.redisson.lockDemo;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class Provider implements  Runnable{
    Queue<String> queue;
    Lock lock;
    Condition emptyCondition;
    Condition fullCondition ;
    public Provider(Queue queue) {
        this.queue = queue;
    }

    public Provider(Queue<String> queue, Lock lock, Condition emptyCondition, Condition fullCondition) {
        this.queue = queue;
        this.lock = lock;
        this.emptyCondition = emptyCondition;
        this.fullCondition = fullCondition;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while(queue.size() == 10){
                try {
                   fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String msg = "msg"+i;
            queue.add(msg);
            System.out.println("生产者生产了"+msg);
           emptyCondition.signal();
            lock.unlock();
        }
    }
}
