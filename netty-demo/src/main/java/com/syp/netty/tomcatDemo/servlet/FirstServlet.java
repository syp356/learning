package com.syp.redisson.tomcatDemo.servlet;

import com.syp.redisson.tomcatDemo.http.MyRequest;
import com.syp.redisson.tomcatDemo.http.MyResponse;
import com.syp.redisson.tomcatDemo.http.MyServlet;

public class FirstServlet extends MyServlet {

	public void doGet(MyRequest request, MyResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(MyRequest request, MyResponse response) throws Exception {
		response.write("This is First Serlvet");
	}

}
