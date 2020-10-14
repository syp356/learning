package com.syp.springDemo.framework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @Author: SYP
 * @Date: 2020/4/8
 * @Description:
 */
public class CustomHandlerMapping {
    private Pattern pattern;
    private Method method;
    private Object controller;

    public CustomHandlerMapping(Pattern pattern, Method method, Object controller) {
        this.pattern = pattern;
        this.method = method;
        this.controller = controller;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
