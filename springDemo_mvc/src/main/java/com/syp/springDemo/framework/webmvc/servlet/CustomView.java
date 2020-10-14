package com.syp.springDemo.framework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map; /**
 * @Author: SYP
 * @Date: 2020/6/1
 * @Description:
 */
public class CustomView {
    private File viewFile;

    public CustomView(File templateFile) {
        this.viewFile = templateFile;
    }

    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) {

    }
}
