package com.syp.redisson.executorDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class ExecutorDemo {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ExecutorService executorService2 = Executors.newCachedThreadPool();
    ExecutorService executorService3 = Executors.newFixedThreadPool(4);
    ExecutorService executorService4 = Executors.newScheduledThreadPool(4);

    ArrayBlockingQueue<User> arrayBlockingQueue = new ArrayBlockingQueue<User>(10);

    public static void main(String[] args) throws InterruptedException {
        ExecutorDemo executorDemo = new ExecutorDemo();
                executorDemo.init();
        for(int i=0; i<1000; i++){
            Thread.sleep(2000);
            executorDemo.register("user_"+i);
        }
    }

    public void init(){
        executorService.execute(() -> {
            while (true) {
                try {
                    User user = arrayBlockingQueue.take();
                    sendMsg(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public  void register(String name){
        User user = new User(name);
        try {
            arrayBlockingQueue.put(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(User user){
        System.out.println("给"+user.getName()+"发送消息");
    }
}
