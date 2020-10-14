package com.syp.springDemo.framework.webmvc.servlet;

import java.io.File;

/**
 * @Author: SYP
 * @Date: 2020/6/1
 * @Description:
 */
public class CustomViewResolver {
    private String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;
    public CustomViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir =  new File(templateRootPath);
    }

    public CustomView resolveViewName(String viewName){
        if(null == viewName || "".equals(viewName.trim())){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName :  (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath()+"/"+viewName).replaceAll("/+","/"));
        return new CustomView(templateFile);
    }
}
