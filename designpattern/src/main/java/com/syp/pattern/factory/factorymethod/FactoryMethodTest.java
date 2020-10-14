package com.syp.pattern.factory.factorymethod;

/**
 * @Author: SYP
 * @Date: 2020/9/5
 * @Description:
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        //小作坊
        IAirConditionFactory factory = getAirConditionFactory("HAIER");
        IAirConditioner airConditioner = factory.create();
        System.out.println(airConditioner);
    }

    private static IAirConditionFactory getAirConditionFactory(String name) {
        switch (name){
            case "HAIER":
                return new HaierAirContionerFactory();
            case "XIAOMI":
                return new MiAirContionerFactory();
            case "GREE":
                return  new GreeAirContionerFactory();
            default:
                return null;
        }
    }
}
