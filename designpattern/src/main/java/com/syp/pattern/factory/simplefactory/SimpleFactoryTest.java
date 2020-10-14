package com.syp.pattern.factory.simplefactory;

/**
 * @Author: SYP
 * @Date: 2020/9/5
 * @Description:
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        //自给自足
        MyAirConditioner conditioner = new MyAirConditioner();
        conditioner.refrigeration();
        conditioner.heating();

        //小作坊
        AirConditionerFactory factory = new AirConditionerFactory();
        IAirConditioner haier = factory.create1("HAIER");
        System.out.println(haier);

        IAirConditioner gree = factory.create2("com.syp.pattern.factory.simplefactory.GreeAirConditioner");
        System.out.println(gree);

        IAirConditioner xiaomi = factory.create3(MiAirConditioner.class);
        System.out.println(xiaomi);
    }
}
