package com.syp.redisson.chainDemo;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class PrintProcesser implements RequestProcesser, Runnable{
    LinkedBlockingDeque<Request> queues = new LinkedBlockingDeque<Request>();
    RequestProcesser nextProcesser;

    boolean finish = false;

    public PrintProcesser(RequestProcesser nextProcesser) {
        this.nextProcesser = nextProcesser;
    }

    @Override
    public RequestProcesser doProcess(Request request) {
        try {
            queues.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nextProcesser;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!finish){
            try {
                Request request = queues.take();
                System.out.println("print "+request);
                nextProcesser.doProcess(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
