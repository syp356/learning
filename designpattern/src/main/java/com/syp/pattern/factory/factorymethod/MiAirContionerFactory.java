package com.syp.pattern.factory.factorymethod;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class MiAirContionerFactory implements IAirConditionFactory {

    @Override
    public MiAirConditioner create() {
        return new MiAirConditioner();
    }
}
