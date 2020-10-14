package com.syp.demo.service.impl;

import com.syp.demo.service.IDemoService;
import com.syp.springDemo.framework.annotatin.CustomService;

/**
 * 核心业务逻辑
 */
@CustomService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name + ",from service.";
	}

}
