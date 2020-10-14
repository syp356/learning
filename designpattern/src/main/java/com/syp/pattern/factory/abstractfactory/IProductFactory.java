package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 * 如果有公共逻辑用抽象类，否则用接口
 */
public interface IProductFactory {
    IAirConditioner createAirConditioner();
    ITelevision createTelevision();
}
