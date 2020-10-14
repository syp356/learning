package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class HaierFactory implements IProductFactory {


    @Override
    public IAirConditioner createAirConditioner() {
        return new HaierAirConditioner();
    }

    @Override
    public ITelevision createTelevision() {
        return new HairTelevision();
    }
}
