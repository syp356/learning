package com.syp.redisson.reentrantLockDemo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class ReentrantLockDemo {
    static ReentrantLock lock = new ReentrantLock();
    static int count = 0;
    public static void inc(){
        try {
            lock.lock();
            Thread.sleep(1000);
            count++;
            System.out.println("inc "+ count);
            desc();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public static void desc(){
        lock.lock();
        count--;
        System.out.println("desc "+count);
        lock.unlock();
    }

    public static void main(String[] args) {
        for(int i=0; i<1000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    inc();
                }
            }).start();
        }
        System.out.println("main "+count);
    }
}
