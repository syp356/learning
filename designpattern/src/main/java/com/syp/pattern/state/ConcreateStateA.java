package com.syp.pattern.state;
/**
 *@Author: SYP
 *@Date: 2020/3/31
 *@Description:
 */
public class ConcreateStateA extends State{
    public void favorite() {
        this.switch2Login();
        this.context.getState().favorite();
    }

    private void switch2Login() {
        System.out.println("跳转到登录页");
        this.switch2Login();
//        this.context.getState().
    }

    public void comment(String comment) {
        this.switch2Login();
        this.context.getState().comment(comment);
    }
}
