package com.syp.netty.tomcatDemo.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class MyResponse {
    private OutputStream os;
    public MyResponse(OutputStream os) {
        this.os = os;
    }

    public void write(String out) throws IOException {
      StringBuilder sb = new StringBuilder();
      sb.append("HTTP/1.1 200 OK\n")
              .append("Content-type: text/html;\n")
              .append(out);
      os.write(sb.toString().getBytes());
    }
}
