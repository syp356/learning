package com.syp.springcloud.exception;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
public class ValidException extends BaseException{
    public ValidException() {
    }

    public ValidException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ValidException(String code, String msg) {
        super(code, msg);
        this.msg = msg;
        this.code = code;
    }
}
