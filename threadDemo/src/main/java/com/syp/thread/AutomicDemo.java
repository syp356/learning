package com.syp.redisson;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class AutomicDemo {

//    static int i=0;
    static AtomicInteger i = new AtomicInteger(0);
    static void inc(){
        try {
            Thread.sleep(1);
//            i++;
            i.getAndIncrement();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            new Thread(() -> AutomicDemo.inc()).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(i.get());
    }
}
