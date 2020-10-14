package com.syp.netty.chart.protocol;

/**
 * @Author: SYP
 * @Date: 2020/8/18
 * @Description:
 */
public enum IMP {
    SYSTEM("SYSTEM"),
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    CHAT("CHAT"),
    FLOWER("FLOWER");

    private String name;
    IMP(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     *@Author: SYP
     *@Date: 2020/8/18
     *@Description:
     */
    public static boolean isIMP(String content){
       return content.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT|FLOWER)]");
    }
}
