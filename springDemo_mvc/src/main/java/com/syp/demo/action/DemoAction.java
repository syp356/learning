package com.syp.demo.action;

import com.syp.demo.service.IDemoService;
import com.syp.springDemo.framework.annotatin.CustomAutowired;
import com.syp.springDemo.framework.annotatin.CustomController;
import com.syp.springDemo.framework.annotatin.CustomRequestMapping;
import com.syp.springDemo.framework.annotatin.CustomRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//虽然，用法一样，但是没有功能
@CustomController
@CustomRequestMapping("/demo")
public class DemoAction {

  	@CustomAutowired
	private IDemoService demoService;

	@CustomRequestMapping("/query")
	public void query(HttpServletRequest req, HttpServletResponse resp,
                      @CustomRequestParam("name") String name){
		String result = demoService.get(name);
//		String result = "Custom name is " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@CustomRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
                    @CustomRequestParam("a") Integer a, @CustomRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@CustomRequestMapping("/sub")
	public void add(HttpServletRequest req, HttpServletResponse resp,
                    @CustomRequestParam("a") Double a, @CustomRequestParam("b") Double b){
		try {
			resp.getWriter().write(a + "-" + b + "=" + (a - b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@CustomRequestMapping("/remove")
	public String  remove(@CustomRequestParam("id") Integer id){
		return "" + id;
	}

}
