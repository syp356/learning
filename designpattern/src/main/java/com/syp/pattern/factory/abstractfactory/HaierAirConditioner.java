package com.syp.pattern.factory.abstractfactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class HaierAirConditioner implements IAirConditioner{
    @Override
    public int refrigeration() {
        return 0;
    }

    @Override
    public int heating() {
        return 0;
    }

}
