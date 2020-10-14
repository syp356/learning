package com.syp.pattern.factory.simplefactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class AirConditionerFactory {

    /**
     * 最简单的工厂模式，传入对应的名字，if判断创建对应的产品对象
     * 缺点：不符合开闭原则，如果增加或者减少产品，需要修改代码，添加或减少对应的if分支
     */
    public IAirConditioner create1(String name){
        if(name.equals("HAIER")){
            return new HaierAirConditioner();
        }else if(name.equals("GREE")){
            return new GreeAirConditioner();
        }else if(name.equals("MI")){
            return new MiAirConditioner();
        }
        return null;
    }

    /**
     *在create1的基础上优化，根据全限定名创建对应产品对象，符合开闭原则
     * 缺点：需要准确输入类的全限定名，容易出错
     */
    public IAirConditioner create2(String name){
        if(null != name && !name.trim().equals("")){
            try {
                return (IAirConditioner) Class.forName(name).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *最终版：使用对应的class来创建对象
     * public IAirConditioner create3(Class clazz){}
     *如果直接传class 会有强制转换问题，使用泛型解决 Class<? extends IAirConditioner> clazz
     */
    public IAirConditioner create3(Class<? extends IAirConditioner> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
