package com.syp.springDemo.framework.annotatin;

import java.lang.annotation.*;

/**
 * @Author: SYP
 * @Date: 2020/4/12
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomController {
    String value() default "";
}
