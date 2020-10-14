package com.syp.redisson.syncDemo;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 * 线程死锁
 * 如果a b是Integer 类型的，并不会产生死锁
 */
public class ThreadDiedLockDemo {
    static Object a = new Object();
    static Object b = new Object();


    public static void main(String[] args) {

        new Thread(new Runnable() {
            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                synchronized (a){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (b){
                        System.out.println("b = "+b);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (b){
                    synchronized (a){
                    }
                }
            }
        }).start();
//        System.out.println("开始执行线程");
//        t1.start();
//        t2.start();
    }

}
