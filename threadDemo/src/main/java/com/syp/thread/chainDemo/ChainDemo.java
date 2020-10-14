package com.syp.redisson.chainDemo;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class ChainDemo {
    public static void main(String[] args) {
        RequestProcesser first = new ChainDemo().startUp();

        for(int i=0; i<10; i++){
            first.doProcess(new Request(i));
        }
    }

    public RequestProcesser startUp(){
        FinalProcesser finished = new FinalProcesser();
        SaveProcesser save = new SaveProcesser(finished);
        PrintProcesser print = new PrintProcesser(save);
        new Thread(print).start();
        new Thread(save).start();
        new Thread(finished).start();
        return print;
    }
}
