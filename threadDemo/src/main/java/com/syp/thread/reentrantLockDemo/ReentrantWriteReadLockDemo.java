package com.syp.redisson.reentrantLockDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 * ReentrantLock 和ReentrantReadWriteLock并没有直接的关系
 * 前者实现Lock接口，后者实现ReadWriteLock,后者提供的读写锁实现了Lock接口
 */
public class ReentrantWriteReadLockDemo {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock readLock = lock.readLock();
    static Lock writeLock = lock.writeLock();
    static int count = 0;
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                put(10);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                get();
            }
        }).start();
    }

    public static void put(int num){
        writeLock.lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count = num;
        writeLock.unlock();
    }

    public static int get(){
        readLock.lock();
        int i = count;
        readLock.unlock();
        System.out.println(i);
        return i;
    }

}
