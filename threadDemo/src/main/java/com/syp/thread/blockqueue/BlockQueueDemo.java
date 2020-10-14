package com.syp.redisson.blockqueue;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: SYP
 * @Date: 2020/8/14
 * @Description:
 */
public class BlockQueueDemo {
    static LinkedBlockingDeque<String> blockQueue = new LinkedBlockingDeque<String>(10);

    public static void main(String[] args) {
        new Thread(new Provider(blockQueue)).start();
        new Thread(new Provider(blockQueue)).start();
        new Thread(new Consumer(blockQueue)).start();
        new Thread(new Consumer(blockQueue)).start();
        new Thread(new Consumer(blockQueue)).start();
        new Thread(new Consumer(blockQueue)).start();
    }
}
