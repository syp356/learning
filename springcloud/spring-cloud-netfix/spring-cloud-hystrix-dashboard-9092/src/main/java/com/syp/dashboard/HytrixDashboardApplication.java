package com.syp.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: SYP
 * @Date: 2020/10/12
 * @Description:
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HytrixDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(HytrixDashboardApplication.class,args);
    }
}
