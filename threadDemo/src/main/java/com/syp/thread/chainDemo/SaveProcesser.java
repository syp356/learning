package com.syp.redisson.chainDemo;


import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class SaveProcesser implements RequestProcesser,Runnable{
    LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<Request>();
    boolean finished = false;
    RequestProcesser nextProcesser;

    public SaveProcesser(FinalProcesser nextProcesser) {
        this.nextProcesser = nextProcesser;
    }

    @Override
    public RequestProcesser doProcess(Request request) {
        try {
            requests.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nextProcesser;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!finished){
            try {
                Request request = requests.take();
                System.out.println("save "+request);
                nextProcesser.doProcess(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
