package com.syp.springDemo1.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: SYP
 * @Date: 2020/4/12
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyControllor {
    String value() default "";
}
