package com.syp.redisson.countdownLatchDemo;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        for(int i=0; i<10;i++){
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread_"+ finalI);
                }
            }).start();
        }

        count.await();
        System.out.println("main");
    }
}
