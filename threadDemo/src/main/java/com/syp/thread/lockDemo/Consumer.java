package com.syp.redisson.lockDemo;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class Consumer implements Runnable{
    Queue<String> queue;
    Lock lock;
     Condition emptyCondition;
     Condition fullCondition ;

    public Consumer(Queue<String> queue, Lock lock, Condition emptyCondition, Condition fullCondition) {
        this.queue = queue;
        this.lock = lock;
        this.emptyCondition = emptyCondition;
        this.fullCondition = fullCondition;
    }

    public void run() {
        for(int i=0; i<20; i++){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while(queue.isEmpty()){
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                String msg = queue.poll();
                System.out.println("消费者消费了"+msg);
                fullCondition.signal();
            lock.unlock();
        }
    }
}
