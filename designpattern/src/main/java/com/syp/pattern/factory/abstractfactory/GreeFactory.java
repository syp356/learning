package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class GreeFactory implements IProductFactory {

    @Override
    public IAirConditioner createAirConditioner() {
        return new GreeAirConditioner();
    }

    @Override
    public ITelevision createTelevision() {
        return new GreeTelevision();
    }
}
