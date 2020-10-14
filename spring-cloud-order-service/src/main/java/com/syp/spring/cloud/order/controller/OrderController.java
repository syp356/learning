package com.syp.spring.cloud.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author: SYP
 *@Date: 2020/8/21
 *@Description:
 */
@RestController
public class OrderController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/orders")
    public String getAllOrder(){
        System.out.println("port:"+port);
        return "Return All Order";
    }
}
