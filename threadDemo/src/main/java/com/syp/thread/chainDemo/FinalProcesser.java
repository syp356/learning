package com.syp.redisson.chainDemo;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class FinalProcesser implements RequestProcesser, Runnable{
    LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<Request>();
    boolean finish = false;
    @Override
    public RequestProcesser doProcess(Request request) {
        try {
            requests.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while(!finish){
            try {
                Request request = requests.take();
                System.out.println("finish "+request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
