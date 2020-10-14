package com.syp.redisson.chainDemo;

/**
 * @Author: SYP
 * @Date: 2020/8/15-14-33
 * @Description:
 */
public interface RequestProcesser {
    RequestProcesser doProcess(Request request);
}
