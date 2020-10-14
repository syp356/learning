package com.syp.springDemo.framework.webmvc.servlet;

import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/5/24
 * @Description:
 */
public class CustomModelAndView {
    private String viewName;
    private Map<String,?> model;

    public CustomModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public CustomModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
