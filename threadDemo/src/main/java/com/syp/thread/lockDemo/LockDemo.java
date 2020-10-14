package com.syp.redisson.lockDemo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class LockDemo {
    static Queue<String> queue = new LinkedList<String>();
    static Lock lock = new ReentrantLock();
    static Condition emptyCondition = lock.newCondition();
    static Condition fullCondition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(new Provider(queue,lock,emptyCondition,fullCondition)).start();
        new Thread(new Consumer(queue,lock,emptyCondition,fullCondition)).start();
        new Thread(new Consumer(queue,lock,emptyCondition,fullCondition)).start();
        new Thread(new Consumer(queue,lock,emptyCondition,fullCondition)).start();
    }
}
