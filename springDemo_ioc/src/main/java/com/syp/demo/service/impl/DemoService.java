package com.syp.demo.service.impl;

import com.syp.demo.service.IDemoService;
import com.syp.springDemo.framework.annotatin.CustomController;
import com.syp.springDemo.framework.annotatin.CustomService;
import com.syp.springDemo1.mvcframework.annotation.MyService;

/**
 * 核心业务逻辑
 */
@CustomService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name + ",from service.";
	}

}
