package com.syp.pattern.state;

/**
 * 2020-03-18
 * 状态模式：
 *状态模式又称为状态机模式，工作流引擎，封装了状态的变化流程
 * spring-statemachine-core
 */
public class StateModel {
    public static void main(String[] args){
        Context context = new Context();
        context.favorite();
        context.comment("评论");
    }
}
