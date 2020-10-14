package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/5
 * @Description:
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        //小作坊
        IProductFactory factory = getFactory("HAIER");
        IAirConditioner airConditioner = factory.createAirConditioner();
        ITelevision television = factory.createTelevision();
        System.out.println(airConditioner);
        System.out.println(television);
    }

    private static IProductFactory getFactory(String name) {
        switch (name){
            case "HAIER":
                return new HaierFactory();
            case "XIAOMI":
                return new MiFactory();
            case "GREE":
                return  new GreeFactory();
            default:
                return null;
        }
    }
}
