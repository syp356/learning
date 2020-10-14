package com.syp.user.service.provider.controller.enums;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2227324689
 * http://www.gupaoedu.com
 **/
public enum LoginTypeEnum {
    NORMAL(0,"帐号密码登录"),
    PHONE_PWD(1,"手机号与密码登录"),
    PHONE_CODE(2,"手机验证码登录"),
    WECHAT(3,"微信授权登录")
    ;

    private int code;
    private String memo;

    LoginTypeEnum(int code, String memo) {
        this.code = code;
        this.memo = memo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
