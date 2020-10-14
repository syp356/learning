package com.syp.order.service.provider.exception;

import com.syp.springcloud.api.R;
import com.syp.springcloud.exception.ValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request){
        log.info("GlobalException.handleException:{},{]",request.getRequestURI(),e);
        String msg = "系统繁忙："+e.getMessage();
        if(e instanceof ValidException){
            msg = e.getMessage();
        }
        return new R.Builder().buildCustomize(msg);
    }
}
