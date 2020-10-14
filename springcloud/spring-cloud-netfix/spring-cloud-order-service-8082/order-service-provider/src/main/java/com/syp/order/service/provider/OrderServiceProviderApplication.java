package com.syp.order.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
@EnableTransactionManagement
@EnableFeignClients(basePackages="com.syp.springcloud.clients")
@SpringBootApplication
public class OrderServiceProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceProviderApplication.class,args);
    }
}
