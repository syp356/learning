package com.syp.redisson.chainDemo;

/**
 * @Author: SYP
 * @Date: 2020/8/15
 * @Description:
 */
public class Request {
    int id;

    public Request(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                '}';
    }
}
