package com.syp.springcloud.exception;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
public class BaseException extends RuntimeException{
    protected String code;
    protected String msg;

    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super();
        this.msg = msg;
    }

    public BaseException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
