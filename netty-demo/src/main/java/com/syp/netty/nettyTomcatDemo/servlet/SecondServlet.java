package com.syp.redisson.nettyDemo.servlet;

import com.syp.redisson.nettyDemo.http.MyRequest;
import com.syp.redisson.nettyDemo.http.MyResponse;
import com.syp.redisson.nettyDemo.http.MyServlet;

public class SecondServlet extends MyServlet {

	public void doGet(MyRequest request, MyResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(MyRequest request, MyResponse response) throws Exception {
		response.write("This is First Serlvet");
	}

}
