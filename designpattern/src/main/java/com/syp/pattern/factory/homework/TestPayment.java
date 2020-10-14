package com.syp.pattern.factory.homework;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class TestPayment {
    public static void main(String[] args) {
        PaymentFactory factory = new PaymentFactory();
        IPayment payment = factory.getPayment(AliPay.class);
        payment.prePay();
        payment.payment();
        payment.callback();
    }
}
