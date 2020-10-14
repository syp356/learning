package com.syp.spring.cloud.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: SYP
 * @Date: 2020/8/21
 * @Description:
 */
@RestController
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }


    @GetMapping("/user/{id}")
    public String findById(@PathVariable("id")int id){
        ServiceInstance serviceInstance = loadBalancerClient.choose("spring-cloud-order-service");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort()+"/orders");
        return restTemplate.getForObject(url,String.class);
    }


}
