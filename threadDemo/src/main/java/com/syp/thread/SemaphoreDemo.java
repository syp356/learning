package com.syp.redisson;

import java.util.concurrent.Semaphore;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i=0; i<10; i++){
            new Car(semaphore,i).start();
        }
    }
    static class Car extends Thread{
        Semaphore semaphore;
        int num;

        public Car(Semaphore semaphore, int num){
            this.semaphore = semaphore;
            this.num = num;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("线程"+num+"获得令牌");
                Thread.sleep(2000);
                semaphore.release();
                System.out.println(num+"释放令牌");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
