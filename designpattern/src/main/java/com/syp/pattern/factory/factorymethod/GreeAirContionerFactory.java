package com.syp.pattern.factory.factorymethod;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class GreeAirContionerFactory implements IAirConditionFactory {

    @Override
    public GreeAirConditioner create() {
        return new GreeAirConditioner();
    }
}
