package com.syp.pattern.factory.homework;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public interface IPayment {
    void prePay();
    void payment();
    boolean callback();
}
