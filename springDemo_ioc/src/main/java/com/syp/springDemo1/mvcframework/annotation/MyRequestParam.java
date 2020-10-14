package com.syp.springDemo1.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: SYP
 * @Date: 2020/4/12
 * @Description:
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
    String value() default "";
}
