package com.syp.thread.base;

/**
 * @Author: SYP
 * @Date: 2020/4/17
 * @Description:
 * 重入锁
 * ReentrantLock Synchronized 都是可重入锁
 * 读写锁 ReentrantReadWriteLock
 *
 */
public class ReentrantLockDemo {
    public synchronized void demo(){
        System.out.println("demo1");
        demo2();
    }

    public void demo2(){
        synchronized (this){
            System.out.println("demo2");
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        demo.demo();
    }
}
