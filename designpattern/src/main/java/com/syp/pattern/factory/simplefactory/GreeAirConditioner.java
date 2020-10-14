package com.syp.pattern.factory.simplefactory;

/**
 * @Author: SYP
 * @Date: 2020/9/4
 * @Description:
 */
public class GreeAirConditioner implements IAirConditioner {
    @Override
    public int refrigeration() {
        return 0;
    }

    @Override
    public int heating() {
        return 0;
    }
}
