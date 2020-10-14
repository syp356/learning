package com.syp.thread.base;

/**
 * @Author: SYP
 * @Date: 2020/4/13
 * @Description:
 *
 * 1.volatile 保证可见性
 * 一个线程不能及时拿到另一个线程修改的值
 * 2.如何保证可见性
 * hsdis
 * /如果加了volatile ,底层会多一个Lock指令
 * 3.可见性到底是什么？
 * 1.硬件层面
 *
 * 2.jvm层面
 * 1.cpu高速缓存   导致数据不一致 volatile 总线加锁 缓存锁（缓存一致性协议（MESI））
 * MESI:modify Exclusive shared Invalid
 * storeBuffer
 * 2.线程和进程
 * 3.指令优化 重排序
 * 4.（cpu层面）内存屏障用来解决可见性问题
 *
 * 源代码-》编译器的重排序 -》 cpu层面的重排序（内存执行的指令）-》最终执行的代码
 * jmm最核心的价值是解决了有序性 可见性
 *
 * 不是所有的程序都会进行重排序
 * 1.数据依赖规则
 *
 * JMM 内存屏障
 * 编译器级别（语言级别）
 *  loadload  storeload loadstore storestore
 * cpu级别
 *
 * happens-before规则
 * 1.程序的顺序
 * 2.volatile
 *
 *
 */
public class Demo_01 {
    public volatile static boolean stop = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() ->{
            int i=0;
            while(!stop){
                i++;
            }
        });

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
    }

}
