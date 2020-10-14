package com.syp.pattern.factory.homework;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class PaymentFactory {
    IPayment getPayment(Class<? extends IPayment> className) {
        try {
            return className.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
