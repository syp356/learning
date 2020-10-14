package com.syp.pattern.factory.factorymethod;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class HaierAirContionerFactory implements IAirConditionFactory {

    @Override
    public HaierAirConditioner create() {
        return new HaierAirConditioner();
    }
}
