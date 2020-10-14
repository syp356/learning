package com.syp.redisson.syncDemo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class ThreadDemo {
    static Queue<String>  queue = new LinkedList<String>();
    public static void main(String[] args) {
        new Thread(new Provider(queue)).start();
        new Thread(new Consumer(queue)).start();

//
//
//        final Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                System.out.println("t1");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        final Thread t2 = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("t2");
//            }
//        });
//
//        Thread t3 = new Thread(new Runnable() {
//            public void run() {
//                System.out.println("t3");
//            }
//        });

//
//        final Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                System.out.println("t1");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        final Thread t2 = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                    t1.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("t2");
//            }
//        });
//
//        Thread t3 = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    t2.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("t3");
//            }
//        });
//        System.out.println("t1开始了");
//        t1.start();
//        t1.join();
//        System.out.println("t2开始了");
//        t2.start();
//        t2.join();
//        System.out.println("t3开始了");
//        t3.start();
    }

}
