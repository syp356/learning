package com.syp.pattern.factory.homework;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class WechatPay implements IPayment{
    @Override
    public void prePay() {

    }

    @Override
    public void payment() {

    }

    @Override
    public boolean callback() {
        return false;
    }
}
