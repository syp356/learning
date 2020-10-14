package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class MiFactory implements IProductFactory {

    @Override
    public IAirConditioner createAirConditioner() {
        return new MiAirConditioner();
    }

    @Override
    public ITelevision createTelevision() {
        return new MiTelevision();
    }
}
