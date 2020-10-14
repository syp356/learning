package com.syp.springcloud.exception;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
public class BizException extends BaseException{
    public BizException(){
        super();
    }

    public BizException(String message){
        super(message);
        this.msg = message;
    }

    public BizException(String code, String msg) {
        super();
        this.msg=msg;
        this.code=code;
    }
}
